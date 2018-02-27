package com.example.totchi.movies.Model;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit makes it easy to connect to a REST web service by translating the API into Java interfaces.
 */

public interface MovieApiservices {
    /**
     * The @GET annotation declares that this request uses the HTTP GET method.
     * The code snippet also illustrates the usage of Retrofit’s path parameter replacement functionality.
     *
     * @param apiKey
     * @return
     */
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailResponse> getMovieTrailers(@Path("movie_id") int id, @Query("api_key") String apiKey);
}
