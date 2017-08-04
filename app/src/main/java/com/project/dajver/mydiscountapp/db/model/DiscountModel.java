package com.project.dajver.mydiscountapp.db.model;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by gleb on 8/4/17.
 */

public class DiscountModel extends RealmObject implements Serializable {

    private int id;
    private String name;
    private String code;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
