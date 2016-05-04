package com.cookingfox.android.app_lifecycle.sample;

import android.app.Application;
import android.util.Log;

import com.cookingfox.android.app_lifecycle.impl.AppLifecycleProvider;
import com.cookingfox.android.app_lifecycle.impl.PersistentAppLifecycleListener;

/**
 * Created by abeldebeer on 04/05/16.
 */
public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "App.onCreate()");

        AppLifecycleProvider.initialize(this).addListener(new PersistentAppLifecycleListener() {
            @Override
            public void onAppCreate(Class<?> origin) {
                Log.i(TAG, "onAppCreate | origin: " + origin.getSimpleName());
            }

            @Override
            public void onAppFinish(Class<?> origin) {
                Log.i(TAG, "onAppFinish | origin: " + origin.getSimpleName());
            }
        });
    }

}
