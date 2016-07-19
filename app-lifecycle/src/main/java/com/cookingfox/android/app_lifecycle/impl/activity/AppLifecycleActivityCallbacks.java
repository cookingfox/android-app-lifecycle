package com.cookingfox.android.app_lifecycle.impl.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Build;
import android.os.Bundle;

import com.cookingfox.android.app_lifecycle.api.manager.AppLifecycleManager;

import static com.cookingfox.guava_preconditions.Preconditions.checkNotNull;

/**
 * Implementation of {@link ActivityLifecycleCallbacks} which communicates lifecycle events to the
 * app lifecycle manager.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class AppLifecycleActivityCallbacks implements ActivityLifecycleCallbacks {

    /**
     * Reference to Android application.
     */
    protected final Application app;

    /**
     * Lifecycle manager.
     */
    protected final AppLifecycleManager manager;

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

    public AppLifecycleActivityCallbacks(Application app, AppLifecycleManager manager) {
        this.app = checkNotNull(app, "Application can not be null");
        this.manager = checkNotNull(manager, "App lifecycle manager can not be null");
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    //----------------------------------------------------------------------------------------------

    public void dispose() {
        app.unregisterActivityLifecycleCallbacks(this);
    }

    public void initialize() {
        app.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        manager.onCreate(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        manager.onStart(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        manager.onResume(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        manager.onPause(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        manager.onStop(activity);

        if (activity.isFinishing()) {
            manager.onFinish(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        // ignore activity lifecycle event
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        // ignore activity lifecycle event: see `onActivityStopped`
    }

}
