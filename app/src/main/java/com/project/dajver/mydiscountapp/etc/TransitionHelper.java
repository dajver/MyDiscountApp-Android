package com.project.dajver.mydiscountapp.etc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.db.model.DiscountModel;
import com.project.dajver.mydiscountapp.etc.parser.model.DataDetailsModel;
import com.project.dajver.mydiscountapp.ui.add.AddDiscountActivity;
import com.project.dajver.mydiscountapp.ui.main.MainActivity;
import com.project.dajver.mydiscountapp.ui.main.details.DiscountDetailsActivity;

/**
 * Created by gleb on 8/5/17.
 */

public class TransitionHelper {

    public static void setDetailsIntent(Context context, DiscountModel discountModel) {
        DataDetailsModel dataDetailsModel = new DataDetailsModel();
        dataDetailsModel.setId(discountModel.getId());
        dataDetailsModel.setCode(discountModel.getCode());
        dataDetailsModel.setName(discountModel.getName());
        dataDetailsModel.setImage(discountModel.getImage());

        Intent intent = new Intent(context, DiscountDetailsActivity.class);
        intent.putExtra(Constants.INTENT_DISCOUNT_MODEL, dataDetailsModel);
        context.startActivity(intent);
        setAnimation(context);
    }

    public static void setAddIntent(Context context) {
        context.startActivity(new Intent(context, AddDiscountActivity.class));
        setAnimation(context);
    }

    public static void setDetailsIntent(Context context, DataDetailsModel dataDetailsModel, String code) {
        DataDetailsModel discountModel = new DataDetailsModel();
        discountModel.setId(dataDetailsModel.getId());
        discountModel.setCode(code);
        discountModel.setName(dataDetailsModel.getName());
        discountModel.setImage(dataDetailsModel.getImage());

        Intent i = new Intent(context, DiscountDetailsActivity.class);
        i.putExtra(Constants.INTENT_DISCOUNT_MODEL, discountModel);
        context.startActivity(i);
        setAnimation(context);
    }

    public static void finish(Context context) {
        ((Activity) context).finish();
        setAnimation(context);
    }

    public static void setMainIntent(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
        ((Activity) context).finish();
        setAnimation(context);
    }

    private static void setAnimation(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
