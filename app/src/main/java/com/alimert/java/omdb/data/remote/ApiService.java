package com.alimert.java.omdb.data.remote;

import com.alimert.java.omdb.data.model.response.omdb.MovieDetail;
import com.alimert.java.omdb.data.model.response.omdb.SearchMovieResult;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET(".")
    Single<SearchMovieResult> getSearchResult(@QueryMap Map<String, String> options);

    @GET(".")
    Single<MovieDetail> getMovieDetail(@QueryMap Map<String, String> options);

}
