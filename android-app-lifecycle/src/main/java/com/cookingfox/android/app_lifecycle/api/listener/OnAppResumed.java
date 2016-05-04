package com.cookingfox.android.app_lifecycle.api.listener;

public interface OnAppResumed extends AppLifecycleEventListener {

    /**
     * When the current activity is resumed by bringing it back to foreground, or after starting a
     * new activity.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppResumed(Class<?> origin);

}
