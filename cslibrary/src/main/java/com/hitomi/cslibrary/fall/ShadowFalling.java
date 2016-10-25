package com.hitomi.cslibrary.fall;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.hitomi.cslibrary.base.CrazyShadowAttr;
import com.hitomi.cslibrary.base.CrazyShadowDirection;
import com.hitomi.cslibrary.base.ShadowHandler;
import com.hitomi.cslibrary.base.CornerShadowView;
import com.hitomi.cslibrary.base.EdgeShadowView;

/**
 * Created by hitomi on 2016/10/19. <br/>
 *
 * 考虑到不希望改变原 View 大小的情况，而又需要阴影效果的情况下。
 * 此种方案应运而生，其原理是在原 View 根布局层面中添加阴影效果。
 * 从 Z 轴的角度看就像是沉淀在原 View 的周围一样。不过因为与原
 * View 不在一个布局层面上，所以当发生用户交互使原 View 的位置
 * 发生改变后，阴影还是会留在原来的位置。
 *
 */
public class ShadowFalling implements ShadowHandler {

    private CrazyShadowAttr attr;

    private Context context;

    private View contentView;

    private boolean init;

    public ShadowFalling(Context context, CrazyShadowAttr attr) {
        this.context = context;
        this.attr = attr;
    }

    private void addShadow() {
        addEdgeShadow();
        addCornerShadow();
    }

    private FrameLayout getParentContainer() {
        Activity activity = (Activity) context;
        ViewGroup decorView = (ViewGroup)activity.getWindow().getDecorView();
        ViewGroup viewGroup = (ViewGroup) decorView.getChildAt(0);
        return (FrameLayout) viewGroup.getChildAt(1);
    }

    private void addEdgeShadow() {
        EdgeShadowView.Builder edgeShadowBuilder = new EdgeShadowView.Builder()
                .setContext(context)
                .setShadowColors(attr.getColors())
                .setCornerRadius(attr.getCorner())
                .setShadowRadius(attr.getShadowRadius());
        FrameLayout parentLayout = getParentContainer();

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
        FrameLayout parentLayout = getParentContainer();

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
        if (attr.getBackground() !=0)
        contentView.setBackgroundColor(attr.getBackground());
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
