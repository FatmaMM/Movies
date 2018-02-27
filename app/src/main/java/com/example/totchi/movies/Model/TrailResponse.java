package com.example.totchi.movies.Model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailResponse {
    /**
     * The @SerializedName annotation is needed to map the JSON id object and results with our results trailers list and id
     */
    @SerializedName("id")
    private int id_trailer;
    @SerializedName("results")
    private List<Trailer> results;

    public int getId_trailer() {
        return id_trailer;
    }

    public List<Trailer> getResults() {
        return results;
    }
}
