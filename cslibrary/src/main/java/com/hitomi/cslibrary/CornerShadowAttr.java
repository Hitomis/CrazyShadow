package com.hitomi.cslibrary;

/**
 * Created by hitomi on 2016/10/19.
 */
class CornerShadowAttr {

    private int baseColor;

    private int[] shadowColors;

    private float cornerRadius;

    private float shadowSize;

    @ShadowDirection
    private int direction;

    @CornerShadowType
    private int type;

    public int getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(int baseColor) {
        this.baseColor = baseColor;
    }

    public int[] getShadowColors() {
        return shadowColors;
    }

    public void setShadowColors(int[] shadowColors) {
        this.shadowColors = shadowColors;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public float getShadowSize() {
        return shadowSize;
    }

    public void setShadowSize(float shadowSize) {
        this.shadowSize = shadowSize;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
