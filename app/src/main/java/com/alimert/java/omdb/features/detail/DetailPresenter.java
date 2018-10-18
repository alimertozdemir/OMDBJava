package com.alimert.java.omdb.features.detail;

import com.alimert.java.omdb.data.DataManager;
import com.alimert.java.omdb.features.base.BasePresenter;
import com.alimert.java.omdb.injection.ConfigPersistent;
import com.alimert.java.omdb.util.rx.scheduler.SchedulerUtils;

import javax.inject.Inject;

@ConfigPersistent
public class DetailPresenter extends BasePresenter<DetailMvpView> {

    private final DataManager dataManager;

    @Inject
    public DetailPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(DetailMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void getMovieDetail(String imdbId) {
        checkViewAttached();
        getView().showProgress(true);
        dataManager.getMovieDetail(imdbId)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        movieDetail -> {
                            getView().showProgress(false);
                            if(SUCCESS.equals(movieDetail.response)) {
                                getView().showMovieDetails(movieDetail);
                            } else {
                                getView().showError(new Throwable(movieDetail.error));
                            }
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        });
    }
}
