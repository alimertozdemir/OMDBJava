package com.alimert.java.omdb.features.main;

import com.alimert.java.omdb.data.DataManager;
import com.alimert.java.omdb.features.base.BasePresenter;
import com.alimert.java.omdb.injection.ConfigPersistent;
import com.alimert.java.omdb.util.rx.scheduler.SchedulerUtils;

import javax.inject.Inject;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager dataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void getSearchResult(String searchKey) {
        checkViewAttached();
        getView().showProgress(true);
        dataManager
                .getSearchResult(searchKey)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        result -> {
                            getView().showProgress(false);
                            if(SUCCESS.equals(result.response)) {
                                getView().showMovieResult(result.search);
                            } else {
                                getView().showError(new Throwable(result.error));
                            }
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        });
    }
}
