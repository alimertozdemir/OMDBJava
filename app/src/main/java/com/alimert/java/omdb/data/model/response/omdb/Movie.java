package com.alimert.java.omdb.data.model.response.omdb;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    @SerializedName("Title")
    public String title;
    @SerializedName("Year")
    public String year;
    @SerializedName("imdbID")
    public String imdbID;
    @SerializedName("Type")
    public String type;
    @SerializedName("Poster")
    public String poster;

}
