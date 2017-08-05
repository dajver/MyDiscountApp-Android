package com.project.dajver.mydiscountapp.ui.add;

import android.content.Intent;

import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.ui.BaseActivity;

/**
 * Created by gleb on 8/4/17.
 */

public class AddDiscountActivity extends BaseActivity {

    @Override
    public int getItemId() {
        return R.layout.activity_add_discount;
    }

    @Override
    public boolean isSlidrActive() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
