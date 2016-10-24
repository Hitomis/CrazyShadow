package com.hitomi.cslibrary.draw;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by hitomi on 2016/10/17.
 */
public class RoundRectShadowDrawable extends Drawable {
    // used to calculate content padding
    final static double COS_45 = Math.cos(Math.toRadians(45));

    final static float SHADOW_MULTIPLIER = 1.5f;

    final int insetShadow; // extra shadow to avoid gaps between card and shadow
    final RectF bounds;
    Paint paint;
    Paint cornerShadowPaint;
    Paint edgeShadowPaint;
    float cornerRadius;
    Path cornerShadowPath;
    // updated value with inset
    float maxShadowSize;
    // actual value set by developer
    float rawMaxShadowSize;
    // multiplied value to account for shadow offset
    float shadowSize;
    // actual value set by developer
    float rawShadowSize;
    private int[] shadowColors;
    private boolean dirty = true;
    private boolean addPaddingForCorners = true;

    public RoundRectShadowDrawable(int background, int[] shadowColors, float radius, float shadowSize, float maxShadowSize) {
        insetShadow = 10;
        this.shadowColors = shadowColors;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(background);
        cornerShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        cornerShadowPaint.setStyle(Paint.Style.FILL);
        cornerRadius = (int) (radius + .5f);
        bounds = new RectF();
        edgeShadowPaint = new Paint(cornerShadowPaint);
        edgeShadowPaint.setAntiAlias(false);
        setShadowSize(shadowSize, maxShadowSize);
    }

    /**
     * Casts the value to an even integer.
     */
    private int toEven(float value) {
        int i = (int) (value + .5f);
        if (i % 2 == 1) {
            return i - 1;
        }
        return i;
    }

    public void setAddPaddingForCorners(boolean addPaddingForCorners) {
        this.addPaddingForCorners = addPaddingForCorners;
        invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
        cornerShadowPaint.setAlpha(alpha);
        edgeShadowPaint.setAlpha(alpha);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        dirty = true;
    }

    void setShadowSize(float shadowSize, float maxShadowSize) {
        if (shadowSize < 0f) {
            throw new IllegalArgumentException("Invalid shadow size " + shadowSize +
                    ". Must be >= 0");
        }
        if (maxShadowSize < 0f) {
            throw new IllegalArgumentException("Invalid max shadow size " + maxShadowSize +
                    ". Must be >= 0");
        }
        shadowSize = toEven(shadowSize);
        maxShadowSize = toEven(maxShadowSize);
        if (shadowSize > maxShadowSize) {
            shadowSize = maxShadowSize;
        }
        if (rawShadowSize == shadowSize && rawMaxShadowSize == maxShadowSize) {
            return;
        }
        rawShadowSize = shadowSize;
        rawMaxShadowSize = maxShadowSize;
        this.shadowSize = (int) (shadowSize * SHADOW_MULTIPLIER + insetShadow + .5f);
        this.maxShadowSize = maxShadowSize + insetShadow;
        dirty = true;
        invalidateSelf();
    }

    @Override
    public boolean getPadding(Rect padding) {
        int vOffset = (int) Math.ceil(calculateVerticalPadding(rawMaxShadowSize, cornerRadius,
                addPaddingForCorners));
        int hOffset = (int) Math.ceil(calculateHorizontalPadding(rawMaxShadowSize, cornerRadius,
                addPaddingForCorners));
        padding.set(hOffset, vOffset, hOffset, vOffset);
        return true;
    }

    float calculateVerticalPadding(float maxShadowSize, float cornerRadius,
                                   boolean addPaddingForCorners) {
        if (addPaddingForCorners) {
            return (float) (maxShadowSize * SHADOW_MULTIPLIER + (1 - COS_45) * cornerRadius);
        } else {
            return maxShadowSize * SHADOW_MULTIPLIER;
        }
    }

    float calculateHorizontalPadding(float maxShadowSize, float cornerRadius,
                                     boolean addPaddingForCorners) {
        if (addPaddingForCorners) {
            return (float) (maxShadowSize + (1 - COS_45) * cornerRadius);
        } else {
            return maxShadowSize;
        }
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void draw(Canvas canvas) {
        if (dirty) {
            buildComponents(getBounds());
            dirty = false;
        }
        canvas.translate(0, rawShadowSize / 2);
        drawShadow(canvas);
        canvas.translate(0, -rawShadowSize / 2);
        canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint);
    }

