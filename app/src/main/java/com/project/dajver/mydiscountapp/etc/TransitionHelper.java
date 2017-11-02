package com.project.dajver.mydiscountapp.etc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.db.model.DiscountModel;
import com.project.dajver.mydiscountapp.etc.parser.model.DataDetailsModel;
import com.project.dajver.mydiscountapp.ui.add.AddDiscountActivity;
import com.project.dajver.mydiscountapp.ui.add.custom.CustomCardActivity;
import com.project.dajver.mydiscountapp.ui.main.MainActivity;
import com.project.dajver.mydiscountapp.ui.main.details.DiscountDetailsActivity;
import com.project.dajver.mydiscountapp.ui.main.details.edit.EditDetailsActivity;

/**
 * Created by gleb on 8/5/17.
 */

public class TransitionHelper {

    public static void setDetailsIntent(Context context, int id, boolean isFinish) {
        Intent intent = new Intent(context, DiscountDetailsActivity.class);
        intent.putExtra(Constants.INTENT_DISCOUNT_ID, id);
        context.startActivity(intent);
        if(isFinish)
            ((Activity) context).finish();
        setAnimation(context);
    }

    public static void setAddIntent(Context context) {
        context.startActivity(new Intent(context, AddDiscountActivity.class));
        setAnimation(context);
    }

    public static void setAddCustomCardIntent(Context context) {
        context.startActivity(new Intent(context, CustomCardActivity.class));
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

    public static void setEditIntent(Context context, DiscountModel discountModel) {
        DataDetailsModel dataDetailsModel = new DataDetailsModel();
        dataDetailsModel.setId(discountModel.getId());
        dataDetailsModel.setCode(discountModel.getCode());
        dataDetailsModel.setName(discountModel.getName());
        dataDetailsModel.setImage(discountModel.getImage());

        Intent i = new Intent(context, EditDetailsActivity.class);
        i.putExtra(Constants.INTENT_DISCOUNT_MODEL, dataDetailsModel);
        context.startActivity(i);
        setAnimation(context);
    }

    private static void setAnimation(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
