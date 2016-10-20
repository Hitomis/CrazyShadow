package com.hitomi.cslibrary.wrap;

import android.content.Context;
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
        } else if (direction == CrazyShadowDirection.LEFTTOP || direction == CrazyShadowDirection.RIGHTTOP) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius());
            height = (int) (contentView.getHeight() - attr.getShadowRadius());
            rlp = new LayoutParams(width, height);
            if (direction == CrazyShadowDirection.LEFTTOP) {
                rlp.addRule(ALIGN_PARENT_LEFT);
            }
            rlp.addRule(ALIGN_PARENT_BOTTOM);
        } else if (direction == CrazyShadowDirection.LEFTBOTTOM || direction == CrazyShadowDirection.RIGHTBOTTOM) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius());
            height = (int) (contentView.getHeight() - attr.getShadowRadius());
            rlp = new LayoutParams(width, height);
            if (direction == CrazyShadowDirection.LEFTBOTTOM)
                rlp.addRule(ALIGN_PARENT_RIGHT);
        } if (direction == CrazyShadowDirection.LEFTTOPBOTTOM || direction == CrazyShadowDirection.RIGHTBOTTOMTOP) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius());
            height = (int) (contentView.getHeight() - attr.getShadowRadius() * 2);
            rlp = new LayoutParams(width, height);
            if (direction == CrazyShadowDirection.LEFTTOPBOTTOM) {
                rlp.addRule(ALIGN_PARENT_RIGHT);
            }
            rlp.addRule(CENTER_VERTICAL);
        } else if (direction == CrazyShadowDirection.TOPRIGHTLEFT || direction == CrazyShadowDirection.BOTTOMLEFTRIGHT) {
            width = (int) (contentView.getWidth() - attr.getShadowRadius() * 2);
            height = (int) (contentView.getHeight() - attr.getShadowRadius());
            rlp = new LayoutParams(width, height);
            if (direction == CrazyShadowDirection.TOPRIGHTLEFT) {
                rlp.addRule(ALIGN_PARENT_BOTTOM);
            }
            rlp.addRule(CENTER_HORIZONTAL);
        } else { // All
            width = (int) (contentView.getWidth() - attr.getShadowRadius() * 2);
            height = (int) (contentView.getHeight() - attr.getShadowRadius() * 2);
            rlp = new LayoutParams(width, height);
            rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        }

        shadowLayout.addView(contentView, rlp);
        shadowLayout.setLayoutParams(new LayoutParams(contentView.getWidth(), contentView.getHeight()));
        parent.addView(shadowLayout, orignalIndex);
    }

    private boolean containLeft() {
        int direction = attr.getDirection();
        return direction == CrazyShadowDirection.ALL ||
                direction == CrazyShadowDirection.LEFT ||
                direction == CrazyShadowDirection.LEFTTOP ||
                direction == CrazyShadowDirection.LEFTBOTTOM ||
                direction == CrazyShadowDirection.LEFTTOPBOTTOM ||
                direction == CrazyShadowDirection.BOTTOMLEFTRIGHT ||
                direction == CrazyShadowDirection.TOPRIGHTLEFT;
    }

    private boolean containTop() {
        int direction = attr.getDirection();
        return direction == CrazyShadowDirection.ALL ||
                direction == CrazyShadowDirection.TOP ||
                direction == CrazyShadowDirection.LEFTTOP ||
                direction == CrazyShadowDirection.RIGHTTOP ||
                direction == CrazyShadowDirection.TOPRIGHTLEFT ||
                direction == CrazyShadowDirection.LEFTTOPBOTTOM ||
                direction == CrazyShadowDirection.RIGHTBOTTOMTOP;
    }

    private boolean containRight() {
        int direction = attr.getDirection();
        return direction == CrazyShadowDirection.ALL ||
                direction == CrazyShadowDirection.RIGHT ||
                direction == CrazyShadowDirection.RIGHTTOP ||
                direction == CrazyShadowDirection.RIGHTBOTTOM ||
                direction == CrazyShadowDirection.RIGHTBOTTOMTOP ||
                direction == CrazyShadowDirection.TOPRIGHTLEFT ||
                direction == CrazyShadowDirection.BOTTOMLEFTRIGHT;
    }

    private boolean containBottom() {
        int direction = attr.getDirection();
        return direction == CrazyShadowDirection.ALL ||
                direction == CrazyShadowDirection.BOTTOM ||
                direction == CrazyShadowDirection.LEFTBOTTOM ||
                direction == CrazyShadowDirection.RIGHTBOTTOM ||
                direction == CrazyShadowDirection.BOTTOMLEFTRIGHT ||
                direction == CrazyShadowDirection.RIGHTBOTTOMTOP ||
                direction == CrazyShadowDirection.LEFTTOPBOTTOM;
    }

    private void addShadow() {
        addEdgeShadow();
        addCornerShadow();
    }

    private void addEdgeShadow() {
        float verHeight = contentView.getHeight() - attr.getShadowRadius() * 2;
        float horWidth = contentView.getWidth() - attr.getShadowRadius() * 2;
        float verWidth, horHeigth;
        verWidth = horHeigth = attr.getShadowRadius();
        if (attr.getCorner() != 0) {
            verHeight -= 2 * attr.getCorner();
            horWidth -= 2 * attr.getCorner();
        }
        EdgeShadowView.Builder edgeShadowBuilder = new EdgeShadowView.Builder()
                .setContext(getContext())
                .setShadowColors(attr.getColors())
                .setCornerRadius(attr.getCorner());

        if (containLeft())
        decorateLeft(verHeight, verWidth, edgeShadowBuilder);

        if (containTop())
        decorateTop(horWidth, horHeigth, edgeShadowBuilder);

        if (containRight())
        decorateRight(verHeight, verWidth, edgeShadowBuilder);

        if (containBottom())
        decorateBottom(horWidth, horHeigth, edgeShadowBuilder);
    }

    private void decorateLeft(float verHeight, float verWidth, EdgeShadowView.Builder edgeShadowBuilder) {
        EdgeShadowView leftEdgeShadow = edgeShadowBuilder
                .setShadowRadius(verWidth)
                .setShadowSize(verHeight)
                .setDirection(CrazyShadowDirection.LEFT)
                .create();
        LayoutParams leftRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        leftRlp.addRule(ALIGN_PARENT_LEFT);
        leftRlp.addRule(CENTER_VERTICAL);
        addView(leftEdgeShadow, leftRlp);
    }

    private void decorateTop(float horWidth, float horHeigth, EdgeShadowView.Builder edgeShadowBuilder) {
        // 放在 contetnView 的 top 位置
        EdgeShadowView topEdgeShadow = edgeShadowBuilder
                .setShadowRadius(horHeigth)
                .setShadowSize(horWidth)
                .setDirection(CrazyShadowDirection.TOP)
                .create();
        LayoutParams topRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        topRlp.addRule(ALIGN_PARENT_TOP);
        topRlp.addRule(CENTER_HORIZONTAL);
        addView(topEdgeShadow, topRlp);
    }

    private void decorateRight(float verHeight, float verWidth, EdgeShadowView.Builder edgeShadowBuilder) {
        // 放在 contetnView 的 Right 位置
        EdgeShadowView rightEdgeShadow = edgeShadowBuilder
                .setShadowRadius(verWidth)
                .setShadowSize(verHeight)
                .setDirection(CrazyShadowDirection.RIGHT)
                .create();
        LayoutParams rightRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rightRlp.addRule(ALIGN_PARENT_RIGHT);
        rightRlp.addRule(CENTER_VERTICAL);
        addView(rightEdgeShadow, rightRlp);
    }

    private void decorateBottom(float horWidth, float horHeigth, EdgeShadowView.Builder edgeShadowBuilder) {
        // 放在 contetnView 的 bottom 位置
        EdgeShadowView bottomEdgeShadow = edgeShadowBuilder
                .setShadowRadius(horHeigth)
                .setShadowSize(horWidth)
                .setDirection(CrazyShadowDirection.BOTTOM)
                .create();
        LayoutParams bottomRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomRlp.addRule(CENTER_HORIZONTAL);
        bottomRlp.addRule(ALIGN_PARENT_BOTTOM);
        addView(bottomEdgeShadow, bottomRlp);
    }

    private void addCornerShadow() {
        int type;
        float shadowSize, cornerRadius;
        if (attr.getCorner() == 0) { // contentView 为普通矩形
            type = CornerShadowType.SECTOR;
            shadowSize = cornerRadius = attr.getShadowRadius() / 2;
        } else { // contentView 为圆角矩形
            type = CornerShadowType.RINGSECTOR;
            shadowSize = attr.getShadowRadius();
            cornerRadius = attr.getCorner();
        }
        CornerShadowView.Builder cornerShadowbuilder = new CornerShadowView.Builder()
                .setContext(getContext())
                .setShadowColors(attr.getColors())
                .setType(type)
                .setShadowSize(shadowSize)
                .setCornerRadius(cornerRadius);

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
                .setDirection(CrazyShadowDirection.LEFTTOP)
                .create();
        LayoutParams leftTopRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        leftTopRlp.addRule(ALIGN_PARENT_TOP);
        leftTopRlp.addRule(ALIGN_PARENT_LEFT);
        addView(leftTopCornerShadow, leftTopRlp);
    }

    private void decorateRightTop(CornerShadowView.Builder cornerShadowbuilder) {
        CornerShadowView rightTopCornerShadow = cornerShadowbuilder
                .setDirection(CrazyShadowDirection.RIGHTTOP)
                .create();
        LayoutParams rightTopRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        rightTopRlp.addRule(ALIGN_PARENT_TOP);
        rightTopRlp.addRule(ALIGN_PARENT_RIGHT);
        addView(rightTopCornerShadow, rightTopRlp);
    }

    private void decorateRightBottom(CornerShadowView.Builder cornerShadowbuilder) {
        // 放在 contentView 的 RightBotom 位置
        CornerShadowView RightBottomCornerShadow = cornerShadowbuilder
                .setDirection(CrazyShadowDirection.RIGHTBOTTOM)
                .create();
        LayoutParams rightBottomRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        rightBottomRlp.addRule(ALIGN_PARENT_BOTTOM);
        rightBottomRlp.addRule(ALIGN_PARENT_RIGHT);
        addView(RightBottomCornerShadow, rightBottomRlp);
    }

    private void decorateLeftBottom(CornerShadowView.Builder cornerShadowbuilder) {
        // 放在 contentView 的 LeftBotom 位置
        CornerShadowView leftBottomCornerShadow = cornerShadowbuilder
                .setDirection(CrazyShadowDirection.LEFTBOTTOM)
                .create();
        LayoutParams leftBottomRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        leftBottomRlp.addRule(ALIGN_PARENT_BOTTOM);
        leftBottomRlp.addRule(ALIGN_PARENT_LEFT);
        addView(leftBottomCornerShadow, leftBottomRlp);
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

    @Override
    public void makeShadow(View view, CrazyShadowAttr attr) {
        this.attr = attr;
        contentView = view;
        init = true;
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(measureListener);
    }

}
