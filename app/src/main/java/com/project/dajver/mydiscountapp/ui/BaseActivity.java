package com.project.dajver.mydiscountapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.dajver.mydiscountapp.R;
import com.r0adkll.slidr.Slidr;

import butterknife.ButterKnife;

/**
 * Created by gleb on 8/4/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getItemId());
        ButterKnife.bind(this);
        if(isSliderActive())
            Slidr.attach(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isSliderActive())
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public abstract int getItemId();
    public abstract boolean isSliderActive();
}
