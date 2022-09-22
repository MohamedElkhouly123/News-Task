package com.example.newstaskapp.view.main.views.activities;

import static com.example.newstaskapp.view.main.utils.HelperMethod.replaceFragmentWithAnimation;
import static com.example.newstaskapp.view.main.utils.HelperMethod.setStatusBar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.newstaskapp.R;
import com.example.newstaskapp.view.main.views.fragments.splashUi.SecondSplashFragment;

public class SplashCycleActivity extends BaseActivity {

    private TextView logo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_splash_activity);

        initViewThenNext();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initViewThenNext() {
        logo = findViewById(R.id.main_splash_logo_tv);
        logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.logo_apper_01));
       setStatusBar(this,R.color.white);
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                replaceFragmentWithAnimation(getSupportFragmentManager(), R.id.container, new SecondSplashFragment(), "r");
                startActivity(new Intent(SplashCycleActivity.this, AdMobActivityActivity.class));
            }
        };
        handler.postDelayed(r, 2500);
    }

//    @Override
//    public void onBackPressed() {
//        finish();
//    }
}