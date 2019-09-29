package com.example.naamkaran;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreferences {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreferences() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<NameDisplay> favorites) {
        android.content.SharedPreferences settings;
        android.content.SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, NameDisplay nameDisplay) {
        List<NameDisplay> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<NameDisplay>();
        favorites.add(nameDisplay);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, NameDisplay product) {
        ArrayList<NameDisplay> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(product);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<NameDisplay> getFavorites(Context context) {
        android.content.SharedPreferences settings;
        List<NameDisplay> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            NameDisplay[] favoriteItems = gson.fromJson(jsonFavorites,
                    NameDisplay[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<NameDisplay>(favorites);
        } else
            return null;

        return (ArrayList<NameDisplay>) favorites;
    }
}
