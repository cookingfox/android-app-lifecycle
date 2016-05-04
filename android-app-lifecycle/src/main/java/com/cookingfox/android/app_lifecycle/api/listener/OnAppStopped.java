package com.cookingfox.android.app_lifecycle.api.listener;

public interface OnAppStopped extends AppLifecycleEventListener {

    /**
     * When the current activity is brought to background.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppStopped(Class<?> origin);

}
