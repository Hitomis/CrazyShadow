package com.hitomi.cslibrary.draw;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.hitomi.cslibrary.base.CrazyShadowAttr;
import com.hitomi.cslibrary.base.ShadowHandler;

/**
 * Created by hitomi on 2016/10/21. <br/>
 *
 * 使用 Drawable 添加阴影效果，同时支持圆角模式. <br/>
 *
 * 因为背景的最大尺寸为原 View 的尺寸，所以设置了阴影
 * 后，原 View 大小会依据配置的阴影尺寸发生变化，并且
 * 原 View 的位置会发生向上的偏移来显示出阴影的效果
 */
public class ShadowDrawer implements ShadowHandler {

    private CrazyShadowAttr attr;

    private View view;

    private Drawable orignalDrawable, shadowDrawable;

    public ShadowDrawer(CrazyShadowAttr attr) {
        this.attr = attr;
    }

    @Override
    public void makeShadow(View view) {
        this.view = view;
        orignalDrawable = view.getBackground();
        int background;
        if (attr.getBackground() != 0) {
            background = attr.getBackground();
        } else {
            ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
            if (colorDrawable == null) {
                background = Color.WHITE;
            } else {
                background = colorDrawable.getColor();
            }
        }
        shadowDrawable = new RoundRectShadowDrawable(
                background, attr.getColors(), attr.getCorner(),
                attr.getShadowRadius(), attr.getShadowRadius());
        view.setBackgroundDrawable(shadowDrawable);
    }

    @Override
    public void removeShadow() {
        if (view != null && view.getBackground() instanceof RoundRectShadowDrawable)
            view.setBackgroundDrawable(orignalDrawable);
    }

    @Override
    public void hideShadow() {
        if (view != null && view.getBackground() instanceof RoundRectShadowDrawable)
            view.setBackgroundDrawable(orignalDrawable);
    }

    @Override
    public void showShadow() {
        if (view  == null || shadowDrawable == null) return ;
        view.setBackgroundDrawable(shadowDrawable);
    }
}
