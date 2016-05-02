package com.cookingfox.android.app_lifecycle.api;

import android.app.Activity;

/**
 * Created by abeldebeer on 02/05/16.
 */
public interface AppLifecycleManager extends AppLifecycleListenable {
    void onCreate(Class<? extends Activity> origin);

    void onStart(Class<? extends Activity> origin);

    void onResume(Class<? extends Activity> origin);

    void onPause(Class<? extends Activity> origin);

    void onStop(Class<? extends Activity> origin);

    void onFinish(Class<? extends Activity> origin);
}
