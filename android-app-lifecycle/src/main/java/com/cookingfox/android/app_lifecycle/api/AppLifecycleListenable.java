package com.cookingfox.android.app_lifecycle.api;

/**
 * Provides the ability to listen for app lifecycle events.
 */
public interface AppLifecycleListenable {

    /**
     * Adds a listener for lifecycle events.
     *
     * @param listener The listener to add.
     */
    void addListener(AppLifecycleListener listener);

    /**
     * Removes a listener for lifecycle events.
     *
     * @param listener The listener to remove.
     */
    void removeListener(AppLifecycleListener listener);

}
