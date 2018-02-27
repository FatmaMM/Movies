package com.example.totchi.movies.Presenter;

import android.content.Intent;

import com.example.totchi.movies.Model.Movie;
import com.example.totchi.movies.Model.MovieResponse;
import com.example.totchi.movies.Model.TrailResponse;

import retrofit2.Call;

/**
 * Created by Android-2 on 2/22/2018.
 */

public interface PresenterInterface {
    void connectToGetMovies(String sortedtype);

    void getMovies(Call<MovieResponse> call);

    void getTrailers(Call<TrailResponse> call);

    void onItemClicked(Intent intent, Movie movie);

    void Save(Intent intent);

    void delete();
}
