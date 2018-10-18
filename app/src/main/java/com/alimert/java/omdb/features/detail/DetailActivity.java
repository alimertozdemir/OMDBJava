package com.alimert.java.omdb.features.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.alimert.java.omdb.R;
import com.alimert.java.omdb.data.model.response.omdb.Movie;
import com.alimert.java.omdb.data.model.response.omdb.MovieDetail;
import com.alimert.java.omdb.features.base.BaseActivity;
import com.alimert.java.omdb.features.common.ErrorView;
import com.alimert.java.omdb.injection.component.ActivityComponent;
import com.alimert.java.omdb.util.StringUtils;
import com.alimert.java.omdb.util.view.SquareImageView;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

public class DetailActivity extends BaseActivity implements DetailMvpView, ErrorView.ErrorListener {

    public static final String MOVIE = "MOVIE";

    @Inject
    DetailPresenter detailPresenter;

    @BindView(R.id.view_error)
    ErrorView errorView;

    @BindView(R.id.iv_poster)
    SquareImageView ivPoster;

    @BindView(R.id.progress)
    ProgressBar progress;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.nested_scroll)
    NestedScrollView nestedScrollView;

    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;

    @BindView(R.id.tv_rated)
    TextView tvRated;

    @BindView(R.id.tv_runtime)
    TextView tvRuntime;

    @BindView(R.id.tv_genre)
    TextView tvGenre;

    @BindView(R.id.tv_director)
    TextView tvDirector;

    @BindView(R.id.tv_writer)
    TextView tvWriter;

    @BindView(R.id.tv_actors)
    TextView tvActors;

    @BindView(R.id.tv_plot)
    TextView tvPlot;

    @BindView(R.id.tv_language)
    TextView tvLanguage;

    @BindView(R.id.tv_country)
    TextView tvCountry;

    @BindView(R.id.tv_awards)
    TextView tvAwards;

    private String movieId;

    public static Intent getStartIntent(Context context, Movie movie) {
        Timber.d("Movie imdbID : " + movie.imdbID);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("MOVIE", movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayShowTitleEnabled(true);
        }

        this.getMovieDetail();

        errorView.setErrorListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        detailPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        detailPresenter.detachView();
    }

    @Override
    public void showMovieDetails(MovieDetail movieDetail) {

        Timber.d("MOVIE DETAIL: " + movieDetail.imdbID);

        tvReleaseDate.setText(StringUtils.getFormattedString(this, R.string.release_date, movieDetail.released));
        tvRated.setText(StringUtils.getFormattedString(this, R.string.rated, movieDetail.rated));
        tvRuntime.setText(StringUtils.getFormattedString(this, R.string.runtime, movieDetail.runtime));
        tvGenre.setText(StringUtils.getFormattedString(this, R.string.genre, movieDetail.genre));
        tvDirector.setText(StringUtils.getFormattedString(this, R.string.director, movieDetail.director));
        tvWriter.setText(StringUtils.getFormattedString(this, R.string.writer, movieDetail.writer));
        tvActors.setText(StringUtils.getFormattedString(this, R.string.actors, movieDetail.actors));
        tvPlot.setText(StringUtils.getFormattedString(this, R.string.plot, movieDetail.plot));
        tvLanguage.setText(StringUtils.getFormattedString(this, R.string.language, movieDetail.language));
        tvCountry.setText(StringUtils.getFormattedString(this, R.string.country, movieDetail.country));
        tvAwards.setText(StringUtils.getFormattedString(this, R.string.awards, movieDetail.awards));

    }

    @Override
    public void showProgress(boolean show) {
        errorView.setVisibility(View.GONE);
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(Throwable error) {
        nestedScrollView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        Timber.e(error, "There was a problem retrieving the pokemon...");
    }

    @Override
    public void onReloadData() {
        detailPresenter.getMovieDetail(movieId);
    }

    private void getMovieDetail() {

        Movie movie = (Movie) getIntent().getSerializableExtra(MOVIE);

        if (movie == null) {
            throw new IllegalArgumentException("Detail Activity requires a movieId@");
        }

        String title = movie.title;
        String movieId = movie.imdbID;
        String poster = movie.poster;

        if (title != null) {
            collapsingToolbarLayout.setTitle(title);
        }

        if (movieId != null) {
            detailPresenter.getMovieDetail(movieId);
        }

        if (poster != null) {
            Glide.with(this)
                    .load(poster)
                    .apply(RequestOptions
                            .centerCropTransform()
                            .placeholder(R.drawable.ic_launcher_background)
                            .priority(Priority.HIGH)
                    )
                    .into(ivPoster);
        }
    }
}
