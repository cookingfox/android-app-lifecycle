package com.cookingfox.android.app_lifecycle.impl.manager;

import android.app.Activity;

import com.cookingfox.android.app_lifecycle.api.AppLifecycleListener;
import com.cookingfox.android.app_lifecycle.api.AppLifecycleManager;
import com.cookingfox.android.app_lifecycle.impl.listener.PersistentAppLifecycleListener;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * App lifecycle manager implementation that supports tracking the Android lifecycle across
 * activities.
 */
public class CrossActivityAppLifecycleManager implements AppLifecycleManager {

    /**
     * The class of activity that last triggered a lifecycle event.
     */
    protected Class<? extends Activity> currentOrigin;

    /**
     * The last lifecycle event that was triggered, to control and validate subsequent events.
     */
    protected AppLifecycleEvent lastEvent;

    /**
     * A set of app lifecycle event listeners.
     */
    protected final Set<AppLifecycleListener> listeners = new LinkedHashSet<>();

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Activity origin) {
        // initially the last event is null
        if (!isValid(origin, new AppLifecycleEvent[]{null})) {
            return;
        }

        currentOrigin = origin.getClass();

        notifyListeners(new ListenerNotifier() {
            @Override
            public void call(AppLifecycleListener listener) {
                listener.onAppCreated(currentOrigin);
            }
        });

        lastEvent = AppLifecycleEvent.CREATE;
    }

    @Override
    public void onStart(Activity origin) {
        // START can be called after CREATE, PAUSE, or STOP
        if (!isValid(origin, AppLifecycleEvent.CREATE, AppLifecycleEvent.PAUSE, AppLifecycleEvent.STOP)) {
            return;
        }

        final Class<? extends Activity> originClass = origin.getClass();

        if (originClass.equals(currentOrigin)) {
            // after create or stop: notify listeners
            notifyListeners(new ListenerNotifier() {
                @Override
                public void call(AppLifecycleListener listener) {
                    listener.onAppStarted(currentOrigin);
                }
            });
        } else if (currentOrigin != null) {
            // after pause: don't notify listeners, only change current origin
            currentOrigin = originClass;
        }

        lastEvent = AppLifecycleEvent.START;
    }

    @Override
    public void onResume(Activity origin) {
        // RESUME can be called after START or PAUSE
        if (!isValid(origin, AppLifecycleEvent.START, AppLifecycleEvent.PAUSE)) {
            return;
        }

        if (origin.getClass().equals(currentOrigin)) {
            notifyListeners(new ListenerNotifier() {
                @Override
                public void call(AppLifecycleListener listener) {
                    listener.onAppResumed(currentOrigin);
                }
            });

            lastEvent = AppLifecycleEvent.RESUME;
        }
    }

    @Override
    public void onPause(Activity origin) {
        // PAUSE can be called after RESUME
        if (!isValid(origin, AppLifecycleEvent.RESUME)) {
            return;
        }

        if (origin.getClass().equals(currentOrigin)) {
            notifyListeners(new ListenerNotifier() {
                @Override
                public void call(AppLifecycleListener listener) {
                    listener.onAppPaused(currentOrigin);
                }
            });

            lastEvent = AppLifecycleEvent.PAUSE;
        }
    }

    @Override
    public void onStop(Activity origin) {
        // STOP can be called after PAUSE
        if (!isValid(origin, AppLifecycleEvent.PAUSE)) {
            return;
        }

        if (origin.getClass().equals(currentOrigin)) {
            notifyListeners(new ListenerNotifier() {
                @Override
                public void call(AppLifecycleListener listener) {
                    listener.onAppStopped(currentOrigin);
                }
            });

            lastEvent = AppLifecycleEvent.STOP;
        }
    }

    @Override
    public void onFinish(Activity origin) {
        // FINISH can be called after STOP
        if (!isValid(origin, AppLifecycleEvent.STOP)) {
            return;
        }

        // different origin: ignore
        if (!origin.getClass().equals(currentOrigin)) {
            return;
        }

        notifyListeners(new ListenerNotifier() {
            @Override
            public void call(AppLifecycleListener listener) {
                listener.onAppFinished(currentOrigin);
            }
        });

        // reset state
        currentOrigin = null;
        lastEvent = null;

        for (AppLifecycleListener listener : listeners) {
            removeListener(listener);
        }
    }

    @Override
    public void addListener(AppLifecycleListener listener) {
        listeners.add(Objects.requireNonNull(listener));
    }

    @Override
    public void removeListener(AppLifecycleListener listener) {
        // do not remove persistent listeners
        if (listener instanceof PersistentAppLifecycleListener) {
            return;
        }

        listeners.remove(Objects.requireNonNull(listener));
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Use the listener notifier to call a specific event method on all listeners.
     *
     * @param notifier Utility for calling the correct lifecycle event methods.
     */
    protected void notifyListeners(ListenerNotifier notifier) {
        for (AppLifecycleListener listener : listeners) {
            notifier.call(listener);
        }
    }

    /**
     * Validates the origin activity and allowed last events. For example, PAUSE can only be called
     * after RESUME.
     *
     * @param origin            The activity that triggered the event.
     * @param allowedLastEvents The events after which this event is allowed to be triggered.
     * @return Whether this event is allowed to be triggered.
     */
    private boolean isValid(Activity origin, AppLifecycleEvent... allowedLastEvents) {
        Objects.requireNonNull(origin);

        return Arrays.asList(allowedLastEvents).contains(lastEvent);
    }

    //----------------------------------------------------------------------------------------------
    // INTERFACE: listener notifier
    //----------------------------------------------------------------------------------------------

    /**
     * Utility for calling the correct lifecycle event methods.
     */
    protected interface ListenerNotifier {

        /**
         * Call a certain listener method.
         *
         * @param listener The listener instance.
         */
        void call(AppLifecycleListener listener);

    }

}
