package com.cookingfox.android.app_lifecycle.impl.manager;

import android.app.Activity;

import com.cookingfox.android.app_lifecycle.api.listener.AppLifecycleEventListener;
import com.cookingfox.android.app_lifecycle.api.listener.AppLifecycleListenable;
import com.cookingfox.android.app_lifecycle.api.listener.OnAppCreated;
import com.cookingfox.android.app_lifecycle.api.listener.OnAppFinished;
import com.cookingfox.android.app_lifecycle.api.listener.OnAppPaused;
import com.cookingfox.android.app_lifecycle.api.listener.OnAppResumed;
import com.cookingfox.android.app_lifecycle.api.listener.OnAppStarted;
import com.cookingfox.android.app_lifecycle.api.listener.OnAppStopped;
import com.cookingfox.android.app_lifecycle.api.listener.PersistentAppLifecycleEventListener;
import com.cookingfox.android.app_lifecycle.api.manager.AppLifecycleManager;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

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
    protected final List<AppLifecycleEventListener> listeners = new LinkedList<AppLifecycleEventListener>();

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    //----------------------------------------------------------------------------------------------

    @Override
    public AppLifecycleListenable addListener(AppLifecycleEventListener listener) {
        if (listeners.contains(checkNotNull(listener, "Listener can not be null"))) {
            throw new IllegalStateException("Listener was already added: " + listener);
        }

        listeners.add(listener);

        return this;
    }

    @Override
    public AppLifecycleListenable removeListener(AppLifecycleEventListener listener) {
        if (!listeners.contains(checkNotNull(listener, "Listener can not be null"))) {
            throw new IllegalStateException("Listener not found: " + listener);
        }

        // do not remove persistent listeners
        if (!(listener instanceof PersistentAppLifecycleEventListener)) {
            listeners.remove(listener);
        }

        return this;
    }

    @Override
    public void onCreate(Activity origin) {
        // initially the last event is null
        if (!isValid(origin, new AppLifecycleEvent[]{null})) {
            return;
        }

        currentOrigin = origin.getClass();

        notifyListeners(new ListenerNotifier() {
            @Override
            public void call(AppLifecycleEventListener listener) {
                if (listener instanceof OnAppCreated) {
                    ((OnAppCreated) listener).onAppCreated(currentOrigin);
                }
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
                public void call(AppLifecycleEventListener listener) {
                    if (listener instanceof OnAppStarted) {
                        ((OnAppStarted) listener).onAppStarted(currentOrigin);
                    }
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
                public void call(AppLifecycleEventListener listener) {
                    if (listener instanceof OnAppResumed) {
                        ((OnAppResumed) listener).onAppResumed(currentOrigin);
                    }
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
                public void call(AppLifecycleEventListener listener) {
                    if (listener instanceof OnAppPaused) {
                        ((OnAppPaused) listener).onAppPaused(currentOrigin);
                    }
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
                public void call(AppLifecycleEventListener listener) {
                    if (listener instanceof OnAppStopped) {
                        ((OnAppStopped) listener).onAppStopped(currentOrigin);
                    }
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
            public void call(AppLifecycleEventListener listener) {
                if (listener instanceof OnAppFinished) {
                    ((OnAppFinished) listener).onAppFinished(currentOrigin);
                }
            }
        });

        // reset state
        currentOrigin = null;
        lastEvent = null;

        for (AppLifecycleEventListener listener : listeners) {
            removeListener(listener);
        }
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Validates the origin activity and allowed last events. For example, PAUSE can only be called
     * after RESUME.
     *
     * @param origin            The activity that triggered the event.
     * @param allowedLastEvents The events after which this event is allowed to be triggered.
     * @return Whether this event is allowed to be triggered.
     */
    protected boolean isValid(Activity origin, AppLifecycleEvent... allowedLastEvents) {
        checkNotNull(origin, "Origin activity can not be null");

        return Arrays.asList(allowedLastEvents).contains(lastEvent);
    }

    /**
     * Use the listener notifier to call a specific event method on all listeners.
     *
     * @param notifier Utility for calling the correct lifecycle event methods.
     */
    protected void notifyListeners(ListenerNotifier notifier) {
        final List<AppLifecycleEventListener> calledListeners = new LinkedList<AppLifecycleEventListener>();
        final int numListeners = listeners.size();

        /**
         * Call listeners in reverse order (added first, called last). Calling listeners this way
         * will prevent a concurrent modification exception, but requires keeping track of called
         * listeners.
         */
        for (int i = numListeners - 1; i >= 0; i--) {
            final AppLifecycleEventListener listener = listeners.get(i);

            if (!calledListeners.contains(listener)) {
                notifier.call(listener);
                calledListeners.add(listener);
            }
        }
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
        void call(AppLifecycleEventListener listener);

    }

}
