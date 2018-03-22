package com.stromdzh.animationtest.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2015/6/24.
 */
public class DeviceUtil {


    /**
     * 获取屏幕宽度
     */
    public static int getScreenW(Context aty) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = aty.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth();
        return w;
    }

    /**
     * 获取屏幕高度
     * 屏幕有效的高度
     * 就是:DecorView的高度去掉导航栏的高度.
     * 这个高度不管你有没有隐藏导航栏, 这个值都不会改变
     *  这个在有些手机上获取的不包括底部的导航栏例如华为h60-l02 4.4.2
     *  有些手机包含底部的高度例如meizu mx4
     */
    public static int getScreenH(Context aty) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = aty.getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        // int h = aty.getWindowManager().getDefaultDisplay().getHeight();
        return h;
    }


}
