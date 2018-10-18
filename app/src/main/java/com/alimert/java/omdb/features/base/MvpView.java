package com.alimert.java.omdb.features.base;

/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
public interface MvpView {

    void showProgress(boolean show);

    void showError(Throwable error);
}
