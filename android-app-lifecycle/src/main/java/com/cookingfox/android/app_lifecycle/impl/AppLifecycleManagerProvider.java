package com.cookingfox.android.app_lifecycle.impl;

import com.cookingfox.android.app_lifecycle.api.AppLifecycleManager;

/**
 * Created by abeldebeer on 02/05/16.
 */
public final class AppLifecycleManagerProvider {

    private static AppLifecycleManager instance;

    public static AppLifecycleManager get() {
        return instance;
    }

    public static void set(AppLifecycleManager instance) {
        AppLifecycleManagerProvider.instance = instance;
    }
}
