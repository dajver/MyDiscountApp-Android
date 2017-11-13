package com.project.dajver.mydiscountapp.ui.main.details;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.db.DiscountController;
import com.project.dajver.mydiscountapp.db.model.DiscountModel;
import com.project.dajver.mydiscountapp.etc.BarcodeGenerator;
import com.project.dajver.mydiscountapp.etc.Constants;
import com.project.dajver.mydiscountapp.etc.TransitionHelper;
import com.project.dajver.mydiscountapp.ui.BaseFragment;

import butterknife.BindView;

import static com.project.dajver.mydiscountapp.etc.Constants.ON_REFRESH_TIME_OUT;

/**
 * Created by gleb on 8/4/17.
 */

public class DiscountDetailsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.image)
    ImageView cardImage;
    @BindView(R.id.code)
    TextView cardCode;
    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private DiscountModel discountModel;
    private boolean isFirstStart = true;

    @Override
    public int getItemId() {
        return R.layout.fragment_discount_details;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        int id = getActivity().getIntent().getIntExtra(Constants.INTENT_DISCOUNT_ID, 0);
        discountModel = new DiscountController(getContext()).getDiscountsById(id);

        swipeRefreshLayout.setOnRefreshListener(this);
        setupViews();

        Settings.System.putInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 20);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.screenBrightness = 1;
        getActivity().getWindow().setAttributes(lp);
    }

    private void setupViews() {
        if(isAdded()) {
            cardImage.setImageBitmap(generateBarcode(discountModel.getCode()));
            cardCode.setText(discountModel.getCode());

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(discountModel.getName());
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.postDelayed(() -> {
            swipeRefreshLayout.setRefreshing(false);
            setupViews();
        }, ON_REFRESH_TIME_OUT);
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

    public void onResume() {
        super.onResume();
        if(!isFirstStart)
            onRefresh();
        isFirstStart = false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            TransitionHelper.setEditIntent(getContext(), discountModel);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
