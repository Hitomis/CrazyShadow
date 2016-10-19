package com.hitomi.cslibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

/**
 * Created by hitomi on 2016/10/17.
 */
public class ShadowLayout extends RelativeLayout {

    private OnMeasureListener measureListener;

    private View contentView;

    private float shadowRadius = 25;

    private boolean init;

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        measureListener = new OnMeasureListener();
    }

    public void attachToView(View view) {
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

    private void prepareLayout() {
        ShadowLayout shadowLayout = ShadowLayout.this;
        ViewGroup parent = (ViewGroup) contentView.getParent();
        parent.removeView(contentView);

        int width = (int) (contentView.getWidth() - shadowRadius * 2);
        int height = (int) (contentView.getHeight() - shadowRadius * 2);
        LayoutParams rlp = new LayoutParams(width, height);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        shadowLayout.addView(contentView, rlp);

        shadowLayout.setLayoutParams(new LayoutParams(
                contentView.getWidth(), contentView.getHeight()));
        parent.addView(shadowLayout);
    }

    private void addShadow() {
        int verHeight = (int) (contentView.getHeight()  - shadowRadius * 2);
        int horWidth = (int) (contentView.getWidth() - shadowRadius * 2);
        int verWidth, horHeigth;
        verWidth = horHeigth = (int) shadowRadius;

        // 放在 contetnView 的 lfet 位置
        EdgeShadowView leftShadow = new EdgeShadowView(getContext());
        leftShadow.setDirection(ShadowDirection.LEFT);
        RelativeLayout.LayoutParams leftRlp = new LayoutParams(verWidth, verHeight);
        leftRlp.addRule(ALIGN_PARENT_LEFT);
        leftRlp.addRule(CENTER_VERTICAL);
        addView(leftShadow, leftRlp);

        // 放在 contetnView 的 top 位置
        EdgeShadowView topShadow = new EdgeShadowView(getContext());
        topShadow.setDirection(ShadowDirection.TOP);
        RelativeLayout.LayoutParams topRlp = new LayoutParams(horWidth, horHeigth);
        topRlp.addRule(ALIGN_PARENT_TOP);
        topRlp.addRule(CENTER_HORIZONTAL);
        addView(topShadow, topRlp);

        // 放在 contetnView 的 Right 位置
        EdgeShadowView rightShadow = new EdgeShadowView(getContext());
        rightShadow.setDirection(ShadowDirection.RIGHT);
        RelativeLayout.LayoutParams rightRlp = new LayoutParams(verWidth, verHeight);
        rightRlp.addRule(ALIGN_PARENT_RIGHT);
        rightRlp.addRule(CENTER_VERTICAL);
        addView(rightShadow, rightRlp);

        // 放在 contetnView 的 bottom 位置
        EdgeShadowView bottomShadow = new EdgeShadowView(getContext());
        bottomShadow.setDirection(ShadowDirection.BOTTOM);
        RelativeLayout.LayoutParams bottomRlp = new LayoutParams(horWidth, horHeigth);
        bottomRlp.addRule(CENTER_HORIZONTAL);
        bottomRlp.addRule(ALIGN_PARENT_BOTTOM);
        addView(bottomShadow, bottomRlp);

        // 放在 contentView 的 LeftTop 位置
        CornerShadowView.Builder builder = new CornerShadowView.Builder();
        CornerShadowView leftTopCornerShadow = builder.setContext(getContext())
                .setDirection(ShadowDirection.LEFTTOP)
                .setType(CornerShadowType.SECTOR)
                .setShadowSize(shadowRadius / 2)
                .setCornerRadius(shadowRadius / 2)
                .create();
        RelativeLayout.LayoutParams leftTopRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        leftTopRlp.addRule(ALIGN_PARENT_TOP);
        leftTopRlp.addRule(ALIGN_PARENT_LEFT);
        addView(leftTopCornerShadow, leftTopRlp);

        // 放在 contentView 的 RightTop 位置
        CornerShadowView rightTopCornerShadow = builder.setContext(getContext())
                .setDirection(ShadowDirection.RIGHTTOP)
                .setType(CornerShadowType.SECTOR)
                .setShadowSize(shadowRadius / 2)
                .setCornerRadius(shadowRadius / 2)
                .create();
        RelativeLayout.LayoutParams rightTopRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        rightTopRlp.addRule(ALIGN_PARENT_TOP);
        rightTopRlp.addRule(ALIGN_PARENT_RIGHT);
        addView(rightTopCornerShadow, rightTopRlp);

        // 放在 contentView 的 RightBotom 位置
        CornerShadowView RightBottomCornerShadow = builder.setContext(getContext())
                .setDirection(ShadowDirection.RIGHTBOTTOM)
                .setType(CornerShadowType.SECTOR)
                .setShadowSize(shadowRadius / 2)
                .setCornerRadius(shadowRadius / 2)
                .create();
        RelativeLayout.LayoutParams rightBottomRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        rightBottomRlp.addRule(ALIGN_PARENT_BOTTOM);
        rightBottomRlp.addRule(ALIGN_PARENT_RIGHT);
        addView(RightBottomCornerShadow, rightBottomRlp);

        // 放在 contentView 的 LeftBotom 位置
        CornerShadowView leftBottomCornerShadow = builder.setContext(getContext())
                .setDirection(ShadowDirection.LEFTBOTTOM)
                .setType(CornerShadowType.SECTOR)
                .setShadowSize(shadowRadius / 2)
                .setCornerRadius(shadowRadius / 2)
                .create();
        RelativeLayout.LayoutParams leftBottomRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        leftBottomRlp.addRule(ALIGN_PARENT_BOTTOM);
        leftBottomRlp.addRule(ALIGN_PARENT_LEFT);
        addView(leftBottomCornerShadow, leftBottomRlp);
    }
}
