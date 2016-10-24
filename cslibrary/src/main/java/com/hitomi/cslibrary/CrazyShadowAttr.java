package com.hitomi.cslibrary;

import android.support.v4.graphics.ColorUtils;

/**
 * Created by hitomi on 2016/10/19.
 */
public class CrazyShadowAttr {

    /**
     * 以何种方式添加阴影:<br/>
     * {@link com.hitomi.cslibrary.CrazyShadow#IMPL_DRAW} <br/>
     * {@link com.hitomi.cslibrary.CrazyShadow#IMPL_WRAP} <br/>
     * {@link com.hitomi.cslibrary.CrazyShadow#IMPL_FLOAT} <br/>
     */
    private String impl;

    /**
     * 阴影的基本颜色，即最深的颜色, {@link #setBaseShadowColor(int)} 方法会自动计算出
     * 绘制阴影时需要的 {@link #colors}
     */
    private int baseShadowColor;

    /**
     * 仅仅对 {@link com.hitomi.cslibrary.CrazyShadow#IMPL_DRAW} 形式的方设置阴影时需要的属性
     */
    private int background;

    /**
     * 绘制阴影时需要的一个颜色由深到浅且长度为3的数组
     */
    private int[] colors;

    /**
     * 对 {@link com.hitomi.cslibrary.CrazyShadow#IMPL_DRAW} 形式表示为背景的圆角角度.<br/>
     * 对 {@link com.hitomi.cslibrary.CrazyShadow#IMPL_WRAP} 与
     * {@link com.hitomi.cslibrary.CrazyShadow#IMPL_FLOAT}
     * 表示为阴影顶点的内侧弧度。以适配被设置的 View 是圆角的情况
     */
    private float corner;

    /**
     * 阴影半径
     */
    private float shadowRadius;

    /**
     * 设置阴影的方向，具体查看 {@link com.hitomi.cslibrary.CrazyShadowAttr}
     */
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

    public boolean containLeft() {
        return direction == CrazyShadowDirection.ALL ||
                direction == CrazyShadowDirection.LEFT ||
                direction == CrazyShadowDirection.LEFT_TOP ||
                direction == CrazyShadowDirection.BOTTOM_LEFT ||
                direction == CrazyShadowDirection.BOTTOM_LEFT_TOP ||
                direction == CrazyShadowDirection.RIGHT_BOTTOM_LEFT ||
                direction == CrazyShadowDirection.LEFT_TOP_RIGHT;
    }

    public boolean containTop() {
        return direction == CrazyShadowDirection.ALL ||
                direction == CrazyShadowDirection.TOP ||
                direction == CrazyShadowDirection.LEFT_TOP ||
                direction == CrazyShadowDirection.TOP_RIGHT ||
                direction == CrazyShadowDirection.LEFT_TOP_RIGHT ||
                direction == CrazyShadowDirection.BOTTOM_LEFT_TOP ||
                direction == CrazyShadowDirection.TOP_RIGHT_BOTTOM;
    }

    public boolean containRight() {
        return direction == CrazyShadowDirection.ALL ||
                direction == CrazyShadowDirection.RIGHT ||
                direction == CrazyShadowDirection.TOP_RIGHT ||
                direction == CrazyShadowDirection.RIGHT_BOTTOM ||
                direction == CrazyShadowDirection.TOP_RIGHT_BOTTOM ||
                direction == CrazyShadowDirection.LEFT_TOP_RIGHT ||
                direction == CrazyShadowDirection.RIGHT_BOTTOM_LEFT;
    }

    public boolean containBottom() {
        return direction == CrazyShadowDirection.ALL ||
                direction == CrazyShadowDirection.BOTTOM ||
                direction == CrazyShadowDirection.BOTTOM_LEFT ||
                direction == CrazyShadowDirection.RIGHT_BOTTOM ||
                direction == CrazyShadowDirection.RIGHT_BOTTOM_LEFT ||
                direction == CrazyShadowDirection.TOP_RIGHT_BOTTOM ||
                direction == CrazyShadowDirection.BOTTOM_LEFT_TOP;
    }
}
