package com.hitomi.cslibrary;

import android.support.annotation.IntDef;

/**
 * Created by hitomi on 2016/10/10.
 */
@IntDef(flag = true, value = {CrazyShadowDirection.LEFT, CrazyShadowDirection.TOP,
        CrazyShadowDirection.RIGHT, CrazyShadowDirection.BOTTOM,
        CrazyShadowDirection.LEFTTOP, CrazyShadowDirection.RIGHTTOP,
        CrazyShadowDirection.RIGHTBOTTOM, CrazyShadowDirection.LEFTBOTTOM,
        CrazyShadowDirection.ALL})
public @interface CrazyShadowDirection {

    int LEFT = 1;

    int TOP = 1 << 1;

    int RIGHT = 1 << 2;

    int BOTTOM = 1 << 3;

    int LEFTTOP = 1 << 4;

    int RIGHTTOP = 1 << 5;

    int RIGHTBOTTOM = 1 << 6;

    int LEFTBOTTOM = 1 << 7;

    int LEFTTOPBOTTOM = 1 << 8;

    int TOPRIGHTLEFT = 1 << 9;

    int RIGHTBOTTOMTOP = 1 << 10;

    int BOTTOMLEFTRIGHT = 1 << 11;

    int ALL = 1 << 12;
}
