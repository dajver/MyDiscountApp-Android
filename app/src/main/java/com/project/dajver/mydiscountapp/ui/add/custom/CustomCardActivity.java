package com.project.dajver.mydiscountapp.ui.add.custom;

import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.ui.BaseActivity;

/**
 * Created by gleb on 8/5/17.
 */

public class CustomCardActivity extends BaseActivity {

    @Override
    public int getItemId() {
        return R.layout.activity_custom_card;
    }

    @Override
    public boolean isSliderActive() {
        return true;
    }
}
