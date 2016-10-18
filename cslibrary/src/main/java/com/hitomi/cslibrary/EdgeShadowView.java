package com.hitomi.cslibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

/**
 * Created by hitomi on 2016/10/17.
 */
public class EdgeShadowView extends View {

    private Drawable mDrawable;

    private int[] shadowColors;

    public EdgeShadowView(Context context) {
        super(context);

        init();
    }

    private void init() {
        // 由浅到深
        shadowColors = new int[]{0x00000000, 0x17000000, 0x43000000};//分别为开始颜色，中间夜色，结束颜色
        mDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, shadowColors);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        mDrawable.draw(canvas);
    }

    @ShadowDirection
    public void setDirection(@ShadowDirection int direction) {
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
        }
    }
}
