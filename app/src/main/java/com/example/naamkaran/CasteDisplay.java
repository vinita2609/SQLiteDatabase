package com.example.naamkaran;

import com.google.gson.annotations.SerializedName;

public class CasteDisplay {

    @SerializedName("id")
    private String id;
    @SerializedName("cat_name")
    private String cat_name;

    public CasteDisplay(String id, String cat_name) {

        this.id = id;
        this.cat_name = cat_name;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
}
}
