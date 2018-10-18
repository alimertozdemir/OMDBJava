package com.alimert.java.omdb.runner;

import android.app.Application;
import android.content.Context;

import io.appflate.restmock.android.RESTMockTestRunner;
import com.alimert.java.omdb.MvpApplication;

/**
 * Created by ravindra on 4/2/17.
 */
public class TestRunner extends RESTMockTestRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, MvpApplication.class.getName(), context);
    }
}
