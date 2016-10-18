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

    private int shadowRadius = 25;

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

        int width = contentView.getWidth() - shadowRadius * 2;
        int height = contentView.getHeight() - shadowRadius * 2;
        LayoutParams rlp = new LayoutParams(width, height);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        shadowLayout.addView(contentView, rlp);

        shadowLayout.setLayoutParams(new LayoutParams(
                contentView.getWidth(), contentView.getHeight()));
        parent.addView(shadowLayout);
    }

    private void addShadow() {
        // 放在 contetnView 的 lfet 位置
        EdgeShadowView leftShadow = new EdgeShadowView(getContext());
        leftShadow.setDirection(ShadowDirection.LEFT);
        RelativeLayout.LayoutParams leftRlp = new LayoutParams(shadowRadius, contentView.getHeight()  - shadowRadius * 2);
        leftRlp.addRule(ALIGN_PARENT_LEFT);
        leftRlp.addRule(CENTER_VERTICAL);
        addView(leftShadow, leftRlp);

        // 放在 contetnView 的 top 位置
        EdgeShadowView topShadow = new EdgeShadowView(getContext());
        topShadow.setDirection(ShadowDirection.TOP);
        RelativeLayout.LayoutParams topRlp = new LayoutParams(contentView.getWidth() - shadowRadius * 2, shadowRadius);
        topRlp.addRule(ALIGN_PARENT_TOP|CENTER_HORIZONTAL);
        addView(topShadow, topRlp);

        // 放在 contetnView 的 Right 位置
        EdgeShadowView rightShadow = new EdgeShadowView(getContext());
        rightShadow.setDirection(ShadowDirection.RIGHT);
        RelativeLayout.LayoutParams rightRlp = new LayoutParams(shadowRadius, contentView.getHeight()  - shadowRadius * 2);
        rightRlp.addRule(ALIGN_PARENT_RIGHT);
        rightRlp.addRule(CENTER_VERTICAL);
        addView(rightShadow, rightRlp);

        // 放在 contetnView 的 bottom 位置
        EdgeShadowView bottomShadow = new EdgeShadowView(getContext());
        bottomShadow.setDirection(ShadowDirection.BOTTOM);
        RelativeLayout.LayoutParams bottomRlp = new LayoutParams(contentView.getWidth() - shadowRadius * 2, shadowRadius);
        bottomRlp.addRule(CENTER_HORIZONTAL);
        bottomRlp.addRule(ALIGN_PARENT_BOTTOM);
        addView(bottomShadow, bottomRlp);
    }
}
