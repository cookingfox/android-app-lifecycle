package com.cookingfox.android.app_lifecycle.api;

/**
 * Listener for app lifecycle events.
 */
public interface AppLifecycleListener {

    /**
     * When the first activity is created.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppCreate(Class<?> origin);

    /**
     * When the first activity is started, or when the current activity is brought back from
     * background to foreground.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppStart(Class<?> origin);

    /**
     * When the current activity is resumed by bringing it back to foreground, or after starting a
     * new activity.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppResume(Class<?> origin);

    /**
     * When the current activity is paused by bringing it to background, or by starting a new
     * activity.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppPause(Class<?> origin);

    /**
     * When the current activity is brought to background.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppStop(Class<?> origin);

    /**
     * When the last activity finished (exit).
     *
     * @param origin The activity that triggered this event.
     */
    void onAppFinish(Class<?> origin);

}
