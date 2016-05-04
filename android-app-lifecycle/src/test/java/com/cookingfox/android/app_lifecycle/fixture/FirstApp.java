package com.cookingfox.android.app_lifecycle.fixture;

import android.app.Application;

import com.cookingfox.android.app_lifecycle.impl.AppLifecycleProvider;

/**
 * Created by abeldebeer on 04/05/16.
 */
public class FirstApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppLifecycleProvider.initialize(this);
    }
}
