package com.example.totchi.movies.Model.Favouites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.totchi.movies.Model.Favouites.FavouriteConteract.FavoriteEntry;
import com.example.totchi.movies.Model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDbHelper extends SQLiteOpenHelper {
    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "favorite.db";
    /**
     * Database version. If we change the database schema, we must increment the database version.
     **/
    private static final int DATABASE_VERSION = 1;

    public static final String LOGTAG = "FAVORITE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //open database
    public void open() {
        Log.i(LOGTAG, "Database Opened");
        db = dbhandler.getWritableDatabase();
    }

    //close databae
    public void close() {
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    //create favourites movie table
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE IF NOT EXISTS " + FavoriteEntry.TABLE_NAME + " (" +
                FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteEntry.COLUMN_MOVIEID + " INTEGER NOT NULL UNIQUE ," +
                FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_RATING + " REAL NOT NULL, " +
                FavoriteEntry.COLUMN_YEAR + " INTEGER, " +
                FavoriteEntry.COLUMN_LANGUAGE + " TEXT, " +
                FavoriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_BACK_POSTER_PATH + " TEXT , " +
                FavoriteEntry.COLUMN_OVERVIEW + " TEXT NOT NULL " +
                "); ";

        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // on upgrade drop older tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME);
        // create new table
        onCreate(sqLiteDatabase);

    }

    //add favourite movies into database
    public void addFavorite(Movie movie) {
        //get writable database to update the data
        SQLiteDatabase db = this.getWritableDatabase();
        //create database
        onCreate(db);
        /**
         * insert favourites in the database with the given content values.
         */
        ContentValues values = new ContentValues();
        values.put(FavoriteEntry.COLUMN_MOVIEID, movie.getId());
        values.put(FavoriteEntry.COLUMN_TITLE, movie.getTitle());
        values.put(FavoriteEntry.COLUMN_RATING, movie.getVoteAverage());
        values.put(FavoriteEntry.COLUMN_YEAR, movie.getReleaseDate().substring(0, 4));
        values.put(FavoriteEntry.COLUMN_LANGUAGE, movie.getOriginalLanguage());
        values.put(FavoriteEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(FavoriteEntry.COLUMN_BACK_POSTER_PATH, movie.getBackdropPath());
        values.put(FavoriteEntry.COLUMN_OVERVIEW, movie.getOverview());

        /**
         * insert data without duplicate
         */
        db.insertWithOnConflict(FavoriteEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        //close database
        db.close();
    }

    /**
     * delete a movie with given id from the database
     *
     * @param id
     */
    public void deleteFavorite(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoriteEntry.TABLE_NAME, FavoriteEntry.COLUMN_MOVIEID + "=" + id, null);
    }

    /**
     * get favourites movies list
     *
     * @return
     */
    public List<Movie> getAllFavorite() {
        //select all columns
        String[] columns = {
                FavoriteEntry._ID,
                FavoriteEntry.COLUMN_MOVIEID,
                FavoriteEntry.COLUMN_TITLE,
                FavoriteEntry.COLUMN_RATING,
                FavoriteEntry.COLUMN_YEAR,
                FavoriteEntry.COLUMN_LANGUAGE,
                FavoriteEntry.COLUMN_POSTER_PATH,
                FavoriteEntry.COLUMN_BACK_POSTER_PATH,
                FavoriteEntry.COLUMN_OVERVIEW
        };
        //ASC sort order
        String sortOrder = FavoriteEntry._ID + " ASC";
        List<Movie> favoriteList = new ArrayList<>();
        //get readable database to update the data
        SQLiteDatabase db = this.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM favourites"
        // with ASC sort order to get a Cursor that contains all rows from the favourites table. The cursor
        // could contain multiple rows of the favourites table table.
        Cursor cursor = db.query(FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        // Use that index to extract the values of the favourite
        // at the current row the cursor is on.
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_MOVIEID))));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_TITLE)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_RATING))));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_YEAR)));
                movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_LANGUAGE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_POSTER_PATH)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_BACK_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_OVERVIEW)));

                favoriteList.add(movie);

            } while (cursor.moveToNext());
        }
        //close cursor
        cursor.close();
        //close database
        db.close();
        //return favorite List
        return favoriteList;
    }

}