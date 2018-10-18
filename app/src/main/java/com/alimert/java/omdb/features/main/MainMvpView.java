package com.alimert.java.omdb.features.main;

import com.alimert.java.omdb.data.model.response.omdb.Movie;
import com.alimert.java.omdb.features.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {

    void showMovieResult(List<Movie> movies);

}
