package com.example.naamkaran;

import com.google.gson.annotations.SerializedName;

public class NameDisplay {


    //  @SerializedName("genre_ids")
    // private List<Integer> genreIds = new ArrayList<Integer>();
    @SerializedName("id")
    private String id;
    @SerializedName("category_id")
    private String category_id;
    @SerializedName("name")
    private String name;
    @SerializedName("meaning")
    private String meaning;
    @SerializedName("gender")
    private String gender;


    public NameDisplay(String id, String category_id, String name, String meaning,
                       String gender) {

        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.meaning = meaning;
        this.gender = gender;

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public char charAt(int i) {
        return charAt(i);
    }
}