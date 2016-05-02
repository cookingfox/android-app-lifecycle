package com.cookingfox.android.app_lifecycle.api;

/**
 * Created by abeldebeer on 02/05/16.
 */
public interface AppLifecycleListener {
    void onAppCreate(Class<?> origin);

    void onAppStart(Class<?> origin);

    void onAppResume(Class<?> origin);

    void onAppPause(Class<?> origin);

    void onAppStop(Class<?> origin);

    void onAppFinish(Class<?> origin);
}
