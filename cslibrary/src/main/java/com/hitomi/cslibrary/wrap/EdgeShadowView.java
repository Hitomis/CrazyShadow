package com.hitomi.cslibrary.wrap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.hitomi.cslibrary.CrazyShadowDirection;

/**
 * Created by hitomi on 2016/10/17.
 */
public class EdgeShadowView extends View {

    private Drawable mDrawable;

    private int[] shadowColors;

    private float shadowRadius;

    private float shadowSize;

    @CrazyShadowDirection
    private int direction;

    private EdgeShadowView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth, measureHeight;
        if (direction == CrazyShadowDirection.LEFT || direction == CrazyShadowDirection.RIGHT) {
            measureWidth = Math.round(shadowRadius);
            measureHeight = Math.round(shadowSize);
        } else {
            measureWidth = Math.round(shadowSize);
            measureHeight = Math.round(shadowRadius);
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        mDrawable.draw(canvas);
    }

    public void setShadowColors(int[] shadowColors) {
        this.shadowColors = shadowColors;
    }

    public void setShadowRadius(float shadowRadius) {
        this.shadowRadius = shadowRadius;
    }

    public void setShadowSize(float shadowSize) {
        this.shadowSize = shadowSize;
    }

    @CrazyShadowDirection
    public void setDirection(@CrazyShadowDirection int direction) {
        this.direction = direction;
        switch (direction) {
            case CrazyShadowDirection.LEFT:
                mDrawable = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, shadowColors);
                break;
            case CrazyShadowDirection.TOP:
                mDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, shadowColors);
                break;
            case CrazyShadowDirection.RIGHT:
                mDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, shadowColors);
                break;
            case CrazyShadowDirection.BOTTOM:
                mDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, shadowColors);
                break;
            default:
                mDrawable = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, shadowColors);
        }
    }

    public static class Builder {

        private Context context;

        private int[] shadowColors;

        private float shadowRadius;

        private float shadowSize;

        @CrazyShadowDirection
        private int direction;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setShadowColors(int[] shadowColors) {
            this.shadowColors = shadowColors;
            return this;
        }

        public Builder setShadowRadius(float shadowRadius) {
            this.shadowRadius = shadowRadius;
            return this;
        }

        public Builder setShadowSize(float shadowSize) {
            this.shadowSize = shadowSize;
            return this;
        }


        public Builder setDirection(@CrazyShadowDirection int direction) {
            this.direction = direction;
            return this;
        }

        public EdgeShadowView create() {
            // 创建 EdgeShadowView
            EdgeShadowView edgeShadowView = new EdgeShadowView(context);
            edgeShadowView.setShadowColors(shadowColors);
            edgeShadowView.setShadowRadius(shadowRadius);
            edgeShadowView.setShadowSize(shadowSize);
            edgeShadowView.setDirection(direction);
            return edgeShadowView;
        }
    }
}
