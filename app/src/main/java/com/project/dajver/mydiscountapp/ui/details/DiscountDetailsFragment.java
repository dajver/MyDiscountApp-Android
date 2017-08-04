package com.project.dajver.mydiscountapp.ui.details;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.db.model.DiscountModel;
import com.project.dajver.mydiscountapp.etc.BarcodeGenerator;
import com.project.dajver.mydiscountapp.etc.Constants;
import com.project.dajver.mydiscountapp.ui.BaseFragment;

import butterknife.BindView;

/**
 * Created by gleb on 8/4/17.
 */

public class DiscountDetailsFragment extends BaseFragment {

    @BindView(R.id.name)
    TextView cardName;
    @BindView(R.id.image)
    ImageView cardImage;

    @Override
    public int getItemId() {
        return R.layout.fragment_discount_details;
    }

    @Override
    public void onCreateView() {
        DiscountModel discountModel = (DiscountModel) getActivity().getIntent().getSerializableExtra(Constants.INTENT_DISCOUNT_MODEL);
        cardName.setText(discountModel.getName());
        cardImage.setImageBitmap(generateBarcode(discountModel.getCode()));
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
