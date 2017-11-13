package com.project.dajver.mydiscountapp.ui.main.details;

import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.ui.BaseActivity;

/**
 * Created by gleb on 8/4/17.
 */

public class DiscountDetailsActivity extends BaseActivity {

    @Override
    public int getItemId() {
        return R.layout.activity_discount_details;
    }

    @Override
    public boolean isSliderActive() {
        return true;
    }
}
