package com.hitomi.cslibrary;

import android.support.v4.graphics.ColorUtils;

/**
 * Created by hitomi on 2016/10/19.
 */
public class CrazyShadowAttr {

    public static final String IMPL_WRAPPER = "wrapper";

    public static final String IMPL_FLOAT = "floatable";

    public static final String IMPL_DRAWABLE = "drawable";

    private String impl;

    private int baseColor;

    private int[] colors;

    private float corner;

    private float shadowRadius;

    @CrazyShadowDirection
    private int direction;

    public String getImpl() {
        return impl;
    }

    public void setImpl(String impl) {
        this.impl = impl;
    }

    public int getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(int baseColor) {
        this.baseColor = baseColor;
        colors = new int[3];
        colors[0] = ColorUtils.setAlphaComponent(baseColor, 0);
        colors[1] = ColorUtils.setAlphaComponent(baseColor, 128);
        colors[2] = ColorUtils.setAlphaComponent(baseColor, 255);
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public float getCorner() {
        return corner;
    }

    public void setCorner(float corner) {
        this.corner = corner;
    }

    public float getShadowRadius() {
        return shadowRadius;
    }

    public void setShadowRadius(float shadowRadius) {
        this.shadowRadius = shadowRadius;
    }

    public int getDirection() {
        return direction;
    }

    @CrazyShadowDirection
    public void setDirection(@CrazyShadowDirection int direction) {
        this.direction = direction;
    }
}
