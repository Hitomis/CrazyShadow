package com.hitomi.crazyshadow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.hitomi.cslibrary.drawable.RoundRectDrawableWithShadow;
import com.hitomi.cslibrary.ShadowLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout, frameLayouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);

        ShadowLayout shadowLayout = new ShadowLayout(this);
        shadowLayout.attachToView(frameLayout);

        findViewById(R.id.frame).setBackgroundDrawable(new RoundRectDrawableWithShadow(Color.TRANSPARENT, 6, 8, 8));
    }
}
