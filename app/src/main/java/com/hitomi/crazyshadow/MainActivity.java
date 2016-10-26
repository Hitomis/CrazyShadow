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
                titleCrazyShadow.remove();
                break;
            case R.id.relay_draw0:
                drawCrazyShadow0.remove();
                break;
            case R.id.relay_draw1:
                drawCrazyShadow1.remove();
                break;
            case R.id.relay_fall0:
                fallCrazyShadow0.remove();
                break;
            case R.id.relay_fall1:
                fallCrazyShadow1.remove();
                break;
            case R.id.relay_wrap0:
                wrapCrazyShadow0.remove();
                break;
            case R.id.relay_wrap1:
                wrapCrazyShadow1.remove();
                break;
            case R.id.relay_shadow0:
                shadowCrazyShadow0.remove();
                break;
            case R.id.relay_shadow1:
                shadowCrazyShadow1.remove();
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
