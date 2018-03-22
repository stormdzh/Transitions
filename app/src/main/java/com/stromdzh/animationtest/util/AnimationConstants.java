package com.stromdzh.animationtest.util;

import android.os.Build;

/**
 * 动画常量定义
 * Created by caill on 2017/11/9.
 */

class AnimationConstants {
    public static boolean ENABLE_ANIMATION; //是否开启动画效果

    //Activity动画常量值
    public static final String ACTIVITY_ANIMATION_ENABLE = "activity_animation_enable";
    public static final String ACTIVITY_ANIMATION_PIVOTX = "activity_animation_pivotx";
    public static final String ACTIVITY_ANIMATION_PIVOTY = "activity_animation_pivoty";
    public static final String ACTIVITY_BACK_FROM_VIDEODETAIL = "activity_back_from_videodetail";
    public static final String INTENT_ORIGIN_PURPOSE_CLASS = "intent_origin_purpose_class";
    public static final String ACTIVITY_ANIMATION_OPEN_ENABLE = "activity_animation_open_enable";
    public static final String ACTIVITY_ANIMATION_CLOSE_ENABLE = "activity_animation_close_enable";

    static{
        ENABLE_ANIMATION = getEnableAnimation();
    }

    /**
     * 动画开关是否打开逻辑：
     */
    private static boolean getEnableAnimation(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            return false;
        }
        return true;
    }

}
