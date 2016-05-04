package com.cookingfox.android.app_lifecycle.api.listener;

public interface OnAppStarted extends AppLifecycleEventListener {

    /**
     * When the first activity is started, or when the current activity is brought back from
     * background to foreground.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppStarted(Class<?> origin);

}