    private void drawShadow(Canvas canvas) {
        final float edgeShadowTop = -cornerRadius - shadowSize;
        final float inset = cornerRadius + insetShadow + rawShadowSize / 2;
        final boolean drawHorizontalEdges = bounds.width() - 2 * inset > 0;
        final boolean drawVerticalEdges = bounds.height() - 2 * inset > 0;
        // LT
        int saved = canvas.save();
        canvas.translate(bounds.left + inset, bounds.top + inset);
        canvas.drawPath(cornerShadowPath, cornerShadowPaint);
        if (drawHorizontalEdges) {
            canvas.drawRect(0, edgeShadowTop,
                    bounds.width() - 2 * inset, -cornerRadius,
                    edgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        // RB
        saved = canvas.save();
        canvas.translate(bounds.right - inset, bounds.bottom - inset);
        canvas.rotate(180f);
        canvas.drawPath(cornerShadowPath, cornerShadowPaint);
        if (drawHorizontalEdges) {
            canvas.drawRect(0, edgeShadowTop,
                    bounds.width() - 2 * inset, -cornerRadius,
                    edgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        // LB
        saved = canvas.save();
        canvas.translate(bounds.left + inset, bounds.bottom - inset);
        canvas.rotate(270f);
        canvas.drawPath(cornerShadowPath, cornerShadowPaint);
        if (drawVerticalEdges) {
            canvas.drawRect(0, edgeShadowTop,
                    bounds.height() - 2 * inset, -cornerRadius, edgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        // RT
        saved = canvas.save();
        canvas.translate(bounds.right - inset, bounds.top + inset);
        canvas.rotate(90f);
        canvas.drawPath(cornerShadowPath, cornerShadowPaint);
        if (drawVerticalEdges) {
            canvas.drawRect(0, edgeShadowTop,
                    bounds.height() - 2 * inset, -cornerRadius, edgeShadowPaint);
        }
        canvas.restoreToCount(saved);
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
        cornerShadowPaint.setShader(new RadialGradient(0, 0, cornerRadius + shadowSize,
                shadowColors,
                new float[]{0f, startRatio, 1f}
                , Shader.TileMode.CLAMP));

        // we offset the content shadowSize/2 pixels up to make it more realistic.
        // this is why edge shadow shader has some extra space
        // When drawing bottom edge shadow, we use that extra space.
        edgeShadowPaint.setShader(new LinearGradient(0, -cornerRadius + shadowSize, 0,
                -cornerRadius - shadowSize,
                shadowColors,
                new float[]{0f, .5f, 1f}, Shader.TileMode.CLAMP));
        edgeShadowPaint.setAntiAlias(false);
    }

    private void buildComponents(Rect bounds) {
        // Card is offset SHADOW_MULTIPLIER * maxShadowSize to account for the shadow shift.
        // We could have different top-bottom offsets to avoid extra gap above but in that case
        // center aligning Views inside the CardView would be problematic.
        final float verticalOffset = rawMaxShadowSize * SHADOW_MULTIPLIER;
        this.bounds.set(bounds.left + rawMaxShadowSize, bounds.top + verticalOffset,
                bounds.right - rawMaxShadowSize, bounds.bottom - verticalOffset);
        buildShadowCorners();
    }

    float getCornerRadius() {
        return cornerRadius;
    }

    void setCornerRadius(float radius) {
        if (radius < 0f) {
            throw new IllegalArgumentException("Invalid radius " + radius + ". Must be >= 0");
        }
        radius = (int) (radius + .5f);
        if (cornerRadius == radius) {
            return;
        }
        cornerRadius = radius;
        dirty = true;
        invalidateSelf();
    }

    void getMaxShadowAndCornerPadding(Rect into) {
        getPadding(into);
    }

    float getShadowSize() {
        return rawShadowSize;
    }

    void setShadowSize(float size) {
        setShadowSize(size, rawMaxShadowSize);
    }

    float getMaxShadowSize() {
        return rawMaxShadowSize;
    }

    void setMaxShadowSize(float size) {
        setShadowSize(rawShadowSize, size);
    }

    float getMinWidth() {
        final float content = 2 *
                Math.max(rawMaxShadowSize, cornerRadius + insetShadow + rawMaxShadowSize / 2);
        return content + (rawMaxShadowSize + insetShadow) * 2;
    }

    float getMinHeight() {
        final float content = 2 * Math.max(rawMaxShadowSize, cornerRadius + insetShadow
                + rawMaxShadowSize * SHADOW_MULTIPLIER / 2);
        return content + (rawMaxShadowSize * SHADOW_MULTIPLIER + insetShadow) * 2;
    }
}
