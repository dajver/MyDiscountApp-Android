package com.project.dajver.mydiscountapp.db;

import android.content.Context;

import com.project.dajver.mydiscountapp.db.model.DiscountModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by gleb on 8/4/17.
 */

public class DiscountController {

    private Realm realm;

    public DiscountController(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public void addDiscount(String title, String code, String image) {
        realm.beginTransaction();

        DiscountModel discountModel = realm.createObject(DiscountModel.class);
        int id = getNextKey();
        discountModel.setId(id);
        discountModel.setName(title);
        discountModel.setCode(code);
        discountModel.setImage(image);

        realm.commitTransaction();
    }

    public RealmResults<DiscountModel> getDiscounts() {
        return realm.where(DiscountModel.class).findAll();
    }

    public DiscountModel getDiscountsById(int id) {
        return realm.where(DiscountModel.class).equalTo("id", id).findFirst();
    }

    public void updateInfo(int id, String title, String code) {
        realm.beginTransaction();

        DiscountModel discountModel = realm.where(DiscountModel.class).equalTo("id", id).findFirst();
        discountModel.setName(title);
        discountModel.setCode(code);

        realm.commitTransaction();
    }

    public void removeItemById(int id) {
        realm.beginTransaction();

        RealmResults<DiscountModel> rows = realm.where(DiscountModel.class).equalTo("id", id).findAll();
        rows.clear();

        realm.commitTransaction();
    }

    private int getNextKey() {
        return realm.where(DiscountModel.class).max("id").intValue() + 1;
    }

    public int getCurrentId() {
        return getNextKey() - 1;
    }
}
