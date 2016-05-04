package com.cookingfox.android.app_lifecycle.api;

import android.app.Activity;

/**
 * Created by abeldebeer on 02/05/16.
 */
public interface AppLifecycleManager extends AppLifecycleListenable {
    void onCreate(Activity origin);

    void onStart(Activity origin);

    void onResume(Activity origin);

    void onPause(Activity origin);

    void onStop(Activity origin);

    void onFinish(Activity origin);
}
