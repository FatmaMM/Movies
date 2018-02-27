package com.example.totchi.movies;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.example.totchi.movies.Model.MovieApiservices;
import com.example.totchi.movies.Model.Movie;
import com.example.totchi.movies.Model.MovieResponse;
import com.example.totchi.movies.Presenter.MainPresenter;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.mock.MockRetrofit;

import static org.mockito.ArgumentMatchers.any;

/**
 * Created by Android-2 on 12/19/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestRetrofitApi extends InstrumentationTestCase {

    private MockRetrofit mockRetrofit;
    private Retrofit retrofit;
    public static final String BASE_URL = "http://api.themoviedb.org/3/";

    @Mock
    MovieApiservices movieApiService;
    @Mock
    MainPresenter mainPresenter;
    @Mock
    Call<MovieResponse> mockedCall;
    MovieResponse movieResponse;
    @Captor
    private ArgumentCaptor<Callback<List<Movie>>> callbackArgumentCaptor;
    @Mock
    Context context;
    List<Movie> movieList;

    @Before
    public void setUp() throws Exception {
    }

//    @Test
//    public void testResponse() throws Exception {
//        Mockito.when(movieApiService.getPopularMovies(context.getString(R.string.popular))).thenReturn(mockedCall);
//        List<Movie> list = movieResponse.getResults();
//
//        Assert.assertEquals(list, movieList);
//        mockedCall.enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//                // The network call was a success and we got a response (list of movies)
//                List movies = response.body().getResults();
//                Assert.assertEquals(list, movies);
//            }

//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//
//            }
//        });
//    }

    //
//    @Test
//    public void http404Async() throws InterruptedException, IOException {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://api.themoviedb.org/3/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        call.enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//
//        Response<String> response = responseRef.get();
//        Assert.assertThat(response.isSuccessful()).isFalse();
//        assertEquals(response.code()).isEqualTo(404);
//        assertThat(response.errorBody().string()).isEqualTo("Hi");
////    }
//    @Test
//    public void testApiResponseFail() {
//
//        Mockito.when(movieApiService.getPopularMovies(context.getString(R.string.popular))).thenReturn(mockedCall);
//
//        Mockito.doAnswer(new Answer() {
//            @Override
//            public Void answer(InvocationOnMock invocation) throws Throwable {
//                Callback<MovieResponse> callback = invocation.getArgumentAt(0, Callback.class);
//
//                //callback.onResponse(mockedCall, Response.success(new Movie()));
//                // or callback.onResponse(mockedCall, Response.error(404. ...);
//                callback.onFailure(mockedCall, new IOException());
//
//                return null;
//            }
//        }).when(mockedCall).enqueue(any(Callback.class));

        // inject mocked ApiInterface to your presenter
        // and then mock view and verify calls (and eventually use ArgumentCaptor to access call parameters)
//    }
}
