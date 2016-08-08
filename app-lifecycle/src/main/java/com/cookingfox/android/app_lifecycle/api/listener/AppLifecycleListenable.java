package com.cookingfox.android.app_lifecycle.api.listener;

/**
 * Provides the ability to listen for app lifecycle events.
 */
public interface AppLifecycleListenable {

    /**
     * Adds a listener for lifecycle events.
     *
     * @param listener The listener to add.
     * @return The current instance, so method calls can be chained.
     */
    AppLifecycleListenable addListener(AppLifecycleEventListener listener);

    /**
     * Removes a listener for lifecycle events.
     *
     * @param listener The listener to remove.
     * @return The current instance, so method calls can be chained.
     */
    AppLifecycleListenable removeListener(AppLifecycleEventListener listener);

}
