package com.hitomi.cslibrary;

import android.support.v4.graphics.ColorUtils;

/**
 * Created by hitomi on 2016/10/19.
 */
public class CrazyShadowAttr {

    private String impl;

    private int baseShadowColor;

    private int background;

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

    public void setBaseShadowColor(int baseShadowColor) {
        this.baseShadowColor = baseShadowColor;
        if (colors == null) {
            colors = new int[3];
            colors[0] = ColorUtils.setAlphaComponent(baseShadowColor, 255);
            colors[1] = ColorUtils.setAlphaComponent(baseShadowColor, 128);
            colors[2] = ColorUtils.setAlphaComponent(baseShadowColor, 0);
        }
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        if (colors != null)
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
