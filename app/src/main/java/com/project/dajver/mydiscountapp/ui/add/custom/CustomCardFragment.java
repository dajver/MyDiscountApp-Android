package com.project.dajver.mydiscountapp.ui.add.custom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.db.DiscountController;
import com.project.dajver.mydiscountapp.etc.ImageFilePathUtils;
import com.project.dajver.mydiscountapp.etc.TransitionHelper;
import com.project.dajver.mydiscountapp.ui.BaseFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Created by gleb on 8/5/17.
 */

public class CustomCardFragment extends BaseFragment {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.cardImg)
    ImageView cardImg;

    private String imagePath;
    private String cardTitle;
    private String cardCode;

    @Override
    public int getItemId() {
        return R.layout.fragment_custom_card;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) { }

    @OnClick(R.id.save)
    public void onSaveClick() {
        cardTitle = this.name.getText().toString();
        cardCode = this.code.getText().toString();

        if(!TextUtils.isEmpty(cardTitle) || !TextUtils.isEmpty(cardCode)) {
            DiscountController discountController = new DiscountController(getContext());
            discountController.addDiscount(cardTitle, cardCode, imagePath);

            TransitionHelper.setDetailsIntent(getContext(), discountController.getCurrentId(), true);
            TransitionHelper.finish(getContext());
        } else
            Toast.makeText(context, getString(R.string.add_custom_card_error), Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.barcodeScanner)
    public void onBarcodeScannerClick() {
        IntentIntegrator.forSupportFragment(this).initiateScan();
    }

    @OnClick(R.id.cardImg)
    public void onCardImageClick() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle(getString(R.string.profile_dialog_title));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.select_dialog_item);
        arrayAdapter.add(getString(R.string.profile_dialog_photo_library));
        arrayAdapter.add(getString(R.string.profile_dialog_take_photo));

        builderSingle.setNegativeButton(getString(R.string.profile_dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        RxImagePicker.with(getContext()).requestMultipleImages().subscribe(new Consumer<List<Uri>>() {
                            @Override
                            public void accept(@NonNull List<Uri> uris) throws Exception {
                                imagePath = ImageFilePathUtils.getPath(context, uris.get(0));
                                Picasso.with(context).load(new File(imagePath.toString())).into(cardImg);
                            }
                        });
                        break;
                    case 1:
                        RxImagePicker.with(context).requestImage(Sources.CAMERA).subscribe(new Consumer<Uri>() {
                            @Override
                            public void accept(@NonNull Uri uri) throws Exception {
                                imagePath = ImageFilePathUtils.getPath(context, uri);
                                Picasso.with(context).load(new File(imagePath.toString())).into(cardImg);
                            }
                        });
                        break;
                }
            }
        });
        builderSingle.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode != RESULT_CANCELED) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                code.setText(scanningResult.getContents());
            }
        }
    }

    @Override
    public boolean isBackButtonActive() {
        return true;
    }
}
