package com.hitomi.cslibrary.floatable;

import android.content.Context;
import android.view.View;

import com.hitomi.cslibrary.CrazyShadowAttr;
import com.hitomi.cslibrary.ShadowHandler;

/**
 * Created by hitomi on 2016/10/19.
 */
public class ShadowFloater implements ShadowHandler {

    private CrazyShadowAttr attr;

    private Context context;

    public ShadowFloater(Context context, CrazyShadowAttr attr) {
        this.context = context;
        this.attr = attr;
    }

    @Override
    public void makeShadow(View view) {

    }

}
