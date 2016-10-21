package com.hitomi.cslibrary.wrap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

import com.hitomi.cslibrary.CrazyShadowDirection;

/**
 * Created by hitomi on 2016/10/18.
 */
public class CornerShadowView extends View {

    private Paint shadowPaint;

    private Path cornerShadowPath;

    private int degrees;

    private int[] shadowColors;

    private float cornerRadius;

    private float shadowSize;

    private CornerShadowView(Context context) {
        super(context);
        init();
    }

    private void init() {
        shadowPaint = new Paint(Paint.DITHER_FLAG);
        shadowPaint.setStyle(Paint.Style.FILL);
        cornerShadowPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureSize = (int) (shadowSize + cornerRadius);
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

    private void buildRingSectorShadow() {
        RectF innerBounds = new RectF(-cornerRadius, -cornerRadius,
                cornerRadius, cornerRadius);
        RectF outerBounds = new RectF(innerBounds);
        outerBounds.inset(-shadowSize, -shadowSize);
        cornerShadowPath.reset();

        cornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
        cornerShadowPath.moveTo(-cornerRadius, 0);
        cornerShadowPath.rLineTo(-shadowSize, 0);
        // outer arc
        cornerShadowPath.arcTo(outerBounds, 180f, 90f, false);
        // inner arc
        cornerShadowPath.arcTo(innerBounds, 270f, -90f, false);
        cornerShadowPath.close();
        float startRatio = cornerRadius / (cornerRadius + shadowSize);
        shadowPaint.setShader(new RadialGradient(0, 0, cornerRadius + shadowSize,
                shadowColors,
                new float[]{0f, startRatio, 1f}
                , Shader.TileMode.CLAMP));
    }

    public void setShadowColors(int[] shadowColors) {
        this.shadowColors = shadowColors;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setShadowSize(float shadowSize) {
        this.shadowSize = shadowSize;
    }

    @CrazyShadowDirection
    public void setDirection(@CrazyShadowDirection int direction) {
        switch (direction) {
            case CrazyShadowDirection.LEFT_TOP:
                degrees = 0;
                break;
            case CrazyShadowDirection.TOP_RIGHT:
                degrees = 90;
                break;
            case CrazyShadowDirection.RIGHT_BOTTOM:
                degrees = 180;
                break;
            case CrazyShadowDirection.BOTTOM_LEFT:
                degrees = 270;
                break;
            default:
                degrees = 0;
        }
    }

    public static class Builder {

        private Context context;

        private int[] shadowColors;

        private float cornerRadius;

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

        public Builder setCornerRadius(float cornerRadius) {
            this.cornerRadius = cornerRadius;
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

        public CornerShadowView create() {
            // 创建 CornerShadowView
            CornerShadowView cornerShadowView = new CornerShadowView(context);
            cornerShadowView.setShadowColors(shadowColors);
            cornerShadowView.setCornerRadius(cornerRadius);
            cornerShadowView.setShadowSize(shadowSize);
            cornerShadowView.setDirection(direction);
            cornerShadowView.buildRingSectorShadow();
            return cornerShadowView;
        }
    }

}
