package com.cookingfox.android.app_lifecycle.api.listener;

public interface OnAppFinished extends AppLifecycleEventListener {

    /**
     * When the last activity finished (exit).
     *
     * @param origin The activity that triggered this event.
     */
    void onAppFinished(Class<?> origin);

}
