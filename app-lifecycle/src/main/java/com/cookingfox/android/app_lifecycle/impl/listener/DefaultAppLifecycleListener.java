package com.cookingfox.android.app_lifecycle.impl.listener;

import com.cookingfox.android.app_lifecycle.api.listener.AppLifecycleListener;

/**
 * No-operation implementation of app lifecycle listener.
 */
public class DefaultAppLifecycleListener implements AppLifecycleListener {

    @Override
    public void onAppCreated(Class<?> origin) {
        // override in subclass
    }

    @Override
    public void onAppStarted(Class<?> origin) {
        // override in subclass
    }

    @Override
    public void onAppResumed(Class<?> origin) {
        // override in subclass
    }

    @Override
    public void onAppPaused(Class<?> origin) {
        // override in subclass
    }

    @Override
    public void onAppStopped(Class<?> origin) {
        // override in subclass
    }

    @Override
    public void onAppFinished(Class<?> origin) {
        // override in subclass
    }

}
