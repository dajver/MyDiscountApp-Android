package com.project.dajver.mydiscountapp.ui.main.details;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.etc.BarcodeGenerator;
import com.project.dajver.mydiscountapp.etc.Constants;
import com.project.dajver.mydiscountapp.etc.parser.model.DataDetailsModel;
import com.project.dajver.mydiscountapp.ui.BaseFragment;

import butterknife.BindView;

/**
 * Created by gleb on 8/4/17.
 */

public class DiscountDetailsFragment extends BaseFragment {

    @BindView(R.id.image)
    ImageView cardImage;
    @BindView(R.id.code)
    TextView cardCode;

    @Override
    public int getItemId() {
        return R.layout.fragment_discount_details;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        DataDetailsModel discountModel = (DataDetailsModel) getActivity().getIntent().getSerializableExtra(Constants.INTENT_DISCOUNT_MODEL);
        cardImage.setImageBitmap(generateBarcode(discountModel.getCode()));
        cardCode.setText(discountModel.getCode());

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(discountModel.getName());

        Settings.System.putInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 20);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.screenBrightness = 1;
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public boolean isBackButtonActive() {
        return true;
    }

    private Bitmap generateBarcode(String code) {
        Bitmap bitmap = null;
        try {
            bitmap = BarcodeGenerator.encodeAsBitmap(code, BarcodeFormat.CODE_128, 600, 300);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
