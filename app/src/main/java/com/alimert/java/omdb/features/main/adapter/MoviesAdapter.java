package com.alimert.java.omdb.features.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.alimert.java.omdb.R;
import com.alimert.java.omdb.data.model.response.omdb.Movie;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private Subject<Movie> clickedMovieItem;
    private Context context;

    @Inject
    MoviesAdapter() {
        clickedMovieItem = PublishSubject.create();
        movieList = Collections.emptyList();
    }

    public void setMovies(List<Movie> movies) {
        this.movieList = movies;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = this.movieList.get(position);
        holder.onBind(movie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public Observable<Movie> getMovieClick() {
        return clickedMovieItem;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_year)
        TextView year;
        @BindView(R.id.iv_thumbnail)
        ImageView thumbnail;

        private Movie movie;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> clickedMovieItem.onNext(movieList.get(getLayoutPosition())));
        }

        void onBind(Movie movie) {
            this.movie = movie;
            title.setText(
                    String.format(
                            "%s%s", movie.title.substring(0, 1).toUpperCase(), movie.title.substring(1)));
            year.setText(movie.year);
            Glide.with(context)
                    .load(movie.poster)
                    .apply(RequestOptions
                            .centerCropTransform()
                            .placeholder(R.drawable.ic_launcher_background)
                            .priority(Priority.HIGH)
                    )
                    .into(thumbnail);
        }
    }
}
