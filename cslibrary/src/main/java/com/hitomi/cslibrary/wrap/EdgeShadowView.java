package com.hitomi.cslibrary.wrap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;

import com.hitomi.cslibrary.CrazyShadowDirection;

/**
 * Created by hitomi on 2016/10/17.
 */
public class EdgeShadowView extends View {

    private Paint shadowPaint;

    private int[] shadowColors;

    private float shadowRadius;

    private float shadowSize;

    private float cornerRadius;

    @CrazyShadowDirection
    private int direction;

    private EdgeShadowView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth, measureHeight;
        if (direction == CrazyShadowDirection.LEFT || direction == CrazyShadowDirection.RIGHT) {
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
        canvas.save();
        switch (direction) {
            case CrazyShadowDirection.TOP:
                canvas.translate(0, cornerRadius + shadowRadius);
                break;
            case CrazyShadowDirection.RIGHT:
                canvas.translate(-cornerRadius, 0);
                canvas.rotate(90);
                break;
            case CrazyShadowDirection.BOTTOM:
                canvas.translate(shadowSize, -cornerRadius);
                canvas.rotate(180);
                break;
            case CrazyShadowDirection.LEFT:
                canvas.translate(cornerRadius + shadowRadius, shadowSize);
                canvas.rotate(270f);
                break;
            default:
        }
        canvas.drawRect(0, -cornerRadius -shadowRadius, shadowSize, -cornerRadius, shadowPaint);
        canvas.restore();
    }

    private void buildEdgeShadowTool() {
        shadowPaint = new Paint();
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setShader(new LinearGradient(0, -cornerRadius + shadowRadius, 0, -cornerRadius - shadowRadius,
                shadowColors,
                new float[]{0f, .5f, 1f}, Shader.TileMode.CLAMP));
    }

    public void setShadowColors(int[] shadowColors) {
        this.shadowColors = shadowColors;
    }

    public void setShadowRadius(float shadowRadius) {
        this.shadowRadius = shadowRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setShadowSize(float shadowSize) {
        this.shadowSize = shadowSize;
    }

    @CrazyShadowDirection
    public void setDirection(@CrazyShadowDirection int direction) {
        this.direction = direction;
    }

    public static class Builder {

        private Context context;

        private int[] shadowColors;

        private float shadowRadius;

        private float shadowSize;

        private float cornerRadius;

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

        public Builder setShadowRadius(float shadowRadius) {
            this.shadowRadius = shadowRadius;
            return this;
        }

        public Builder setShadowSize(float shadowSize) {
            this.shadowSize = shadowSize;
            return this;
        }

        public Builder setCornerRadius(float cornerRadius) {
            this.cornerRadius = cornerRadius;
            return this;
        }

        public Builder setDirection(@CrazyShadowDirection int direction) {
            this.direction = direction;
            return this;
        }

        public EdgeShadowView create() {
            // 创建 EdgeShadowView
            EdgeShadowView edgeShadowView = new EdgeShadowView(context);
            edgeShadowView.setShadowColors(shadowColors);
            edgeShadowView.setShadowRadius(shadowRadius);
            edgeShadowView.setShadowSize(shadowSize);
            edgeShadowView.setCornerRadius(cornerRadius);
            edgeShadowView.setDirection(direction);
            edgeShadowView.buildEdgeShadowTool();
            return edgeShadowView;
        }
    }
}
