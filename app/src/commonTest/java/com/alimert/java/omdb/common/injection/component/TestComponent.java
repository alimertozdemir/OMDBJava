package com.alimert.java.omdb.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.alimert.java.omdb.common.injection.module.ApplicationTestModule;
import com.alimert.java.omdb.injection.component.AppComponent;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends AppComponent {
}
