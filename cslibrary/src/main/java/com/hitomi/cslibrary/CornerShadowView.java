package com.hitomi.cslibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

/**
 * Created by hitomi on 2016/10/18.
 */
public class CornerShadowView extends View {

    private Paint shadowPaint;

    private Path cornerShadowPath;

    private CornerShadowAttr attr;

    private int degrees;

    private CornerShadowView(Context context, CornerShadowAttr attr) {
        super(context);
        this.attr = attr;
        init();
    }

    private void init() {
        shadowPaint = new Paint(Paint.DITHER_FLAG);
        shadowPaint.setStyle(Paint.Style.FILL);
        cornerShadowPath = new Path();

        switch (attr.getType()) {
            case CornerShadowType.SECTOR:
                buildSectorShadow();
                break;
            case CornerShadowType.RINGSECTOR:
                buildRingSectorShadow();
                break;
        }

        switch (attr.getDirection()) {
            case ShadowDirection.LEFTTOP:
                degrees = 0;
                break;
            case ShadowDirection.RIGHTTOP:
                degrees = 90;
                break;
            case ShadowDirection.RIGHTBOTTOM:
                degrees = 180;
                break;
            case ShadowDirection.LEFTBOTTOM:
                degrees = 270;
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureSize = (int) (attr.getShadowSize() + attr.getCornerRadius());
        setMeasuredDimension(measureSize, measureSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getMeasuredWidth(), getMeasuredHeight());
        canvas.rotate(degrees, -getMeasuredWidth() / 2f, -getMeasuredHeight() / 2f);
        canvas.drawPath(cornerShadowPath, shadowPaint);
        canvas.restore();
    }

    private void buildSectorShadow() {
        float size = attr.getCornerRadius() + attr.getShadowSize();
        RectF rectF = new RectF(-size, -size, size, size);
        cornerShadowPath.reset();

        cornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
        cornerShadowPath.moveTo(0, 0);
        cornerShadowPath.rLineTo(-size, 0);
        cornerShadowPath.arcTo(rectF, 180, 90f, false);
        cornerShadowPath.close();
        float startRatio = attr.getCornerRadius() / (attr.getCornerRadius() + attr.getShadowSize());
        shadowPaint.setShader(new RadialGradient(0, 0, attr.getCornerRadius() + attr.getShadowSize(),
                attr.getShadowColors(),
                new float[]{0f, startRatio, 1f}
                , Shader.TileMode.CLAMP));
    }

    private void buildRingSectorShadow() {
        RectF innerBounds = new RectF(-attr.getCornerRadius(), -attr.getCornerRadius(),
                attr.getCornerRadius(), attr.getCornerRadius());
        RectF outerBounds = new RectF(innerBounds);
        outerBounds.inset(-attr.getShadowSize(), -attr.getShadowSize());
        cornerShadowPath.reset();

        cornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
        cornerShadowPath.moveTo(-attr.getCornerRadius(), 0);
        cornerShadowPath.rLineTo(-attr.getShadowSize(), 0);
        // outer arc
        cornerShadowPath.arcTo(outerBounds, 180f, 90f, false);
        // inner arc
        cornerShadowPath.arcTo(innerBounds, 270f, -90f, false);
        cornerShadowPath.close();
        float startRatio = attr.getCornerRadius() / (attr.getCornerRadius() + attr.getShadowSize());
        shadowPaint.setShader(new RadialGradient(0, 0, attr.getCornerRadius() + attr.getShadowSize(),
                attr.getShadowColors(),
                new float[]{0f, startRatio, 1f}
                , Shader.TileMode.CLAMP));
    }

    public void setShadowAttr(CornerShadowAttr attr) {
        this.attr = attr;
    }

    public static class Builder {

        private Context context;

        private int[] shadowColors;

        private float cornerRadius;

        private float shadowSize;

        @ShadowDirection
        private int direction;

        @CornerShadowType
        private int type;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setShadowColors(int[] shadowColors) {
            this.shadowColors = shadowColors;
            return this;
        }

        public Builder setCornerRadius(float cornerRadius) {
            this.cornerRadius = cornerRadius;
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

        public Builder setType(@CornerShadowType int type) {
            this.type = type;
            return this;
        }

        public CornerShadowView create() {
            if (shadowColors == null) {
                // 默认的颜色。由深到浅
                shadowColors = new int[]{0x43000000, 0x17000000, 0x00000000};//分别为开始颜色，中间夜色，结束颜色
            }

            // 构建 CornerShadowAttr
            CornerShadowAttr attr = new CornerShadowAttr();
            attr.setShadowColors(shadowColors);
            attr.setCornerRadius(cornerRadius);
            attr.setShadowSize(shadowSize);
            attr.setDirection(direction);
            attr.setType(type);

            // 创建 CornerShadowView
            CornerShadowView cornerShadowView = new CornerShadowView(context, attr);
            return cornerShadowView;
        }
    }

}
