package com.hitomi.cslibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hitomi on 2016/10/18.
 */
public class CornerShadowView extends View {

    private Paint shadowPaint;

    private Path cornerShadowPath;

    private float cornerRadius, shadowSize;

    private int[] shadowColors;

    public CornerShadowView(Context context) {
        this(context, null);
    }

    public CornerShadowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        shadowPaint.setStyle(Paint.Style.FILL);

        setSize(16, 20);
        setDirection(ShadowDirection.LEFTTOP);
    }

    public CornerShadowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 由深到浅
        shadowColors = new int[]{0x43000000, 0x17000000, 0x00000000};//分别为开始颜色，中间夜色，结束颜色
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(200, 200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(50, 50);
        canvas.drawPath(cornerShadowPath, shadowPaint);
        canvas.restore();
    }

    public void setSize(float cr, float ss) {
        cornerRadius = cr;
        shadowSize = ss;
    }

    @ShadowDirection
    public void setDirection(@ShadowDirection int direction) {
        switch (direction) {
            case ShadowDirection.LEFTTOP:
                break;
            case ShadowDirection.RIGHTTOP:
                break;
            case ShadowDirection.LEFTBOTTOM:
                break;
            case ShadowDirection.RIGHTBOTTOM:
                break;
        }
        buildShadowCorners();
        invalidate();
    }

    private void buildShadowCorners() {
        RectF innerBounds = new RectF(-cornerRadius, -cornerRadius, cornerRadius, cornerRadius);
        RectF outerBounds = new RectF(innerBounds);
        outerBounds.inset(-shadowSize, -shadowSize);

        if (cornerShadowPath == null) {
            cornerShadowPath = new Path();
        } else {
            cornerShadowPath.reset();
        }
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
        shadowPaint.setAntiAlias(false);
    }
}
