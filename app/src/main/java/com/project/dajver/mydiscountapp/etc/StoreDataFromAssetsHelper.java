package com.project.dajver.mydiscountapp.etc;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by gleb on 8/4/17.
 */

public class StoreDataFromAssetsHelper {

    public static String get(Context c) throws IOException {
        StringBuilder buf=new StringBuilder();
        InputStream json = c.getAssets().open("scheme.json");
        BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
        String str;
        while ((str=in.readLine()) != null) {
            buf.append(str);
        }
        in.close();

        return buf.toString();
    }
}
