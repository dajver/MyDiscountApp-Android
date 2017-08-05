package com.project.dajver.mydiscountapp.ui.main.details.edit;

import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.ui.BaseActivity;

/**
 * Created by gleb on 8/5/17.
 */

public class EditDetailsActivity extends BaseActivity {

    @Override
    public int getItemId() {
        return R.layout.activity_edit_details;
    }

    @Override
    public boolean isSlidrActive() {
        return true;
    }
}
