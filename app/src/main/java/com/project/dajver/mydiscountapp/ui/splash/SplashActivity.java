package com.project.dajver.mydiscountapp.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.etc.TransitionHelper;

import static com.project.dajver.mydiscountapp.etc.Constants.SPLASH_TIME_OUT;

/**
 * Created by gleb on 8/5/17.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(() -> TransitionHelper.setMainIntent(SplashActivity.this), SPLASH_TIME_OUT);
    }
}
