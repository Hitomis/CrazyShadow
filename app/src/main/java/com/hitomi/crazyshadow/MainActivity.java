package com.hitomi.crazyshadow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View titleView;

    private View drawView0, drawView1;

    private View fallView0, fallView1;

    private View wrapView0, wrapView1;

    private View shadowView0, shadowView1;

    private CrazyShadow titleCrazyShadow;

    private CrazyShadow drawCrazyShadow0, drawCrazyShadow1;

    private CrazyShadow fallCrazyShadow0, fallCrazyShadow1;

    private CrazyShadow wrapCrazyShadow0, wrapCrazyShadow1;

    private CrazyShadow shadowCrazyShadow0, shadowCrazyShadow1;

    private boolean titleFlag = true, draw0Flag = true, draw1Flag = true, fall0Flag = true, fall1Flag = true;

    private boolean wrap0Flag = true, wrap1Flag = true, shadow0Flag = true, shadow1Flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initShadow();
        initListener();
    }

    private void initListener() {
        titleView.setOnClickListener(this);
        drawView0.setOnClickListener(this);
        drawView1.setOnClickListener(this);
        fallView0.setOnClickListener(this);
        fallView1.setOnClickListener(this);
        wrapView0.setOnClickListener(this);
        wrapView1.setOnClickListener(this);
        shadowView0.setOnClickListener(this);
        shadowView1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relay_title:
                if (titleFlag) {
                    titleCrazyShadow.hide();
                } else {
                    titleCrazyShadow.show();
                }
                titleFlag = !titleFlag;
                break;
            case R.id.relay_draw0:
                if (draw0Flag) {
                    drawCrazyShadow0.hide();
                } else {
                    drawCrazyShadow0.show();
                }
                draw0Flag = !draw0Flag;
                break;
            case R.id.relay_draw1:
                if (draw1Flag) {
                    drawCrazyShadow1.hide();
                } else {
                    drawCrazyShadow1.show();
                }
                draw1Flag = !draw1Flag;
                break;
            case R.id.relay_fall0:
                if (fall0Flag) {
                    fallCrazyShadow0.hide();
                } else {
                    fallCrazyShadow0.show();
                }
                fall0Flag = !fall0Flag;
                break;
            case R.id.relay_fall1:
                if (fall1Flag) {
                    fallCrazyShadow1.hide();
                } else {
                    fallCrazyShadow1.show();
                }
                fall1Flag = !fall1Flag;
                break;
            case R.id.relay_wrap0:
                if (wrap0Flag) {
                    wrapCrazyShadow0.hide();
                } else {
                    wrapCrazyShadow0.show();
                }
                wrap0Flag = !wrap0Flag;
                break;
            case R.id.relay_wrap1:
                if (wrap1Flag) {
                    wrapCrazyShadow1.hide();
                } else {
                    wrapCrazyShadow1.show();
                }
                wrap1Flag = !wrap1Flag;
                break;
            case R.id.relay_shadow0:
                if (shadow0Flag) {
                    shadowCrazyShadow0.hide();
                } else {
                    shadowCrazyShadow0.show();
                }
                shadow0Flag = !shadow0Flag;
                break;
            case R.id.relay_shadow1:
                if (shadow1Flag) {
                    shadowCrazyShadow1.hide();
                } else {
                    shadowCrazyShadow1.show();
                }
                shadow1Flag = !shadow1Flag;
                break;
        }
    }

    private void initShadow() {
        titleCrazyShadow = new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.BOTTOM)
                .setShadowRadius(dip2Px(5))
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(titleView);

        drawCrazyShadow0 = new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dip2Px(3))
                .setBaseShadowColor(Color.RED)
                .setImpl(CrazyShadow.IMPL_DRAW)
                .action(drawView0);

        drawCrazyShadow1 = new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dip2Px(3))
                .setCorner(dip2Px(5))
                .setBackground(Color.parseColor("#96a993"))
                .setImpl(CrazyShadow.IMPL_DRAW)
                .action(drawView1);

        fallCrazyShadow0 = new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dip2Px(3))
                .setImpl(CrazyShadow.IMPL_FALL)
                .action(fallView0);

        fallCrazyShadow1 = new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dip2Px(3))
                .setCorner(dip2Px(4))
                .setImpl(CrazyShadow.IMPL_FALL)
                .action(fallView1);

        wrapCrazyShadow0 = new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dip2Px(3))
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(wrapView0);

        wrapCrazyShadow1 = new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dip2Px(5))
                .setCorner(dip2Px(8))
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(wrapView1);

        shadowCrazyShadow0 = new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dip2Px(3))
                .setBackground(Color.parseColor("#a0a0a0"))
                .setBaseShadowColor(Color.parseColor("#a0a0a0"))
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(findViewById(R.id.relay_shadow0));

        shadowCrazyShadow1 = new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.TOP)
                .setShadowRadius(dip2Px(5))
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(findViewById(R.id.relay_shadow1));

    }

    private void initView() {
        titleView = findViewById(R.id.relay_title);
        drawView0 = findViewById(R.id.relay_draw0);
        drawView1 = findViewById(R.id.relay_draw1);
        fallView0 = findViewById(R.id.relay_fall0);
        fallView1 = findViewById(R.id.relay_fall1);
        wrapView0 = findViewById(R.id.relay_wrap0);
        wrapView1 = findViewById(R.id.relay_wrap1);
        shadowView0 = findViewById(R.id.relay_shadow0);
        shadowView1 = findViewById(R.id.relay_shadow1);
    }


    public int dip2Px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
