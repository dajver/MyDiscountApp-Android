package com.project.dajver.mydiscountapp.ui.main.details.edit;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.db.DiscountController;
import com.project.dajver.mydiscountapp.etc.Constants;
import com.project.dajver.mydiscountapp.etc.TransitionHelper;
import com.project.dajver.mydiscountapp.etc.parser.model.DataDetailsModel;
import com.project.dajver.mydiscountapp.ui.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gleb on 8/5/17.
 */

public class EditDetailsFragment extends BaseFragment {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.code)
    EditText code;

    private DataDetailsModel discountModel;

    @Override
    public int getItemId() {
        return R.layout.fragment_edit_details;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        discountModel = (DataDetailsModel) getActivity().getIntent().getSerializableExtra(Constants.INTENT_DISCOUNT_MODEL);
        name.setText(discountModel.getName());
        code.setText(discountModel.getCode());
    }

    @OnClick(R.id.save)
    public void onSaveClick() {
        String name = !TextUtils.isEmpty(this.name.getText().toString()) ? this.name.getText().toString() : discountModel.getName();
        String code = !TextUtils.isEmpty(this.code.getText().toString()) ? this.code.getText().toString() : discountModel.getCode();

        new DiscountController(getContext()).updateInfo(discountModel.getId(), name, code);

        TransitionHelper.finish(getContext());
    }

    @Override
    public boolean isBackButtonActive() {
        return true;
    }
}
