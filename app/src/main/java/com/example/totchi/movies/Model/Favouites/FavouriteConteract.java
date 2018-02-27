package com.example.totchi.movies.Model.Favouites;


import android.provider.BaseColumns;

public class FavouriteConteract {
    public static final class FavoriteEntry implements BaseColumns {
        //table and columns name
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_LANGUAGE = "language";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_BACK_POSTER_PATH = "backposterpath";
        public static final String COLUMN_OVERVIEW = "overview";

    }
}
