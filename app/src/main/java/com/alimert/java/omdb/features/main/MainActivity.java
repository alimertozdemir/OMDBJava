package com.alimert.java.omdb.features.main;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alimert.java.omdb.R;
import com.alimert.java.omdb.data.model.response.omdb.Movie;
import com.alimert.java.omdb.features.base.BaseActivity;
import com.alimert.java.omdb.features.common.ErrorView;
import com.alimert.java.omdb.features.detail.DetailActivity;
import com.alimert.java.omdb.features.main.adapter.MoviesAdapter;
import com.alimert.java.omdb.injection.component.ActivityComponent;
import com.alimert.java.omdb.util.view.RecyclerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView, ErrorView.ErrorListener, SearchView.OnQueryTextListener {

    @Inject
    MoviesAdapter moviesAdapter;
    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.view_error)
    ErrorView errorView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.rvMovies)
    RecyclerView recyclerViewMovies;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private final int MIN_SEARCH_KEY_LENGTH = 2;
    private String searchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);

        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
        swipeRefreshLayout.setColorSchemeResources(R.color.white);
        swipeRefreshLayout.setOnRefreshListener(() -> mainPresenter.getSearchResult(searchKey));

        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMovies.addItemDecoration(new RecyclerItemDecoration(this));
        recyclerViewMovies.setAdapter(moviesAdapter);
        this.movieClicked();
        errorView.setErrorListener(this);
    }

    private void movieClicked() {
        Disposable disposable =
                moviesAdapter
                        .getMovieClick()
                        .subscribe(
                                movie ->
                                        startActivity(DetailActivity.getStartIntent(this, movie)),
                                throwable -> {
                                    Timber.e(throwable, "Movie click failed");
                                    Toast.makeText(
                                            this,
                                            R.string.error_something_bad_happened,
                                            Toast.LENGTH_LONG)
                                            .show();
                                });
        mainPresenter.addDisposable(disposable);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        mainPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        mainPresenter.detachView();
    }

    @Override
    public void showMovieResult(List<Movie> movies) {
        moviesAdapter.setMovies(movies);
        recyclerViewMovies.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            if (recyclerViewMovies.getVisibility() == View.VISIBLE
                    && moviesAdapter.getItemCount() > 0) {
                swipeRefreshLayout.setRefreshing(true);
            } else {
                progressBar.setVisibility(View.VISIBLE);

                recyclerViewMovies.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.GONE);
            }

            errorView.setVisibility(View.GONE);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(Throwable error) {
        recyclerViewMovies.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        Timber.e(error, "There was an error retrieving movies");
    }

    @Override
    public void onReloadData() {
        mainPresenter.getSearchResult(searchKey);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(this);
        }

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextChange(String text) {
        if ("".equals(text) || text.length() < MIN_SEARCH_KEY_LENGTH) {
            return false;
        }
        this.searchKey = text.trim();
        mainPresenter.getSearchResult(searchKey);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
