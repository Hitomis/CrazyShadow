package com.hitomi.crazyshadow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.CrazyShadowAttr;
import com.hitomi.cslibrary.CrazyShadowDirection;
import com.hitomi.cslibrary.drawable.RoundRectShadowDrawable;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout, frameLayouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);

        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(10)
                .setImpl(CrazyShadowAttr.IMPL_WRAPPER)
                .action(frameLayout);


        findViewById(R.id.frame).setBackgroundDrawable(new RoundRectShadowDrawable(Color.TRANSPARENT, 6, 8, 8));
    }
}
