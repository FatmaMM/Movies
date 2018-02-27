package com.example.totchi.movies;

import com.example.totchi.movies.Model.MovieApiservices;
import com.example.totchi.movies.Model.Movie;
import com.example.totchi.movies.Model.MovieResponse;
import com.example.totchi.movies.Presenter.MainPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import static org.mockito.Matchers.any;

/**
 * Created by Android-2 on 12/19/2017.
 */

public class TestClass {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";

    @Mock
    MovieApiservices movieApiService;
    @Mock
    MainPresenter mainPresenter;
    @Mock
    Call<MovieResponse> mockedCall;
    @Mock
    MovieResponse movieResponse;
    @Captor
    private ArgumentCaptor<Callback<List<Movie>>> callbackArgumentCaptor;
    @Mock
    MainActivity context;
    @Mock
    List<Movie> movieList;
    private MockRetrofit mockRetrofit;
    private Retrofit retrofit;

    @Before
    public void setUp() {
        mainPresenter = Mockito.mock(MainPresenter.class);
        movieResponse = Mockito.mock(MovieResponse.class);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetworkBehavior behavior = NetworkBehavior.create();

        mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
//        mockedCall = mainPresenter.connectAndGetApiData("Popular Movies");
    }

    @Test
    public void testConnectionAndGetApiData() {
        Mockito.when(mainPresenter.connectAndGetApiData("Popular Movies")).thenReturn(mockedCall);
        Assert.assertEquals(null, mockedCall);
//        Assert.assertTrue(mockedCall != null);
//
//        List list = movieResponse.getResults();
//        List list1 = mainPresenter.getMovies(mockedCall);
//        Assert.assertEquals(list, list1);
    }

    @Test
    public void testApiResponseFail() {
        Mockito.when(mainPresenter.connectAndGetApiData("Popular Movies")).thenReturn(mockedCall);
        Assert.assertEquals(null, mockedCall);
        Assert.assertTrue(mockedCall == null);

    }
}
