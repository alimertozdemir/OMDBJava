package com.alimert.java.omdb.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import com.alimert.java.omdb.data.DataManager;
import com.alimert.java.omdb.injection.ApplicationContext;
import com.alimert.java.omdb.injection.module.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ApplicationContext
    Context context();

    Application application();

    DataManager apiManager();
}
