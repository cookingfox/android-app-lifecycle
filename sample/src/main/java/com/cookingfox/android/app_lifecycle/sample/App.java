package com.cookingfox.android.app_lifecycle.sample;

import android.app.Application;
import android.util.Log;

import com.cookingfox.android.app_lifecycle.impl.AppLifecycleProvider;
import com.cookingfox.android.app_lifecycle.impl.listener.PersistentAppLifecycleListener;

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
            public void onAppCreated(Class<?> origin) {
                Log.i(TAG, "onAppCreated | origin: " + origin.getSimpleName());
            }

            @Override
            public void onAppStarted(Class<?> origin) {
                Log.i(TAG, "onAppStarted | origin: " + origin.getSimpleName());
            }

            @Override
            public void onAppResumed(Class<?> origin) {
                Log.i(TAG, "onAppResumed | origin: " + origin.getSimpleName());
            }

            @Override
            public void onAppPaused(Class<?> origin) {
                Log.i(TAG, "onAppPaused | origin: " + origin.getSimpleName());
            }

            @Override
            public void onAppStopped(Class<?> origin) {
                Log.i(TAG, "onAppStopped | origin: " + origin.getSimpleName());
            }

            @Override
            public void onAppFinished(Class<?> origin) {
                Log.i(TAG, "onAppFinished | origin: " + origin.getSimpleName());
            }
        });
    }

}
