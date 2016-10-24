package com.hitomi.cslibrary.floatable;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.hitomi.cslibrary.base.CrazyShadowAttr;
import com.hitomi.cslibrary.base.CrazyShadowDirection;
import com.hitomi.cslibrary.base.ShadowHandler;
import com.hitomi.cslibrary.base.CornerShadowView;
import com.hitomi.cslibrary.base.EdgeShadowView;

/**
 * Created by hitomi on 2016/10/19.
 */
public class ShadowFloater implements ShadowHandler {

    private CrazyShadowAttr attr;

    private Context context;

    private View contentView;

    private boolean init;

    public ShadowFloater(Context context, CrazyShadowAttr attr) {
        this.context = context;
        this.attr = attr;
    }

    private void addShadow() {
        addEdgeShadow();
        addCornerShadow();
    }

    private void addEdgeShadow() {
        EdgeShadowView.Builder edgeShadowBuilder = new EdgeShadowView.Builder()
                .setContext(context)
                .setShadowColors(attr.getColors())
                .setCornerRadius(attr.getCorner())
                .setShadowRadius(attr.getShadowRadius());
        FrameLayout parentLayout = (FrameLayout) contentView.getParent().getParent();

        if (attr.containLeft())
            decorateLeft(edgeShadowBuilder, parentLayout);

        if (attr.containTop())
            decorateTop(edgeShadowBuilder, parentLayout);

        if (attr.containRight())
            decorateRight(edgeShadowBuilder, parentLayout);

        if (attr.containBottom())
            decorateBottom(edgeShadowBuilder, parentLayout);
    }

    private void decorateLeft(EdgeShadowView.Builder edgeShadowBuilder, FrameLayout parentLayout) {
        FrameLayout.LayoutParams parentLp = new FrameLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        float shadowSize;
        float leftMargin, topMargin;
        leftMargin = contentView.getLeft() - attr.getShadowRadius();
        if (attr.getDirection() == CrazyShadowDirection.LEFT) {
            shadowSize = contentView.getHeight();
            topMargin = contentView.getTop();
        } else if (attr.getDirection() == CrazyShadowDirection.ALL
                || attr.getDirection() == CrazyShadowDirection.BOTTOM_LEFT_TOP) {
            shadowSize = contentView.getHeight() -  2 * attr.getCorner();
            topMargin = contentView.getTop() + attr.getCorner();
        } else {
            shadowSize = contentView.getHeight() - attr.getCorner();
            if (attr.getDirection() == CrazyShadowDirection.LEFT_TOP || attr.getDirection() == CrazyShadowDirection.LEFT_TOP_RIGHT) {
                topMargin = contentView.getTop() + attr.getCorner();
            } else {
                topMargin = contentView.getTop();
            }
        }
        parentLp.leftMargin = (int) leftMargin;
        parentLp.topMargin = (int) topMargin;
        parentLayout.addView(edgeShadowBuilder
                .setShadowSize(shadowSize)
                .setDirection(CrazyShadowDirection.LEFT)
                .create(), parentLp);
    }

    private void decorateTop(EdgeShadowView.Builder edgeShadowBuilder, FrameLayout parentLayout) {
        FrameLayout.LayoutParams parentLp = new FrameLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        float shadowSize;
        float leftMargin, topMargin;
        topMargin = contentView.getTop() - attr.getShadowRadius();
        if (attr.getDirection() == CrazyShadowDirection.TOP) {
            shadowSize = contentView.getWidth();
            leftMargin = contentView.getLeft();
        } else if (attr.getDirection() == CrazyShadowDirection.ALL
                || attr.getDirection() == CrazyShadowDirection.LEFT_TOP_RIGHT) {
            shadowSize = contentView.getWidth() - 2 * attr.getCorner();
            leftMargin = contentView.getLeft() + attr.getCorner();
        } else {
            shadowSize = contentView.getWidth() - attr.getCorner();
            if (attr.getDirection() == CrazyShadowDirection.LEFT_TOP || attr.getDirection() == CrazyShadowDirection.BOTTOM_LEFT_TOP) {
                leftMargin = contentView.getLeft() + attr.getCorner();
            } else {
                leftMargin = contentView.getLeft();
            }
        }
        parentLp.leftMargin = (int) leftMargin;
        parentLp.topMargin = (int) topMargin;
        parentLayout.addView(edgeShadowBuilder
                .setShadowSize(shadowSize)
                .setDirection(CrazyShadowDirection.TOP)
                .create(), parentLp);
    }

    private void decorateRight(EdgeShadowView.Builder edgeShadowBuilder, FrameLayout parentLayout) {
        FrameLayout.LayoutParams parentLp = new FrameLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        float shadowSize;
        float leftMargin, topMargin;
        leftMargin = contentView.getRight();
        if (attr.getDirection() == CrazyShadowDirection.RIGHT) {
            shadowSize = contentView.getHeight();
            topMargin = contentView.getTop();
        } else if (attr.getDirection() == CrazyShadowDirection.ALL
                || attr.getDirection() == CrazyShadowDirection.TOP_RIGHT_BOTTOM) {
            shadowSize = contentView.getHeight() - 2 * attr.getCorner();
            topMargin = contentView.getTop() + attr.getCorner();
        } else {
            shadowSize = contentView.getHeight() - attr.getCorner();
            if (attr.getDirection() == CrazyShadowDirection.TOP_RIGHT || attr.getDirection() == CrazyShadowDirection.LEFT_TOP_RIGHT) {
                topMargin = contentView.getTop() + attr.getCorner();
            } else {
                topMargin = contentView.getTop();
            }
        }
        parentLp.leftMargin = (int) leftMargin;
        parentLp.topMargin = (int) topMargin;
        parentLayout.addView(edgeShadowBuilder
                .setShadowSize(shadowSize)
                .setDirection(CrazyShadowDirection.RIGHT)
                .create(), parentLp);
    }

