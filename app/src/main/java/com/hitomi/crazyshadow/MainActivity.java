package com.hitomi.crazyshadow;

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
                .setDirection(CrazyShadowDirection.BOTTOM)
                .setShadowRadius(10)
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(findViewById(R.id.relay_title));


        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(10)
                .setCorner(8)
                .setImpl(CrazyShadow.IMPL_FLOAT)
                .action(findViewById(R.id.frame_layout));

    }
}
