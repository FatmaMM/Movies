package com.example.totchi.movies.Presenter;


import android.content.Context;
import android.content.Intent;

import com.example.totchi.movies.BuildConfig;
import com.example.totchi.movies.Model.FavouritesInterface;
import com.example.totchi.movies.Model.FavouritesModel;
import com.example.totchi.movies.Model.Movie;
import com.example.totchi.movies.Model.MovieApiservices;
import com.example.totchi.movies.Model.MovieResponse;
import com.example.totchi.movies.Model.TrailResponse;
import com.example.totchi.movies.Model.Trailer;
import com.example.totchi.movies.Model.Values;
import com.example.totchi.movies.View.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Trailer presenter class to get Trailers list for a movie
 */
public class TrailersPresenter implements PresenterInterface {

    private static Retrofit retrofit = null;

    List<Trailer> trailer_list;
    MainView netWork;
    FavouritesInterface favouritesInterface;
    Context context;
    Intent intent;

    /**
     * Presenter constructor
     *  @param context
     * @param netWork
     * @param intent
     */
    public TrailersPresenter(Context context, MainView netWork, Intent intent) {
        this.context = context;
        this.netWork = netWork;
        this.intent = intent;
    }


    @Override
    public void connectToGetMovies(String sortedtype) {
        netWork.showProgress();

        //instantiate the Retrofit instance
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(new Values().BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        // Create a very simple REST adapter which points the Movie DB API endpoint.
        MovieApiservices movieApiService = retrofit.create(MovieApiservices.class);

        //Get Movie Trailers
        Call<TrailResponse> call = movieApiService.getMovieTrailers(intent.getIntExtra("id",1), BuildConfig.API_KEY);
        getTrailers(call);
    }

    @Override
    public void getMovies(Call<MovieResponse> call) {

    }

    @Override
    public void getTrailers(Call<TrailResponse> call) {
        netWork.showProgress();
        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<TrailResponse>() {
            @Override
            public void onResponse(Call<TrailResponse> call, Response<TrailResponse> response) {
                // The network call was a success and we got a response (list of trailers)
                trailer_list = response.body().getResults();
                netWork.showTrailsList(trailer_list);
                netWork.hideProgress();
            }

            @Override
            public void onFailure(Call<TrailResponse> call, Throwable t) {
                // the network call was a failure or the server send an error
                netWork.hideProgress();
                netWork.onFail();
            }
        });
    }

    @Override
    public void onItemClicked(Intent intent, Movie movie) {

    }

    @Override
    public void Save(Intent intent) {
        favouritesInterface = new FavouritesModel(context);
        favouritesInterface.saveFavourite(intent);
    }

    @Override
    public void delete() {
        favouritesInterface = new FavouritesModel(context);
        favouritesInterface.deleteFavorite(intent.getIntExtra("id",1));
    }

}
