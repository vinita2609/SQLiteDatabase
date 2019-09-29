package com.example.naamkaran.Interface;

import com.example.naamkaran.CasteDisplay;
import com.example.naamkaran.NameDisplay;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NameDataService {

    @GET("/v1/naamkaran/category_list")
    Call<CasteDisplay> getCastes(@Query("id") String id);

    @GET("/v1/naamkaran/post_list_by_cat_and_gender")
    Call<List<NameDisplay>> getNames(@Query("category_id") String category_id, @Query("gender") String gender);
}
