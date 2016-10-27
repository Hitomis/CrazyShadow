package com.hitomi.cslibrary.wrap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.hitomi.cslibrary.base.CornerShadowView;
import com.hitomi.cslibrary.base.CrazyShadowAttr;
import com.hitomi.cslibrary.base.CrazyShadowDirection;
import com.hitomi.cslibrary.base.EdgeShadowView;
import com.hitomi.cslibrary.base.ShadowHandler;


/**
 * Created by hitomi on 2016/10/17. <br/>
 *
 * 使用包装的方式为 View 添加阴影。<br/>
 *
 * 其原理是根据配置的阴影尺寸大小来修改原 View 的大小后，在原 View 的四周
 * 附加阴影效果。<br/>
 *
 */
public class ShadowWrapper implements ShadowHandler {

    private Context context;

    private View contentView;

    private RelativeLayout shadowLayout;

    private Drawable orignalDrawable;

    private OnMeasureListener measureListener;

    private CrazyShadowAttr attr;

    private boolean init;

    public ShadowWrapper(Context context, CrazyShadowAttr attr) {
        this.context = context;
        this.attr = attr;
        measureListener = new OnMeasureListener();
    }

    private void prepareLayout() {
        ViewGroup parent = (ViewGroup) contentView.getParent();
        int orignalIndex = parent.indexOfChild(contentView);
        parent.removeView(contentView);

        shadowLayout = new RelativeLayout(context);
        shadowLayout.setLayoutParams(contentView.getLayoutParams());
        parent.addView(shadowLayout, orignalIndex);
        shadowLayout.addView(contentView, getContentViewLayoutParams());
    }

