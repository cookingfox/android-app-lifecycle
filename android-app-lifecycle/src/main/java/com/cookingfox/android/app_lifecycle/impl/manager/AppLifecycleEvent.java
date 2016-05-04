package com.cookingfox.android.app_lifecycle.impl.manager;

/**
 * Internal lifecycle event enum to control and validate the order of events.
 */
enum AppLifecycleEvent {

    CREATE,
    START,
    RESUME,
    PAUSE,
    STOP,
    FINISH

}
