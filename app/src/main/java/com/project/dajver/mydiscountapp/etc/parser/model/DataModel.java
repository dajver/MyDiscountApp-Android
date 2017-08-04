package com.project.dajver.mydiscountapp.etc.parser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gleb on 8/4/17.
 */

public class DataModel {
    @SerializedName("schemes")
    @Expose
    private List<DataDetailsModel> schemes = null;

    public List<DataDetailsModel> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<DataDetailsModel> schemes) {
        this.schemes = schemes;
    }
}
