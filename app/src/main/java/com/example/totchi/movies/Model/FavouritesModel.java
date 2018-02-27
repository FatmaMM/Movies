package com.example.totchi.movies.Model;

import android.content.Context;
import android.content.Intent;

import com.example.totchi.movies.Model.Favouites.FavoriteDbHelper;

/**
 * Created by Android-2 on 2/26/2018.
 */

public class FavouritesModel implements FavouritesInterface {
    private FavoriteDbHelper favoriteDbHelper;
    Movie favorite;
    String name, yearD, language, rate, movieoverview, imagepath;
    int id;
    Context context;

    public FavouritesModel(Context context) {
        this.context = context;
        favoriteDbHelper = new FavoriteDbHelper(context);
    }

    @Override
    public void saveFavourite(Intent intent) {

        favorite = new Movie();
        favorite.setId(intent.getIntExtra("id", 1));
        favorite.setTitle(intent.getStringExtra("name"));
        favorite.setPosterPath(intent.getStringExtra("poster"));
        favorite.setBackdropPath(intent.getStringExtra("image"));
        favorite.setVoteAverage(Double.parseDouble(intent.getStringExtra("rate")));
        favorite.setOverview(intent.getStringExtra("overView"));
        favorite.setReleaseDate(intent.getStringExtra("year").substring(0, 4));
        favorite.setOriginalLanguage(intent.getStringExtra("language"));

        favoriteDbHelper.addFavorite(favorite);

    }

    @Override
    public void deleteFavorite(int id) {
        favoriteDbHelper.deleteFavorite(id);
    }
}