    @NonNull
    private RelativeLayout.LayoutParams getContentViewLayoutParams() {
        int width, height;
        RelativeLayout.LayoutParams rlp;
        int direction = attr.getDirection();
        if (direction == CrazyShadowDirection.LEFT || direction == CrazyShadowDirection.RIGHT) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius());
            height = contentView.getHeight();
            rlp = new RelativeLayout.LayoutParams(width, height);
            if (direction == CrazyShadowDirection.LEFT)
                rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else if (direction == CrazyShadowDirection.TOP || direction == CrazyShadowDirection.BOTTOM) {
            width = contentView.getWidth();
            height = (int) (contentView.getHeight() - attr.getShadowRadius());
            rlp = new RelativeLayout.LayoutParams(width, height);
            if (direction == CrazyShadowDirection.TOP)
                rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        } else if (direction == CrazyShadowDirection.LEFT_TOP || direction == CrazyShadowDirection.TOP_RIGHT) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius());
            height = (int) (contentView.getHeight() - attr.getShadowRadius());
            rlp = new RelativeLayout.LayoutParams(width, height);
            if (direction == CrazyShadowDirection.LEFT_TOP) {
                rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            } else {
                rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            }
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        } else if (direction == CrazyShadowDirection.BOTTOM_LEFT || direction == CrazyShadowDirection.RIGHT_BOTTOM) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius());
            height = (int) (contentView.getHeight() - attr.getShadowRadius());
            rlp = new RelativeLayout.LayoutParams(width, height);
            if (direction == CrazyShadowDirection.BOTTOM_LEFT)
                rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else if (direction == CrazyShadowDirection.BOTTOM_LEFT_TOP || direction == CrazyShadowDirection.TOP_RIGHT_BOTTOM) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius());
            height = (int) (contentView.getHeight() - attr.getShadowRadius() * 2);
            rlp = new RelativeLayout.LayoutParams(width, height);
            if (direction == CrazyShadowDirection.BOTTOM_LEFT_TOP) {
                rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
            rlp.addRule(RelativeLayout.CENTER_VERTICAL);
        } else if (direction == CrazyShadowDirection.LEFT_TOP_RIGHT || direction == CrazyShadowDirection.RIGHT_BOTTOM_LEFT) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius() * 2);
            height = (int) (contentView.getHeight() - attr.getShadowRadius());
            rlp = new RelativeLayout.LayoutParams(width, height);
            if (direction == CrazyShadowDirection.LEFT_TOP_RIGHT) {
                rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            }
            rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        } else { // All
            width = (int) (contentView.getWidth() - attr.getShadowRadius() * 2);
            height = (int) (contentView.getHeight() - attr.getShadowRadius() * 2);
            rlp = new RelativeLayout.LayoutParams(width, height);
            rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        }
        return rlp;
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

        if (attr.containLeft())
            decorateLeft(edgeShadowBuilder);

        if (attr.containTop())
            decorateTop(edgeShadowBuilder);

        if (attr.containRight())
            decorateRight(edgeShadowBuilder);

        if (attr.containBottom())
            decorateBottom(edgeShadowBuilder);
    }

    private void decorateLeft(EdgeShadowView.Builder edgeShadowBuilder) {
        RelativeLayout.LayoutParams leftRlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        float shadowSize;
        if (attr.getDirection() == CrazyShadowDirection.LEFT) {
            shadowSize = contentView.getHeight();
        } else if (attr.getDirection() == CrazyShadowDirection.ALL
                || attr.getDirection() == CrazyShadowDirection.BOTTOM_LEFT_TOP) {
            shadowSize = contentView.getHeight() - 2 * (attr.getShadowRadius() + attr.getCorner());
            leftRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        } else {
            shadowSize = contentView.getHeight() - attr.getShadowRadius() - attr.getCorner();
            if (attr.getDirection() == CrazyShadowDirection.LEFT_TOP || attr.getDirection() == CrazyShadowDirection.LEFT_TOP_RIGHT) {
                leftRlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            }
        }
        EdgeShadowView leftEdgeShadow = edgeShadowBuilder
                .setShadowSize(shadowSize)
                .setDirection(CrazyShadowDirection.LEFT)
                .create();
        shadowLayout.addView(leftEdgeShadow, leftRlp);
    }

    private void decorateTop(EdgeShadowView.Builder edgeShadowBuilder) {
        RelativeLayout.LayoutParams topRlp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        float shadowSize;
        if (attr.getDirection() == CrazyShadowDirection.TOP) {
            shadowSize = contentView.getWidth();
        } else if (attr.getDirection() == CrazyShadowDirection.ALL
                || attr.getDirection() == CrazyShadowDirection.LEFT_TOP_RIGHT) {
            shadowSize = contentView.getWidth() - 2 * (attr.getShadowRadius() + attr.getCorner());
            topRlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        } else {
            shadowSize = contentView.getWidth() - attr.getShadowRadius() - attr.getCorner();
            if (attr.getDirection() == CrazyShadowDirection.LEFT_TOP || attr.getDirection() == CrazyShadowDirection.BOTTOM_LEFT_TOP) {
                topRlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
        }
        EdgeShadowView topEdgeShadow = edgeShadowBuilder
                .setShadowSize(shadowSize)
                .setDirection(CrazyShadowDirection.TOP)
                .create();
        shadowLayout.addView(topEdgeShadow, topRlp);
    }

    private void decorateRight(EdgeShadowView.Builder edgeShadowBuilder) {
        RelativeLayout.LayoutParams rightRlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightRlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        float shadowSize;
        if (attr.getDirection() == CrazyShadowDirection.RIGHT) {
            shadowSize = contentView.getHeight();
        } else if (attr.getDirection() == CrazyShadowDirection.ALL
                || attr.getDirection() == CrazyShadowDirection.TOP_RIGHT_BOTTOM) {
            shadowSize = contentView.getHeight() - 2 * (attr.getShadowRadius() + attr.getCorner());
            rightRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        } else {
            shadowSize = contentView.getHeight() - attr.getShadowRadius() - attr.getCorner();
            if (attr.getDirection() == CrazyShadowDirection.TOP_RIGHT || attr.getDirection() == CrazyShadowDirection.LEFT_TOP_RIGHT) {
                rightRlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            }
        }
        EdgeShadowView rightEdgeShadow = edgeShadowBuilder
                .setShadowSize(shadowSize)
                .setDirection(CrazyShadowDirection.RIGHT)
                .create();
        shadowLayout.addView(rightEdgeShadow, rightRlp);
    }

    private void decorateBottom(EdgeShadowView.Builder edgeShadowBuilder) {
        RelativeLayout.LayoutParams bottomRlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomRlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        float shadowSize;
        if (attr.getDirection() == CrazyShadowDirection.BOTTOM) {
            shadowSize = contentView.getWidth();
        } else if (attr.getDirection() == CrazyShadowDirection.ALL
                || attr.getDirection() == CrazyShadowDirection.RIGHT_BOTTOM_LEFT) {
            shadowSize = contentView.getWidth() - 2 * (attr.getShadowRadius() + attr.getCorner());
            bottomRlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        } else {
            shadowSize = contentView.getWidth() - attr.getShadowRadius() - attr.getCorner();
            if (attr.getDirection() == CrazyShadowDirection.BOTTOM_LEFT || attr.getDirection() == CrazyShadowDirection.BOTTOM_LEFT_TOP) {
                bottomRlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
        }
        EdgeShadowView bottomEdgeShadow = edgeShadowBuilder
                .setShadowSize(shadowSize)
                .setDirection(CrazyShadowDirection.BOTTOM)
                .create();
        shadowLayout.addView(bottomEdgeShadow, bottomRlp);
    }

    private void addCornerShadow() {
        CornerShadowView.Builder cornerShadowBuilder = new CornerShadowView.Builder()
                .setContext(context)
                .setShadowColors(attr.getColors())
                .setShadowSize(attr.getShadowRadius())
                .setCornerRadius(attr.getCorner());

        if (attr.containLeft() && attr.containTop())
            decorateLeftTop(cornerShadowBuilder);

        if (attr.containRight() && attr.containTop())
            decorateRightTop(cornerShadowBuilder);

        if (attr.containRight() && attr.containBottom())
            decorateRightBottom(cornerShadowBuilder);

        if (attr.containLeft() && attr.containBottom())
            decorateLeftBottom(cornerShadowBuilder);
    }

    private void decorateLeftTop(CornerShadowView.Builder cornerShadowBuilder) {
        CornerShadowView leftTopCornerShadow = cornerShadowBuilder
                .setDirection(CrazyShadowDirection.LEFT_TOP)
                .create();
        RelativeLayout.LayoutParams leftTopRlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        leftTopRlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        leftTopRlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        shadowLayout.addView(leftTopCornerShadow, leftTopRlp);
    }

    private void decorateRightTop(CornerShadowView.Builder cornerShadowbuilder) {
        CornerShadowView rightTopCornerShadow = cornerShadowbuilder
                .setDirection(CrazyShadowDirection.TOP_RIGHT)
                .create();
        RelativeLayout.LayoutParams rightTopRlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        rightTopRlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        rightTopRlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        shadowLayout.addView(rightTopCornerShadow, rightTopRlp);
    }

    private void decorateRightBottom(CornerShadowView.Builder cornerShadowbuilder) {
        CornerShadowView RightBottomCornerShadow = cornerShadowbuilder
                .setDirection(CrazyShadowDirection.RIGHT_BOTTOM)
                .create();
        RelativeLayout.LayoutParams rightBottomRlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        rightBottomRlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rightBottomRlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        shadowLayout.addView(RightBottomCornerShadow, rightBottomRlp);
    }

    private void decorateLeftBottom(CornerShadowView.Builder cornerShadowbuilder) {
        CornerShadowView leftBottomCornerShadow = cornerShadowbuilder
                .setDirection(CrazyShadowDirection.BOTTOM_LEFT)
                .create();
        RelativeLayout.LayoutParams leftBottomRlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        leftBottomRlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        leftBottomRlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        shadowLayout.addView(leftBottomCornerShadow, leftBottomRlp);
    }

    @Override
    public void makeShadow(View view) {
        contentView = view;
        init = true;
        if (attr.getBackground() != 0) {
            orignalDrawable = contentView.getBackground();
            contentView.setBackgroundColor(attr.getBackground());
        }
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(measureListener);
    }

    @Override
    public void removeShadow() {
        shadowLayout.removeView(contentView);

        ViewGroup parent = (ViewGroup) shadowLayout.getParent();
        int orignalIndex = parent.indexOfChild(shadowLayout);
        parent.removeView(shadowLayout);

        contentView.setLayoutParams(shadowLayout.getLayoutParams());
        parent.addView(contentView, orignalIndex);

        if (attr.getBackground() != 0) {
            contentView.setBackgroundDrawable(orignalDrawable);
        }
    }

    @Override
    public void hideShadow() {
        setShadowViewAlpha(0);
        ViewGroup.LayoutParams contentViewLp = contentView.getLayoutParams();
        contentViewLp.width = shadowLayout.getLayoutParams().width;
        contentViewLp.height = shadowLayout.getLayoutParams().height;
        contentView.setLayoutParams(contentViewLp);
        if (attr.getBackground() != 0) {
            contentView.setBackgroundDrawable(orignalDrawable);
        }
    }

    @Override
    public void showShadow() {
        setShadowViewAlpha(1);
        contentView.setLayoutParams(getContentViewLayoutParams());
        if (attr.getBackground() != 0 && contentView != null) {
            contentView.setBackgroundColor(attr.getBackground());
        }
    }

    private void setShadowViewAlpha(int alpha) {
        int childCount = shadowLayout.getChildCount();
        View child;
        for (int i = 0; i < childCount; i++) {
            child = shadowLayout.getChildAt(i);
            if (child instanceof EdgeShadowView || child instanceof CornerShadowView) {
                child.setAlpha(alpha);
            }
        }
    }

    private class OnMeasureListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            if (init) {
                prepareLayout();
                addShadow();
                init = false;
                contentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        }
    }

}
