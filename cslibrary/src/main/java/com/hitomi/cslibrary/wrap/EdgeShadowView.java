package com.hitomi.cslibrary.wrap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.hitomi.cslibrary.ShadowDirection;

/**
 * Created by hitomi on 2016/10/17.
 */
public class EdgeShadowView extends View {

    private Drawable mDrawable;

    private int[] shadowColors;

    private float shadowRadius;

    private float shadowSize;

    @ShadowDirection
    private int direction;

    private EdgeShadowView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth, measureHeight;
        if (direction == ShadowDirection.LEFT || direction == ShadowDirection.RIGHT) {
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

    @ShadowDirection
    public void setDirection(@ShadowDirection int direction) {
        this.direction = direction;
        switch (direction) {
            case ShadowDirection.LEFT:
                mDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, shadowColors);
                break;
            case ShadowDirection.TOP:
                mDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, shadowColors);
                break;
            case ShadowDirection.RIGHT:
                mDrawable = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, shadowColors);
                break;
            case ShadowDirection.BOTTOM:
                mDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, shadowColors);
                break;
            default:
                mDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, shadowColors);
        }
    }

    public static class Builder {

        private Context context;

        private int[] shadowColors;

        private float shadowRadius;

        private float shadowSize;

        @ShadowDirection
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


        public Builder setDirection(@ShadowDirection int direction) {
            this.direction = direction;
            return this;
        }

        public EdgeShadowView create() {
            if (shadowColors == null)
                // 默认的颜色。由浅到深
                //分别为开始颜色，中间夜色，结束颜色
                shadowColors = new int[]{0x00000000, 0x17000000, 0x43000000};

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
