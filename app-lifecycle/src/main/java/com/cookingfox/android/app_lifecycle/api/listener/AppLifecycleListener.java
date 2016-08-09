package com.cookingfox.android.app_lifecycle.api.listener;

/**
 * Listener for all app lifecycle events.
 */
public interface AppLifecycleListener extends
        OnAppCreated,
        OnAppStarted,
        OnAppResumed,
        OnAppPaused,
        OnAppStopped,
        OnAppFinished {
}
