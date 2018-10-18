package com.alimert.java.omdb.data.model.response.omdb;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchMovieResult {

    @SerializedName("Search")
    public List<Movie> search = null;
    public String totalResults;
    @SerializedName("Response")
    public String response;
    @SerializedName("Error")
    public String error;

}
