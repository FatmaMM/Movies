package com.example.totchi.movies.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Trailer model class.
 * Used in trailer view.
 */

public class Trailer {
    /**
     * The @SerializedName annotation is needed to map the JSON keys and names for trailers
     */
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;

    public Trailer(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
