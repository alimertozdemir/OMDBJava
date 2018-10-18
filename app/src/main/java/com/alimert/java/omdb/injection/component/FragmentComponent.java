package com.alimert.java.omdb.injection.component;

import dagger.Subcomponent;
import com.alimert.java.omdb.injection.PerFragment;
import com.alimert.java.omdb.injection.module.FragmentModule;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
}
