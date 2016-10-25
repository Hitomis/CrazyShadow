package com.hitomi.cslibrary.draw;

import android.graphics.drawable.ColorDrawable;
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
 * 原 View 的位置会发生向下的偏移来显示出阴影的效果
 */
public class ShadowDrawer implements ShadowHandler {

    private CrazyShadowAttr attr;

    public ShadowDrawer(CrazyShadowAttr attr) {
        this.attr = attr;
    }

    @Override
    public void makeShadow(View view) {
        RoundRectShadowDrawable drawable;
        if (attr.getBackground() != 0) {
            drawable = new RoundRectShadowDrawable(
                    attr.getBackground(), attr.getColors(), attr.getCorner(),
                    attr.getShadowRadius(), attr.getShadowRadius());
        } else {
            ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
            drawable = new RoundRectShadowDrawable(
                    colorDrawable.getColor(), attr.getColors(), attr.getCorner(),
                    attr.getShadowRadius(), attr.getShadowRadius());
        }
        view.setBackgroundDrawable(drawable);
    }
}
