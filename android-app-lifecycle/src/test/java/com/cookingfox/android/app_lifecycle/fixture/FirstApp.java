package com.cookingfox.android.app_lifecycle.fixture;

import android.app.Application;

import com.cookingfox.android.app_lifecycle.impl.AppLifecycleProvider;

public class FirstApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppLifecycleProvider.initialize(this);
    }
}
