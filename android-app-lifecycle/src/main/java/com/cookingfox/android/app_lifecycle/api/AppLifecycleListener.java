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
    void onAppCreated(Class<?> origin);

    /**
     * When the first activity is started, or when the current activity is brought back from
     * background to foreground.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppStarted(Class<?> origin);

    /**
     * When the current activity is resumed by bringing it back to foreground, or after starting a
     * new activity.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppResumed(Class<?> origin);

    /**
     * When the current activity is paused by bringing it to background, or by starting a new
     * activity.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppPaused(Class<?> origin);

    /**
     * When the current activity is brought to background.
     *
     * @param origin The activity that triggered this event.
     */
    void onAppStopped(Class<?> origin);

    /**
     * When the last activity finished (exit).
     *
     * @param origin The activity that triggered this event.
     */
    void onAppFinished(Class<?> origin);

}
