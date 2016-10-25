package com.hitomi.crazyshadow;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;

public class MainActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.BOTTOM)
                .setShadowRadius(10)
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(findViewById(R.id.relay_title));

        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(6)
                .setCorner(6)
                .setBackground(Color.parseColor("#d07784"))
                .setImpl(CrazyShadow.IMPL_DRAW)
                .action(findViewById(R.id.relay_draw));

        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(6)
                .setImpl(CrazyShadow.IMPL_FALL)
                .action(findViewById(R.id.relay_fall));

        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(6)
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(findViewById(R.id.relay_wrap));

    }
}
