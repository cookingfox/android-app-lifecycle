package com.cookingfox.android.app_lifecycle.impl;

import android.app.Application;

import com.cookingfox.android.app_lifecycle.api.manager.AppLifecycleManager;
import com.cookingfox.android.app_lifecycle.impl.manager.CrossActivityAppLifecycleManager;

import static com.cookingfox.guava_preconditions.Preconditions.checkNotNull;

/**
 * Provides initialization of app lifecycle manager and static access to manager.
 */
public final class AppLifecycleProvider {

    /**
     * Static instance of lifecycle manager.
     */
    protected static AppLifecycleManager manager;

    /**
     * Disposes the app lifecycle manager.
     *
     * @throws NullPointerException when not initialized.
     * @see AppLifecycleManager#dispose()
     */
    public static void dispose() {
        getManager().dispose();

        manager = null;
    }

    /**
     * Returns the app lifecycle manager.
     *
     * @return App lifecycle manager instance.
     * @throws NullPointerException when not initialized.
     */
    public static AppLifecycleManager getManager() {
        return checkNotNull(manager, "Can not get app lifecycle manager: not yet initialized - " +
                "call `AppLifecycleProvider.initialize()` first");
    }

    /**
     * Initializes the app lifecycle manager.
     *
     * @param app An instance of the Android application, to ensure the correct starting point.
     * @return App lifecycle manager instance.
     * @throws NullPointerException  when the Application instance is null.
     * @throws IllegalStateException when the manager is already initialized.
     */
    public static AppLifecycleManager initialize(Application app) {
        checkNotNull(app, "Can not initialize app lifecycle manager: provided `Application` " +
                "instance is null");

        if (manager != null) {
            throw new IllegalStateException("App lifecycle manager is already initialized");
        }

        return manager = new CrossActivityAppLifecycleManager();
    }

}
