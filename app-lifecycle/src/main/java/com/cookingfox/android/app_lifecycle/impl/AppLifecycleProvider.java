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
     * @return App lifecycle manager instance.
     * @throws NullPointerException when not initialized.
     */
    public static AppLifecycleManager getManager() {
        return checkNotNull(manager, "Not yet initialized");
    }

    /**
     * Initializes the app lifecycle manager.
     *
     * @param app An instance of the Android application, to ensure the correct starting point.
     * @return App lifecycle manager instance.
     */
    public static AppLifecycleManager initialize(Application app) {
        checkNotNull(app, "App should not be null");

        if (manager != null) {
            throw new IllegalStateException("Already initialized");
        }

        return manager = new CrossActivityAppLifecycleManager();
    }

}
