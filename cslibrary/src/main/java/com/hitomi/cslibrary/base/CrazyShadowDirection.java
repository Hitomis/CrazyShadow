package com.hitomi.cslibrary.base;

import android.support.annotation.IntDef;

/**
 * Created by hitomi on 2016/10/10.
 */
@IntDef(flag = true, value = {CrazyShadowDirection.LEFT, CrazyShadowDirection.TOP,
        CrazyShadowDirection.RIGHT, CrazyShadowDirection.BOTTOM,
        CrazyShadowDirection.LEFT_TOP, CrazyShadowDirection.TOP_RIGHT,
        CrazyShadowDirection.RIGHT_BOTTOM, CrazyShadowDirection.BOTTOM_LEFT,
        CrazyShadowDirection.BOTTOM_LEFT_TOP, CrazyShadowDirection.LEFT_TOP_RIGHT,
        CrazyShadowDirection.TOP_RIGHT_BOTTOM, CrazyShadowDirection.RIGHT_BOTTOM_LEFT,
        CrazyShadowDirection.ALL})
public @interface CrazyShadowDirection {

    int LEFT = 1;

    int TOP = 1 << 1;

    int RIGHT = 1 << 2;

    int BOTTOM = 1 << 3;

    int LEFT_TOP = 1 << 4;

    int TOP_RIGHT = 1 << 5;

    int RIGHT_BOTTOM = 1 << 6;

    int BOTTOM_LEFT = 1 << 7;

    int BOTTOM_LEFT_TOP = 1 << 8;

    int LEFT_TOP_RIGHT = 1 << 9;

    int TOP_RIGHT_BOTTOM = 1 << 10;

    int RIGHT_BOTTOM_LEFT = 1 << 11;

    int ALL = 1 << 12;
}
