package com.cookingfox.android.app_lifecycle.impl;

import android.app.Application;

import com.cookingfox.android.app_lifecycle.api.AppLifecycleManager;

import java.util.Objects;

/**
 * Created by abeldebeer on 02/05/16.
 */
public final class AppLifecycleProvider {

    protected static AppLifecycleManager manager;

    public static AppLifecycleManager getManager() {
        return Objects.requireNonNull(manager, "Not yet initialized");
    }

    public static AppLifecycleManager initialize(Application app) {
        if (manager != null) {
            throw new IllegalStateException("Already initialized");
        }

        Objects.requireNonNull(app, "App should not be null");

        return manager = new CrossActivityAppLifecycleManager();
    }

}
