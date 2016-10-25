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
                .setShadowRadius(dip2Px(5))
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(findViewById(R.id.relay_title));


        /*********************************************************************************/
        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dip2Px(3))
                .setBaseShadowColor(Color.RED)
                .setImpl(CrazyShadow.IMPL_DRAW)
                .action(findViewById(R.id.relay_draw0));

        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dip2Px(3))
                .setCorner(dip2Px(5))
                .setBackground(Color.parseColor("#96a993"))
                .setImpl(CrazyShadow.IMPL_DRAW)
                .action(findViewById(R.id.relay_draw1));

        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dip2Px(3))
                .setImpl(CrazyShadow.IMPL_FALL)
                .action(findViewById(R.id.relay_fall0));

        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dip2Px(3))
                .setCorner(dip2Px(4))
                .setImpl(CrazyShadow.IMPL_FALL)
                .action(findViewById(R.id.relay_fall1));

        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dip2Px(3))
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(findViewById(R.id.relay_wrap0));

        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dip2Px(5))
                .setCorner(dip2Px(8))
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(findViewById(R.id.relay_wrap1));

        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.TOP)
                .setShadowRadius(dip2Px(5))
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(findViewById(R.id.relay_shadow));
    }


    public int dip2Px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
