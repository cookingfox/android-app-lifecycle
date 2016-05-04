package com.cookingfox.android.app_lifecycle.api.listener;

public interface OnAppPaused extends AppLifecycleEventListener {

    /**
     * When the current activity is paused by bringing it to background, or by starting a new
     * activity.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppPaused(Class<?> origin);

}
