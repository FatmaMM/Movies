package com.example.totchi.movies.Model;

import android.content.Intent;

/**
 * Created by Android-2 on 2/26/2018.
 */

public interface FavouritesInterface {
    void saveFavourite(Intent intent);

    void deleteFavorite(int id);
}
