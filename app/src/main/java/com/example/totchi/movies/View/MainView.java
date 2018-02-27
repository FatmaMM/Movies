package com.example.totchi.movies.View;


import com.example.totchi.movies.Model.Movie;
import com.example.totchi.movies.Model.Trailer;

import java.util.List;

public interface MainView {
    //on network fail
    void onFail();

    //show download progress bar
    void showProgress();

    //hide progress bar when the results appear
    void hideProgress();

    void showMoviesList(List<Movie> movies);

    void showTrailsList(List<Trailer> list);
}
