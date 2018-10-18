package com.alimert.java.omdb.data;

import com.alimert.java.omdb.data.model.response.omdb.MovieDetail;
import com.alimert.java.omdb.data.model.response.omdb.SearchMovieResult;
import com.alimert.java.omdb.data.remote.ApiService;
import com.alimert.java.omdb.util.ApiQueryManager;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;


@Singleton
public class DataManager {

    private ApiService apiService;
    private Map<String, String> apiQuery;

    @Inject
    public DataManager(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<SearchMovieResult> getSearchResult(String searchKey) {
        apiQuery = ApiQueryManager.getInstance().addQueryParams("s", searchKey);
        return apiService.getSearchResult(apiQuery);
    }

    public Single<MovieDetail> getMovieDetail(String imdbId) {
        apiQuery = ApiQueryManager.getInstance().addQueryParams("i", imdbId);
        return apiService.getMovieDetail(apiQuery);
    }
}
