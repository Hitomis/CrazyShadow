package com.hitomi.cslibrary;

import android.support.annotation.IntDef;

/**
 * Created by hitomi on 2016/10/10.
 */
@IntDef(flag = true, value = {ShadowDirection.LEFT, ShadowDirection.TOP,
        ShadowDirection.RIGHT, ShadowDirection.BOTTOM,
        ShadowDirection.LEFTTOP, ShadowDirection.RIGHTTOP,
        ShadowDirection.RIGHTBOTTOM, ShadowDirection.LEFTBOTTOM})
public @interface ShadowDirection {

    int LEFT = 1;

    int TOP = 1 << 1;

    int RIGHT = 1 << 2;

    int BOTTOM = 1 << 3;

    int LEFTTOP = 1 << 4;

    int RIGHTTOP = 1 << 5;

    int RIGHTBOTTOM = 1 << 6;

    int LEFTBOTTOM = 1 << 7;
}
