package com.hitomi.cslibrary.drawable;

import android.view.View;

import com.hitomi.cslibrary.CrazyShadowAttr;
import com.hitomi.cslibrary.ShadowHandler;

/**
 * Created by hitomi on 2016/10/21.
 */
public class ShadowDrawer implements ShadowHandler {

    private RoundRectShadowDrawable drawable;

    public ShadowDrawer(CrazyShadowAttr attr) {
        attr.getBackground();
        drawable = new RoundRectShadowDrawable(attr.getBackground(),
                attr.getColors(), attr.getCorner(),
                attr.getShadowRadius(), attr.getShadowRadius());
    }

    @Override
    public void makeShadow(View view) {
        view.setBackgroundDrawable(drawable);
    }
}
