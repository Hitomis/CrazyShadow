package com.hitomi.cslibrary.wrap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.hitomi.cslibrary.CrazyShadowAttr;
import com.hitomi.cslibrary.CrazyShadowDirection;
import com.hitomi.cslibrary.ShadowHandler;


/**
 * Created by hitomi on 2016/10/17.
 */
public class ShadowWrapper extends RelativeLayout implements ShadowHandler {

    private View contentView;

    private OnMeasureListener measureListener;

    private boolean init;

    private CrazyShadowAttr attr;

    public ShadowWrapper(Context context) {
        this(context, null);
    }

    public ShadowWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        measureListener = new OnMeasureListener();
    }

    private void prepareLayout() {
        ShadowWrapper shadowLayout = ShadowWrapper.this;
        ViewGroup parent = (ViewGroup) contentView.getParent();
        int orignalIndex = parent.indexOfChild(contentView);
        parent.removeView(contentView);
        LayoutParams rlp = getContentViewLayoutParams();
        shadowLayout.setLayoutParams(new LayoutParams(contentView.getWidth(), contentView.getHeight()));
        parent.addView(shadowLayout, orignalIndex);
        shadowLayout.addView(contentView, rlp);
    }

    @NonNull
    private LayoutParams getContentViewLayoutParams() {
        int width, height;
        LayoutParams rlp;
        int direction = attr.getDirection();
        if (direction == CrazyShadowDirection.LEFT || direction == CrazyShadowDirection.RIGHT) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius());
            height = contentView.getHeight();
            rlp = new LayoutParams(width, height);
            if (direction == CrazyShadowDirection.LEFT)
                rlp.addRule(ALIGN_PARENT_RIGHT);
        } else if (direction == CrazyShadowDirection.TOP || direction == CrazyShadowDirection.BOTTOM) {
            width = contentView.getWidth();
            height = (int) (contentView.getHeight() - attr.getShadowRadius());
            rlp = new LayoutParams(width, height);
            if (direction == CrazyShadowDirection.TOP)
                rlp.addRule(ALIGN_PARENT_BOTTOM);
        } else if (direction == CrazyShadowDirection.LEFT_TOP || direction == CrazyShadowDirection.TOP_RIGHT) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius());
            height = (int) (contentView.getHeight() - attr.getShadowRadius());
            rlp = new LayoutParams(width, height);
            if (direction == CrazyShadowDirection.LEFT_TOP) {
                rlp.addRule(ALIGN_PARENT_RIGHT);
            } else {
                rlp.addRule(ALIGN_PARENT_LEFT);
            }
            rlp.addRule(ALIGN_PARENT_BOTTOM);
        } else if (direction == CrazyShadowDirection.BOTTOM_LEFT || direction == CrazyShadowDirection.RIGHT_BOTTOM) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius());
            height = (int) (contentView.getHeight() - attr.getShadowRadius());
            rlp = new LayoutParams(width, height);
            if (direction == CrazyShadowDirection.BOTTOM_LEFT)
                rlp.addRule(ALIGN_PARENT_RIGHT);
        } else if (direction == CrazyShadowDirection.BOTTOM_LEFT_TOP || direction == CrazyShadowDirection.TOP_RIGHT_BOTTOM) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius());
            height = (int) (contentView.getHeight() - attr.getShadowRadius() * 2);
            rlp = new LayoutParams(width, height);
            if (direction == CrazyShadowDirection.BOTTOM_LEFT_TOP) {
                rlp.addRule(ALIGN_PARENT_RIGHT);
            }
            rlp.addRule(CENTER_VERTICAL);
        } else if (direction == CrazyShadowDirection.LEFT_TOP_RIGHT || direction == CrazyShadowDirection.RIGHT_BOTTOM_LEFT) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius() * 2);
            height = (int) (contentView.getHeight() - attr.getShadowRadius());
            rlp = new LayoutParams(width, height);
            if (direction == CrazyShadowDirection.LEFT_TOP_RIGHT) {
                rlp.addRule(ALIGN_PARENT_BOTTOM);
            }
            rlp.addRule(CENTER_HORIZONTAL);
        } else { // All
            width = (int) (contentView.getWidth() - attr.getShadowRadius() * 2);
            height = (int) (contentView.getHeight() - attr.getShadowRadius() * 2);
            rlp = new LayoutParams(width, height);
            rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        }
        return rlp;
    }

    private boolean containLeft() {
        int direction = attr.getDirection();
        return direction == CrazyShadowDirection.ALL ||
                direction == CrazyShadowDirection.LEFT ||
                direction == CrazyShadowDirection.LEFT_TOP ||
                direction == CrazyShadowDirection.BOTTOM_LEFT ||
                direction == CrazyShadowDirection.BOTTOM_LEFT_TOP ||
                direction == CrazyShadowDirection.RIGHT_BOTTOM_LEFT ||
                direction == CrazyShadowDirection.LEFT_TOP_RIGHT;
    }

    private boolean containTop() {
        int direction = attr.getDirection();
        return direction == CrazyShadowDirection.ALL ||
                direction == CrazyShadowDirection.TOP ||
                direction == CrazyShadowDirection.LEFT_TOP ||
                direction == CrazyShadowDirection.TOP_RIGHT ||
                direction == CrazyShadowDirection.LEFT_TOP_RIGHT ||
                direction == CrazyShadowDirection.BOTTOM_LEFT_TOP ||
                direction == CrazyShadowDirection.TOP_RIGHT_BOTTOM;
    }

    private boolean containRight() {
        int direction = attr.getDirection();
        return direction == CrazyShadowDirection.ALL ||
                direction == CrazyShadowDirection.RIGHT ||
                direction == CrazyShadowDirection.TOP_RIGHT ||
                direction == CrazyShadowDirection.RIGHT_BOTTOM ||
                direction == CrazyShadowDirection.TOP_RIGHT_BOTTOM ||
                direction == CrazyShadowDirection.LEFT_TOP_RIGHT ||
                direction == CrazyShadowDirection.RIGHT_BOTTOM_LEFT;
    }

    private boolean containBottom() {
        int direction = attr.getDirection();
        return direction == CrazyShadowDirection.ALL ||
                direction == CrazyShadowDirection.BOTTOM ||
                direction == CrazyShadowDirection.BOTTOM_LEFT ||
                direction == CrazyShadowDirection.RIGHT_BOTTOM ||
                direction == CrazyShadowDirection.RIGHT_BOTTOM_LEFT ||
                direction == CrazyShadowDirection.TOP_RIGHT_BOTTOM ||
                direction == CrazyShadowDirection.BOTTOM_LEFT_TOP;
    }

    private void addShadow() {
        addEdgeShadow();
        addCornerShadow();
    }

    private void addEdgeShadow() {
        EdgeShadowView.Builder edgeShadowBuilder = new EdgeShadowView.Builder()
                .setContext(getContext())
                .setShadowColors(attr.getColors())
                .setCornerRadius(attr.getCorner());

        if (containLeft())
            decorateLeft(edgeShadowBuilder);

        if (containTop())
            decorateTop(edgeShadowBuilder);

        if (containRight())
            decorateRight(edgeShadowBuilder);

        if (containBottom())
            decorateBottom(edgeShadowBuilder);
    }

    private void decorateLeft(EdgeShadowView.Builder edgeShadowBuilder) {
        LayoutParams leftRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        float shadowSize;
        if (attr.getDirection() == CrazyShadowDirection.LEFT) {
            shadowSize = contentView.getHeight();
        } else if (attr.getDirection() == CrazyShadowDirection.ALL
                || attr.getDirection() == CrazyShadowDirection.BOTTOM_LEFT_TOP) {
            shadowSize = contentView.getHeight() - 2 * (attr.getShadowRadius() + attr.getCorner());
            leftRlp.addRule(CENTER_VERTICAL);
        } else {
            shadowSize = contentView.getHeight() - attr.getShadowRadius() - attr.getCorner();
            if (attr.getDirection() == CrazyShadowDirection.LEFT_TOP || attr.getDirection() == CrazyShadowDirection.LEFT_TOP_RIGHT) {
                leftRlp.addRule(ALIGN_PARENT_BOTTOM);
            }
        }
        EdgeShadowView leftEdgeShadow = edgeShadowBuilder
                .setShadowRadius(attr.getShadowRadius())
                .setShadowSize(shadowSize)
                .setDirection(CrazyShadowDirection.LEFT)
                .create();
        addView(leftEdgeShadow, leftRlp);
    }

    private void decorateTop(EdgeShadowView.Builder edgeShadowBuilder) {
        LayoutParams topRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        float shadowSize;
        if (attr.getDirection() == CrazyShadowDirection.TOP) {
            shadowSize = contentView.getWidth();
        } else if (attr.getDirection() == CrazyShadowDirection.ALL
                || attr.getDirection() == CrazyShadowDirection.LEFT_TOP_RIGHT) {
            shadowSize = contentView.getWidth() - 2 * (attr.getShadowRadius() + attr.getCorner());
            topRlp.addRule(CENTER_HORIZONTAL);
        } else {
            shadowSize = contentView.getWidth() - attr.getShadowRadius() - attr.getCorner();
            if (attr.getDirection() == CrazyShadowDirection.LEFT_TOP || attr.getDirection() == CrazyShadowDirection.BOTTOM_LEFT_TOP) {
                topRlp.addRule(ALIGN_PARENT_RIGHT);
            }
        }
        EdgeShadowView topEdgeShadow = edgeShadowBuilder
                .setShadowRadius(attr.getShadowRadius())
                .setShadowSize(shadowSize)
                .setDirection(CrazyShadowDirection.TOP)
                .create();
        addView(topEdgeShadow, topRlp);
    }

    private void decorateRight(EdgeShadowView.Builder edgeShadowBuilder) {
        LayoutParams rightRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightRlp.addRule(ALIGN_PARENT_RIGHT);
        float shadowSize;
        if (attr.getDirection() == CrazyShadowDirection.RIGHT) {
            shadowSize = contentView.getHeight();
        } else if (attr.getDirection() == CrazyShadowDirection.ALL
                || attr.getDirection() == CrazyShadowDirection.TOP_RIGHT_BOTTOM) {
            shadowSize = contentView.getHeight() - 2 * (attr.getShadowRadius() + attr.getCorner());
            rightRlp.addRule(CENTER_VERTICAL);
        } else {
            shadowSize = contentView.getHeight() - attr.getShadowRadius() - attr.getCorner();
            if (attr.getDirection() == CrazyShadowDirection.TOP_RIGHT || attr.getDirection() == CrazyShadowDirection.LEFT_TOP_RIGHT) {
                rightRlp.addRule(ALIGN_PARENT_BOTTOM);
            }
        }
        EdgeShadowView rightEdgeShadow = edgeShadowBuilder
                .setShadowRadius(attr.getShadowRadius())
                .setShadowSize(shadowSize)
                .setDirection(CrazyShadowDirection.RIGHT)
                .create();
        addView(rightEdgeShadow, rightRlp);
    }

    private void decorateBottom(EdgeShadowView.Builder edgeShadowBuilder) {
        LayoutParams bottomRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomRlp.addRule(ALIGN_PARENT_BOTTOM);
        float shadowSize;
        if (attr.getDirection() == CrazyShadowDirection.BOTTOM) {
            shadowSize = contentView.getWidth();
        } else if (attr.getDirection() == CrazyShadowDirection.ALL
                || attr.getDirection() == CrazyShadowDirection.RIGHT_BOTTOM_LEFT) {
            shadowSize = contentView.getWidth() - 2 * (attr.getShadowRadius() + attr.getCorner());
            bottomRlp.addRule(CENTER_HORIZONTAL);
        } else {
            shadowSize = contentView.getWidth() - attr.getShadowRadius() - attr.getCorner();
            if (attr.getDirection() == CrazyShadowDirection.BOTTOM_LEFT || attr.getDirection() == CrazyShadowDirection.BOTTOM_LEFT_TOP) {
                bottomRlp.addRule(ALIGN_PARENT_RIGHT);
            }
        }
        EdgeShadowView bottomEdgeShadow = edgeShadowBuilder
                .setShadowRadius(attr.getShadowRadius())
                .setShadowSize(shadowSize)
                .setDirection(CrazyShadowDirection.BOTTOM)
                .create();
        addView(bottomEdgeShadow, bottomRlp);
    }

    private void addCornerShadow() {
        CornerShadowView.Builder cornerShadowbuilder = new CornerShadowView.Builder()
                .setContext(getContext())
                .setShadowColors(attr.getColors())
                .setShadowSize(attr.getShadowRadius())
                .setCornerRadius(attr.getCorner());

        if (containLeft() && containTop())
            decorateLeftTop(cornerShadowbuilder);

        if (containRight() && containTop())
            decorateRightTop(cornerShadowbuilder);

        if (containRight() && containBottom())
            decorateRightBottom(cornerShadowbuilder);

        if (containLeft() && containBottom())
            decorateLeftBottom(cornerShadowbuilder);
    }

    private void decorateLeftTop(CornerShadowView.Builder cornerShadowbuilder) {
        CornerShadowView leftTopCornerShadow = cornerShadowbuilder
                .setDirection(CrazyShadowDirection.LEFT_TOP)
                .create();
        LayoutParams leftTopRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        leftTopRlp.addRule(ALIGN_PARENT_TOP);
        leftTopRlp.addRule(ALIGN_PARENT_LEFT);
        addView(leftTopCornerShadow, leftTopRlp);
    }

    private void decorateRightTop(CornerShadowView.Builder cornerShadowbuilder) {
        CornerShadowView rightTopCornerShadow = cornerShadowbuilder
                .setDirection(CrazyShadowDirection.TOP_RIGHT)
                .create();
        LayoutParams rightTopRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        rightTopRlp.addRule(ALIGN_PARENT_TOP);
        rightTopRlp.addRule(ALIGN_PARENT_RIGHT);
        addView(rightTopCornerShadow, rightTopRlp);
    }

    private void decorateRightBottom(CornerShadowView.Builder cornerShadowbuilder) {
        CornerShadowView RightBottomCornerShadow = cornerShadowbuilder
                .setDirection(CrazyShadowDirection.RIGHT_BOTTOM)
                .create();
        LayoutParams rightBottomRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        rightBottomRlp.addRule(ALIGN_PARENT_BOTTOM);
        rightBottomRlp.addRule(ALIGN_PARENT_RIGHT);
        addView(RightBottomCornerShadow, rightBottomRlp);
    }

    private void decorateLeftBottom(CornerShadowView.Builder cornerShadowbuilder) {
        CornerShadowView leftBottomCornerShadow = cornerShadowbuilder
                .setDirection(CrazyShadowDirection.BOTTOM_LEFT)
                .create();
        LayoutParams leftBottomRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        leftBottomRlp.addRule(ALIGN_PARENT_BOTTOM);
        leftBottomRlp.addRule(ALIGN_PARENT_LEFT);
        addView(leftBottomCornerShadow, leftBottomRlp);
    }

    @Override
    public void makeShadow(View view, CrazyShadowAttr attr) {
        this.attr = attr;
        contentView = view;
        init = true;
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(measureListener);
    }

    private class OnMeasureListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            if (init) {
                prepareLayout();
                addShadow();
                init = false;
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        }
    }

}
