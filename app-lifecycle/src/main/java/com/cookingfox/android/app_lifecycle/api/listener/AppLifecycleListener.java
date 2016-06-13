package com.cookingfox.android.app_lifecycle.api.listener;

import com.cookingfox.android.app_lifecycle.api.listener.OnAppCreated;
import com.cookingfox.android.app_lifecycle.api.listener.OnAppFinished;
import com.cookingfox.android.app_lifecycle.api.listener.OnAppPaused;
import com.cookingfox.android.app_lifecycle.api.listener.OnAppResumed;
import com.cookingfox.android.app_lifecycle.api.listener.OnAppStarted;
import com.cookingfox.android.app_lifecycle.api.listener.OnAppStopped;

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
