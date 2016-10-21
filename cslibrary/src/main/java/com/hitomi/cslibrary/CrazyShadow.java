package com.hitomi.cslibrary;

import android.content.Context;
import android.view.View;

import com.hitomi.cslibrary.floatable.ShadowFloatable;
import com.hitomi.cslibrary.wrap.ShadowWrapper;

/**
 * Created by hitomi on 2016/10/19.
 */
public class CrazyShadow {

    private Context context;

    private CrazyShadowAttr attr;

    private ShadowHandler shadowHandler;

    public CrazyShadow(Context context) {
        this.context = context;
    }

    private void createShadowHandler() {
        if (attr.getImpl().equals(CrazyShadowAttr.IMPL_DRAWABLE)) {
//            shadowHandler = new RoundRectShadowDrawable();
        } else if (attr.getImpl().equals(CrazyShadowAttr.IMPL_WRAPPER)) {
            shadowHandler = new ShadowWrapper(context);
        } else {
            shadowHandler = new ShadowFloatable();
        }
    }

    public void make(View view) {
        shadowHandler.makeShadow(view, attr);
    }

    public void setAttr(CrazyShadowAttr attr) {
        this.attr = attr;
        createShadowHandler();
    }

    public static class Builder {

        private Context context;

        private String impl;

        private int baseColor;

        private int[] colors;

        private float corner;

        private float shadowRadius;

        @CrazyShadowDirection
        private int direction;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setImpl(String impl) {
            this.impl = impl;
            return this;
        }

        public Builder setBaseColor(int baseColor) {
            this.baseColor = baseColor;
            return this;
        }

        public Builder setColors(int[] colors) {
            this.colors = colors;
            return this;
        }

        public Builder setCorner(float corner) {
            this.corner = corner;
            return this;
        }

        public Builder setShadowRadius(float shadowRadius) {
            this.shadowRadius = shadowRadius;
            return this;
        }

        public Builder setDirection(int direction) {
            this.direction = direction;
            return this;
        }

        private CrazyShadow create() {
            if (colors == null)
                // 默认的颜色。由深到浅
                //分别为开始颜色，中间夜色，结束颜色
                colors = new int[]{0x63000000, 0x32000000, 0x00000000};
            CrazyShadowAttr attr = new CrazyShadowAttr();
            attr.setImpl(impl);
            attr.setBaseColor(baseColor);
            attr.setColors(colors);
            attr.setCorner(corner);
            attr.setShadowRadius(shadowRadius);
            attr.setDirection(direction);
            CrazyShadow crazyShadow = new CrazyShadow(context);
            crazyShadow.setAttr(attr);
            return crazyShadow;
        }

        public void action(View view) {
            create().make(view);
        }
    }
}
