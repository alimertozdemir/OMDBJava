package com.alimert.java.omdb.features.detail;

import com.alimert.java.omdb.data.model.response.omdb.MovieDetail;
import com.alimert.java.omdb.features.base.MvpView;

public interface DetailMvpView extends MvpView {

    void showMovieDetails(MovieDetail movieDetail);

}
