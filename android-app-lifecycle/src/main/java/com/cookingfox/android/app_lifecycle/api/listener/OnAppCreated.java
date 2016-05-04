package com.cookingfox.android.app_lifecycle.api.listener;

public interface OnAppCreated extends AppLifecycleEventListener {

    /**
     * When the first activity is created.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppCreated(Class<?> origin);

}
