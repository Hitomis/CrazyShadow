package com.hitomi.crazyshadow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.CrazyShadowDirection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(40)
                .setBaseShadowColor(Color.BLACK)
                .setImpl(CrazyShadow.IMPL_WRAPPER)
                .action(findViewById(R.id.frame_layout));


//        findViewById(R.id.frame).setBackgroundDrawable(new RoundRectShadowDrawable(Color.TRANSPARENT, 6, 20, 20));
    }
}
