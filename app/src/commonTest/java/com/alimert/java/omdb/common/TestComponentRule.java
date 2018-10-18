package com.alimert.java.omdb.common;

import android.content.Context;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.alimert.java.omdb.MvpApplication;
import com.alimert.java.omdb.common.injection.component.DaggerTestComponent;
import com.alimert.java.omdb.common.injection.component.TestComponent;
import com.alimert.java.omdb.common.injection.module.ApplicationTestModule;
import com.alimert.java.omdb.data.DataManager;

/**
 * Test rule that creates and sets a Dagger TestComponent into the application overriding the
 * existing application component. Use this rule in your test case in order for the app to use mock
 * dependencies. It also exposes some of the dependencies so they can be easily accessed from the
 * tests, e.g. to stub mocks etc.
 */
public class TestComponentRule implements TestRule {

    private final TestComponent testComponent;
    private final Context context;

    public TestComponentRule(Context context) {
        this.context = context;
        MvpApplication application = MvpApplication.get(context);
        testComponent =
                DaggerTestComponent.builder()
                        .applicationTestModule(new ApplicationTestModule(application))
                        .build();
    }

    public TestComponent getTestComponent() {
        return testComponent;
    }

    public Context getContext() {
        return context;
    }

    public DataManager getMockApiManager() {
        return testComponent.apiManager();
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                MvpApplication application = MvpApplication.get(context);
                application.setComponent(testComponent);
                base.evaluate();
                application.setComponent(null);
            }
        };
    }
}
