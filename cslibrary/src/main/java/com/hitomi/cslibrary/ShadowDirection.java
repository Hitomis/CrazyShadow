package com.hitomi.cslibrary;

import android.support.annotation.IntDef;

/**
 * Created by hitomi on 2016/9/26.
 */
@IntDef(flag = true, value = {ShadowDirection.LEFT, ShadowDirection.TOP,
        ShadowDirection.RIGHT, ShadowDirection.BOTTOM,
        ShadowDirection.LEFTTOP, ShadowDirection.RIGHTTOP,
        ShadowDirection.RIGHTBOTTOM, ShadowDirection.LEFTBOTTOM})
public @interface ShadowDirection {

    public static int LEFT = 1;

    public static int TOP = 1 << 1;

    public static int RIGHT = 1 << 2;

    public static int BOTTOM = 1 << 3;

    public static int LEFTTOP = 1 << 4;

    public static int RIGHTTOP = 1 << 5;

    public static int RIGHTBOTTOM = 1 << 6;

    public static int LEFTBOTTOM = 1 << 7;
}
