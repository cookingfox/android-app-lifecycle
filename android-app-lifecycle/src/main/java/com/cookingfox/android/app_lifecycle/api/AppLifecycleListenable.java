package com.cookingfox.android.app_lifecycle.api;

import com.cookingfox.android.app_lifecycle.api.listener.AppLifecycleEventListener;

/**
 * Provides the ability to listen for app lifecycle events.
 */
public interface AppLifecycleListenable {

    /**
     * Adds a listener for lifecycle events.
     *
     * @param listener The listener to add.
     */
    AppLifecycleListenable addListener(AppLifecycleEventListener listener);

    /**
     * Removes a listener for lifecycle events.
     *
     * @param listener The listener to remove.
     */
    AppLifecycleListenable removeListener(AppLifecycleEventListener listener);

}
