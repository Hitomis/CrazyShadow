package com.hitomi.cslibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.hitomi.cslibrary.wrap.CornerShadowType;
import com.hitomi.cslibrary.wrap.CornerShadowView;
import com.hitomi.cslibrary.wrap.EdgeShadowView;


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
        float verHeight = contentView.getHeight() - shadowRadius * 2;
        float horWidth = contentView.getWidth() - shadowRadius * 2;
        float verWidth, horHeigth;
        verWidth = horHeigth = shadowRadius;

        EdgeShadowView.Builder edgeShadowBuilder = new EdgeShadowView.Builder();
        CornerShadowView.Builder cornerShadowbuilder = new CornerShadowView.Builder();

        // 放在 contetnView 的 lfet 位置
        EdgeShadowView leftEdgeShadow = edgeShadowBuilder.setContext(getContext())
                .setShadowRadius(verWidth)
                .setShadowSize(verHeight)
                .setDirection(ShadowDirection.LEFT)
                .create();
        RelativeLayout.LayoutParams leftRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        leftRlp.addRule(ALIGN_PARENT_LEFT);
        leftRlp.addRule(CENTER_VERTICAL);
        addView(leftEdgeShadow, leftRlp);

        // 放在 contetnView 的 top 位置
        EdgeShadowView topEdgeShadow = edgeShadowBuilder
                .setShadowRadius(horHeigth)
                .setShadowSize(horWidth)
                .setDirection(ShadowDirection.TOP)
                .create();
        RelativeLayout.LayoutParams topRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        topRlp.addRule(ALIGN_PARENT_TOP);
        topRlp.addRule(CENTER_HORIZONTAL);
        addView(topEdgeShadow, topRlp);

        // 放在 contetnView 的 Right 位置
        EdgeShadowView rightEdgeShadow = edgeShadowBuilder
                .setShadowRadius(verWidth)
                .setShadowSize(verHeight)
                .setDirection(ShadowDirection.RIGHT)
                .create();
        RelativeLayout.LayoutParams rightRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rightRlp.addRule(ALIGN_PARENT_RIGHT);
        rightRlp.addRule(CENTER_VERTICAL);
        addView(rightEdgeShadow, rightRlp);

        // 放在 contetnView 的 bottom 位置
        EdgeShadowView bottomEdgeShadow = edgeShadowBuilder
                .setShadowRadius(horHeigth)
                .setShadowSize(horWidth)
                .setDirection(ShadowDirection.BOTTOM)
                .create();
        RelativeLayout.LayoutParams bottomRlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomRlp.addRule(CENTER_HORIZONTAL);
        bottomRlp.addRule(ALIGN_PARENT_BOTTOM);
        addView(bottomEdgeShadow, bottomRlp);

        // 放在 contentView 的 LeftTop 位置
        CornerShadowView leftTopCornerShadow = cornerShadowbuilder.setContext(getContext())
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
        CornerShadowView rightTopCornerShadow = cornerShadowbuilder
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
        CornerShadowView RightBottomCornerShadow = cornerShadowbuilder
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
        CornerShadowView leftBottomCornerShadow = cornerShadowbuilder
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
