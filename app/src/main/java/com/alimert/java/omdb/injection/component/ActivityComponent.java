package com.alimert.java.omdb.injection.component;

import dagger.Subcomponent;
import com.alimert.java.omdb.features.detail.DetailActivity;
import com.alimert.java.omdb.features.main.MainActivity;
import com.alimert.java.omdb.injection.PerActivity;
import com.alimert.java.omdb.injection.module.ActivityModule;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(DetailActivity detailActivity);
}
