package com.project.dajver.mydiscountapp.etc.parser;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.dajver.mydiscountapp.etc.StoreDataFromAssetsHelper;
import com.project.dajver.mydiscountapp.etc.parser.model.DataDetailsModel;
import com.project.dajver.mydiscountapp.etc.parser.model.DataModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by gleb on 8/4/17.
 */

public class ParseJSONHelper {

    public static List<DataDetailsModel> getData(Context context) {
        DataModel dataModel = new DataModel();
        try {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<DataModel>(){}.getType();
            dataModel = gson.fromJson(StoreDataFromAssetsHelper.get(context), collectionType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataModel.getSchemes();
    }
}
