package com.cookingfox.android.app_lifecycle.api.manager;

import android.app.Activity;

import com.cookingfox.android.app_lifecycle.api.listener.AppLifecycleListenable;

/**
 * Allows activities to trigger app lifecycle events and other components to listen to these events.
 */
public interface AppLifecycleManager extends AppLifecycleListenable {

    /**
     * Clear all listeners and app references.
     */
    void dispose();

    /**
     * Trigger an activity create event.
     *
     * @param origin The activity that triggers the event.
     */
    void onCreate(Activity origin);

    /**
     * Trigger an activity start event.
     *
     * @param origin The activity that triggers the event.
     */
    void onStart(Activity origin);

    /**
     * Trigger an activity resume event.
     *
     * @param origin The activity that triggers the event.
     */
    void onResume(Activity origin);

    /**
     * Trigger an activity pause event.
     *
     * @param origin The activity that triggers the event.
     */
    void onPause(Activity origin);

    /**
     * Trigger an activity stop event.
     *
     * @param origin The activity that triggers the event.
     */
    void onStop(Activity origin);

    /**
     * Trigger an activity finish event.
     *
     * @param origin The activity that triggers the event.
     */
    void onFinish(Activity origin);

}
