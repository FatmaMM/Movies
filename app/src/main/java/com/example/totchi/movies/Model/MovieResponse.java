package com.example.totchi.movies.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    /**
     * The @SerializedName annotation is needed to map the JSON results with our results list
     */
    @SerializedName("results")
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

}
