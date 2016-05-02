package com.cookingfox.android.app_lifecycle.api;

/**
 * Created by abeldebeer on 02/05/16.
 */
public interface AppLifecycleListenable {
    void addListener(AppLifecycleListener listener);

    void removeListener(AppLifecycleListener listener);
}
