package com.stromdzh.animationtest.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by suxq on 2017/11/8.
 * 反射方式更改android:windowIsTranslucent
 * http://www.jianshu.com/p/b6d682e301c2#
 * api<=19一下，该类的接口失效
 */
class ActivityTranslucentHelper {
    private static final String TAG = ActivityTranslucentHelper.class.getSimpleName();

    interface PageTranslucentListener {
        void onPageTranslucent();
    }

    static class MyInvocationHandler implements InvocationHandler {

        private WeakReference<PageTranslucentListener> listener;

        public MyInvocationHandler(WeakReference<PageTranslucentListener> listener) {
            this.listener = listener;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                boolean success = (boolean) args[0];
                if (success && listener.get() != null) {
                    listener.get().onPageTranslucent();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 在Activity被convertFromTranslucent方法转为不透明之后，将其再从不透明转为透明。而且该方法对本来不透明的Activity是没有作用的。所以我们只有在本身就为透明的Activity中调用convertFromTranslucent将其转为不透明之后才可以通过convertToTranslucent方法将其再转为透明。
     * 虽说如此，但api21以上确实是可以直接将本身主题不透明的Activity转为透明的，21以下的就不行。所以为了兼容，建议将Activity的主题设置为透明，而针对还有web页面的Activity，再它的onCreate方法中先调用convertFromTranslucent转为不透明，设置其SwipeBackLayout的pageTranslucent为false，再在侧滑开始时调用convertToTranslucent将其转为透明.
     *
     * @param activity
     * @param listener
     */
    private static void convertActivityToTranslucentAfterL(Activity activity, PageTranslucentListener listener) {
        try {
            Method getActivityOptions = Activity.class.getDeclaredMethod("getActivityOptions");
            getActivityOptions.setAccessible(true);
            Object options = getActivityOptions.invoke(activity);

            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }


            MyInvocationHandler myInvocationHandler = new MyInvocationHandler(new WeakReference<PageTranslucentListener>(listener));
            Object obj = Proxy.newProxyInstance(Activity.class.getClassLoader(), new Class[]{translucentConversionListenerClazz}, myInvocationHandler);

            Method convertToTranslucent = Activity.class.getDeclaredMethod("convertToTranslucent",
                    translucentConversionListenerClazz, ActivityOptions.class);
            convertToTranslucent.setAccessible(true);
            convertToTranslucent.invoke(activity, obj, options);
            Log.d(TAG, "convertActivityToTranslucentAfterL true");
        } catch (Throwable t) {
            Log.i(TAG, "convertActivityToTranslucentAfterL error: " + t.getMessage());
            t.printStackTrace();
        }
    }

    private static void convertActivityToTranslucentBeforeL(Activity activity, PageTranslucentListener listener) {
        try {
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }


            MyInvocationHandler myInvocationHandler = new MyInvocationHandler(new WeakReference<PageTranslucentListener>(listener));
            Object obj = Proxy.newProxyInstance(Activity.class.getClassLoader(), new Class[]{translucentConversionListenerClazz}, myInvocationHandler);

            Method convertToTranslucent = Activity.class.getDeclaredMethod("convertToTranslucent",
                    translucentConversionListenerClazz);
            convertToTranslucent.setAccessible(true);
            convertToTranslucent.invoke(activity, obj);
            Log.d(TAG, "convertActivityToTranslucentAfterL true");
        } catch (Throwable t) {
            Log.i(TAG, "convertActivityToTranslucentBeforeL error: " + t.getMessage());
            t.printStackTrace();
        }
    }

    /**
     * android:windowIsTranslucent=true
     *
     * @param activity
     * @param listener
     */
    public static void converActivityToTranslucent(Activity activity, PageTranslucentListener listener) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            convertActivityToTranslucentBeforeL(activity, listener);
//        } else {
//            convertActivityToTranslucentAfterL(activity, listener);
//        }
    }

    /**
     * android:windowIsTranslucent=false
     * 如果activity有播放视频的控件，调用该方法会导致视频黑屏无法播放，暂时不知道原因
     * 但参考5.0的系统动画实现原理，在onStart()里对view注册onPreDraw监听，然后执行动画，执行前先调用converActivityToTranslucent，动画结束后执行该方法就不会导致播放器黑屏
     * 但在onCreate()或onNewIntent()里这样做还是会导致视频黑屏
     *
     * @param activity
     * @return
     */
    public static boolean convertActivityFromTranslucent(Activity activity) {
//        try {
//            Method method = Activity.class.getDeclaredMethod("convertFromTranslucent");
//            method.setAccessible(true);
//            method.invoke(activity);
//            Logger.d(TAG, "convertActivityFromTranslucent true");
//            return true;
//        } catch (Throwable t) {
//            Logger.e(TAG, "convertActivityFromTranslucent error: " + t.getMessage());
//            t.printStackTrace();
//            return false;
//        }

        return false;
    }

}
