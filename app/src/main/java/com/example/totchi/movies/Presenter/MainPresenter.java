package com.example.totchi.movies.Presenter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.totchi.movies.BuildConfig;
import com.example.totchi.movies.MainActivity;
import com.example.totchi.movies.Model.MovieApiservices;
import com.example.totchi.movies.Model.TrailResponse;
import com.example.totchi.movies.Model.Values;
import com.example.totchi.movies.Model.Movie;
import com.example.totchi.movies.Model.MovieResponse;
import com.example.totchi.movies.R;
import com.example.totchi.movies.View.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainPresenter implements PresenterInterface {

    private static final String TAG = MainActivity.class.getSimpleName();
    MainView netWork;
    MovieApiservices movieApiService;

    Retrofit retrofit = null;
    Context context;

    Call<MovieResponse> call;
    List<Movie> movies;


    /**
     * @param context
     * @param netWork
     */
    public MainPresenter(Context context, MainView netWork) {
        this.context = context;
        this.netWork = netWork;
    }

    public MainPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onItemClicked(Intent intent, Movie mItem) {
        intent.putExtra("id", mItem.getId());
        intent.putExtra("name", mItem.getTitle());
        intent.putExtra("year", mItem.getReleaseDate());
        intent.putExtra("language", mItem.getOriginalLanguage());
        intent.putExtra("rate", mItem.getVoteAverage().toString());
        intent.putExtra("overView", mItem.getOverview());
        intent.putExtra("poster", mItem.getPosterPath());
        intent.putExtra("image", mItem.getBackdropPath());
        context.startActivity(intent);
    }

    @Override
    public void Save(Intent intent) {

    }

    @Override
    public void delete() {

    }

    @Override
    public void connectToGetMovies(String sortedtype) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(new Values().BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        // Create a very simple REST adapter which points the Movie DB API endpoint.
        movieApiService = retrofit.create(MovieApiservices.class);

        if (sortedtype.equalsIgnoreCase(context.getString(R.string.popular))) {
            //get Popular movies
            call = movieApiService.getPopularMovies(BuildConfig.API_KEY);

        } else {
            //get Top rated movies
            call = movieApiService.getTopRatedMovies(BuildConfig.API_KEY);
        }
        getMovies(call);
    }

    @Override
    public void getMovies(Call<MovieResponse> call) {

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                // The network call was a success and we got a response (list of movies)
                movies = response.body().getResults();
                netWork.hideProgress();
                netWork.showMoviesList(movies);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                netWork.hideProgress();
                netWork.onFail();
            }
        });
        return;
    }

    @Override
    public void getTrailers(Call<TrailResponse> call) {

    }
}