    private void decorateBottom(EdgeShadowView.Builder edgeShadowBuilder, FrameLayout parentLayout) {
        FrameLayout.LayoutParams parentLp = new FrameLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        float shadowSize;
        float leftMargin, topMargin;
        topMargin = contentView.getBottom();
        if (attr.getDirection() == CrazyShadowDirection.BOTTOM) {
            shadowSize = contentView.getWidth();
            leftMargin = contentView.getLeft();
        } else if (attr.getDirection() == CrazyShadowDirection.ALL
                || attr.getDirection() == CrazyShadowDirection.RIGHT_BOTTOM_LEFT) {
            shadowSize = contentView.getWidth() - 2 * attr.getCorner();
            leftMargin = contentView.getLeft() + attr.getCorner();
        } else {
            shadowSize = contentView.getWidth() - attr.getCorner();
            if (attr.getDirection() == CrazyShadowDirection.BOTTOM_LEFT || attr.getDirection() == CrazyShadowDirection.BOTTOM_LEFT_TOP) {
                leftMargin = contentView.getLeft() + attr.getCorner();
            } else {
                leftMargin = contentView.getLeft();
            }
        }
        parentLp.leftMargin = (int) leftMargin;
        parentLp.topMargin = (int) topMargin;
        parentLayout.addView(edgeShadowBuilder
                .setShadowSize(shadowSize)
                .setDirection(CrazyShadowDirection.BOTTOM)
                .create(), parentLp);
    }

    private void addCornerShadow() {
        CornerShadowView.Builder cornerShadowBuilder = new CornerShadowView.Builder()
                .setContext(context)
                .setShadowColors(attr.getColors())
                .setShadowSize(attr.getShadowRadius())
                .setCornerRadius(attr.getCorner());
        FrameLayout parentLayout = (FrameLayout) contentView.getParent().getParent();

        if (attr.containLeft() && attr.containTop())
            decorateLeftTop(cornerShadowBuilder, parentLayout);

        if (attr.containRight() && attr.containTop())
            decorateRightTop(cornerShadowBuilder, parentLayout);

        if (attr.containRight() && attr.containBottom())
            decorateRightBottom(cornerShadowBuilder, parentLayout);

        if (attr.containLeft() && attr.containBottom())
            decorateLeftBottom(cornerShadowBuilder, parentLayout);
    }

    private void decorateLeftTop(CornerShadowView.Builder cornerShadowBuilder, FrameLayout parentLayout) {
        FrameLayout.LayoutParams parentLp = new FrameLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        parentLp.leftMargin = (int) (contentView.getLeft() - attr.getShadowRadius());
        parentLp.topMargin = (int) (contentView.getTop() - attr.getShadowRadius());

        parentLayout.addView(cornerShadowBuilder
                .setDirection(CrazyShadowDirection.LEFT_TOP)
                .create(), parentLp);
    }

    private void decorateRightTop(CornerShadowView.Builder cornerShadowBuilder, FrameLayout parentLayout) {
        FrameLayout.LayoutParams parentLp = new FrameLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        parentLp.leftMargin = (int) (contentView.getRight() - attr.getCorner());
        parentLp.topMargin = (int) (contentView.getTop() - attr.getShadowRadius());

        parentLayout.addView(cornerShadowBuilder
                .setDirection(CrazyShadowDirection.TOP_RIGHT)
                .create(), parentLp);
    }

    private void decorateRightBottom(CornerShadowView.Builder cornerShadowBuilder, FrameLayout parentLayout) {
        FrameLayout.LayoutParams parentLp = new FrameLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        parentLp.leftMargin = (int) (contentView.getRight() - attr.getCorner());
        parentLp.topMargin = (int) (contentView.getBottom() - attr.getCorner());

        parentLayout.addView(cornerShadowBuilder
                .setDirection(CrazyShadowDirection.RIGHT_BOTTOM)
                .create(), parentLp);
    }

    private void decorateLeftBottom(CornerShadowView.Builder cornerShadowBuilder, FrameLayout parentLayout) {
        FrameLayout.LayoutParams parentLp = new FrameLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        parentLp.leftMargin = (int) (contentView.getLeft() - attr.getShadowRadius());
        parentLp.topMargin = (int) (contentView.getBottom() - attr.getCorner());

        parentLayout.addView(cornerShadowBuilder
                .setDirection(CrazyShadowDirection.BOTTOM_LEFT)
                .create(), parentLp);
    }

    @Override
    public void makeShadow(View view) {
        contentView = view;
        init = true;
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new OnMeasureListener());
    }

    private class OnMeasureListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            if (init) {
                addShadow();
                init = false;
                contentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        }
    }

}
