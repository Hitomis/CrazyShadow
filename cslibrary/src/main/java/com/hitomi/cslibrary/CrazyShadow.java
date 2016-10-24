package com.hitomi.cslibrary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.hitomi.cslibrary.drawable.ShadowDrawer;
import com.hitomi.cslibrary.floatable.ShadowFloater;
import com.hitomi.cslibrary.wrap.ShadowWrapper;

/**
 * Created by hitomi on 2016/10/19.
 */
public class CrazyShadow {

    /**
     * 以 {@link android.view.View#setBackground(Drawable)} 的形式为你的
     * View 设置阴影背景，同时可以设置圆角
     */
    public static final String IMPL_DRAW = "drawer";

    /**
     * 以包装的形式为你的 View 设置阴影
     */
    public static final String IMPL_WRAP = "wrappper";

    /**
     * 以浮动的形式为你的 View 设置阴影
     */
    public static final String IMPL_FLOAT = "floater";

    private Context context;

    private ShadowHandler shadowHandler;

    private CrazyShadow(Context context) {
        this.context = context;
    }

    private void createShadowHandler(CrazyShadowAttr attr) {
        if (attr.getImpl().equals(IMPL_DRAW)) {
            shadowHandler = new ShadowDrawer(attr);
        } else if (attr.getImpl().equals(IMPL_WRAP)) {
            shadowHandler = new ShadowWrapper(context, attr);
        } else {
            shadowHandler = new ShadowFloater(context, attr);
        }
    }

    public void make(View view) {
        shadowHandler.makeShadow(view);
    }

    public static class Builder {

        private Context context;

        /**
         * {@link #IMPL_DRAW} <br/>
         * {@link #IMPL_WRAP} <br/>
         * {@link #IMPL_FLOAT} <br/>
         */
        private String impl;

        /**
         * 阴影的基本色
         */
        private int baseShadowColor;

        /**
         * 针对 {@link #IMPL_DRAW} 形式的方设置阴影时需要的背景色
         */
        private int background;

        /**
         * 表示阴影的一个颜色由深到浅且长度为3的数组
         */
        private int[] colors;

        /**
         * 对 {@link #IMPL_DRAW} 形式表示为背景的圆角角度.<br/>
         * 对 {@link #IMPL_WRAP} 与 {@link #IMPL_FLOAT}
         * 表示为阴影顶点的内侧弧度。以适配被设置的 View 是圆角的情况
         */
        private float corner;

        /**
         * 阴影半径
         */
        private float shadowRadius;

        /**
         * 阴影设置在 View 的方向
         */
        @CrazyShadowDirection
        private int direction;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        /**
         *  以何种方式添加阴影:<br/>
         * {@link #IMPL_DRAW} <br/>
         * {@link #IMPL_WRAP} <br/>
         * {@link #IMPL_FLOAT} <br/>
         * @param impl
         * @return Builder
         */
        public Builder setImpl(String impl) {
            this.impl = impl;
            return this;
        }

        /**
         * 阴影的基本颜色，即最深的颜色, {@link com.hitomi.cslibrary.CrazyShadowAttr#setBaseShadowColor(int)}
         * 方法会自动计算出绘制阴影时需要的 {@link #colors}
         * @param baseColor
         * @return Builder
         */
        public Builder setBaseShadowColor(int baseColor) {
            this.baseShadowColor = baseColor;
            return this;
        }

        /**
         * 仅仅对 {@link #IMPL_DRAW} 形式的方设置阴影时需要的属性, 用来设置
         * Drawable 需要的背景色
         * @param background
         * @return Builder
         */
        public Builder setBackground(int background) {
            this.background = background;
            return this;
        }

        /**
         * 绘制阴影时需要的一个颜色由深到浅且长度为3的数组
         * @param colors
         * @return Builder
         */
        public Builder setColors(int[] colors) {
            this.colors = colors;
            return this;
        }

        /**
         * 对 {@link #IMPL_DRAW} 形式表示为背景的圆角角度.<br/>
         * 对 {@link #IMPL_WRAP} 与 {@link #IMPL_FLOAT}
         * 表示为阴影顶点的内侧弧度。以适配被设置的 View 是圆角的情况
         * @param corner
         * @return Builder
         */
        public Builder setCorner(float corner) {
            this.corner = corner;
            return this;
        }

        /**
         * 设置阴影半径
         * @param shadowRadius
         * @return Builder
         */
        public Builder setShadowRadius(float shadowRadius) {
            this.shadowRadius = shadowRadius;
            return this;
        }

        /**
         * 设置阴影的方向，具体查看 {@link com.hitomi.cslibrary.CrazyShadowAttr}
         * @param direction
         * @return Builder
         */
        public Builder setDirection(int direction) {
            this.direction = direction;
            return this;
        }

        private CrazyShadow create() {
            if (colors == null && baseShadowColor == 0)
                // 默认的颜色。由深到浅
                //分别为开始颜色，中间颜色，结束颜色
                colors = new int[]{0x63000000, 0x32000000, 0x00000000};
            CrazyShadowAttr attr = new CrazyShadowAttr();
            attr.setImpl(impl);
            attr.setBaseShadowColor(baseShadowColor);
            attr.setBackground(background);
            attr.setColors(colors);
            attr.setCorner(corner);
            attr.setShadowRadius(shadowRadius);
            attr.setDirection(direction);
            CrazyShadow crazyShadow = new CrazyShadow(context);
            crazyShadow.createShadowHandler(attr);
            return crazyShadow;
        }

        /**
         * 绘制阴影的启动方法，你需要保证参数已经正确设置完毕
         * @param view 被设置阴影的 View
         */
        public void action(View view) {
            create().make(view);
        }
    }
}
