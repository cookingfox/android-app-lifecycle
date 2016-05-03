package com.cookingfox.android.app_lifecycle.impl;

import android.app.Activity;

import com.cookingfox.android.app_lifecycle.api.AppLifecycleListener;
import com.cookingfox.android.app_lifecycle.api.AppLifecycleManager;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by abeldebeer on 02/05/16.
 */
public class CrossActivityAppLifecycleManager implements AppLifecycleManager {

    protected Class<? extends Activity> currentOrigin;
    protected AppLifecycleEvent lastEvent;
    protected final Set<AppLifecycleListener> listeners = new LinkedHashSet<>();

    @Override
    public void onCreate(Class<? extends Activity> origin) {
        if (!isValid(origin, new AppLifecycleEvent[]{null})) {
            return;
        }

        currentOrigin = origin;

        notifyListeners(new ListenerNotifier() {
            @Override
            public void apply(AppLifecycleListener listener) {
                listener.onAppCreate(currentOrigin);
            }
        });

        lastEvent = AppLifecycleEvent.CREATE;
    }

    @Override
    public void onStart(Class<? extends Activity> origin) {
        if (!isValid(origin, AppLifecycleEvent.CREATE, AppLifecycleEvent.PAUSE)) {
            return;
        }

        if (origin.equals(currentOrigin)) {
            /**
             * From CREATE to START: this only happens in the application's onStart phase, never
             * again after.
             */
            notifyListeners(new ListenerNotifier() {
                @Override
                public void apply(AppLifecycleListener listener) {
                    listener.onAppStart(currentOrigin);
                }
            });
        } else if (currentOrigin != null) {
            /**
             * From PAUSE to START: this happens when transitioning from one activity to the other.
             * In this case we don't want to notify listeners of the START event, but instead only
             * change the current origin.
             */
            currentOrigin = origin;
        }

        lastEvent = AppLifecycleEvent.START;
    }

    @Override
    public void onResume(Class<? extends Activity> origin) {
        if (!isValid(origin, AppLifecycleEvent.START, AppLifecycleEvent.PAUSE)) {
            return;
        }

        if (origin.equals(currentOrigin)) {
            notifyListeners(new ListenerNotifier() {
                @Override
                public void apply(AppLifecycleListener listener) {
                    listener.onAppResume(currentOrigin);
                }
            });

            lastEvent = AppLifecycleEvent.RESUME;
        }
    }

    @Override
    public void onPause(Class<? extends Activity> origin) {
        if (!isValid(origin, AppLifecycleEvent.RESUME)) {
            return;
        }

        if (origin.equals(currentOrigin)) {
            notifyListeners(new ListenerNotifier() {
                @Override
                public void apply(AppLifecycleListener listener) {
                    listener.onAppPause(currentOrigin);
                }
            });

            lastEvent = AppLifecycleEvent.PAUSE;
        }
    }

    @Override
    public void onStop(Class<? extends Activity> origin) {
        if (!isValid(origin, AppLifecycleEvent.PAUSE)) {
            return;
        }

        if (origin.equals(currentOrigin)) {
            notifyListeners(new ListenerNotifier() {
                @Override
                public void apply(AppLifecycleListener listener) {
                    listener.onAppStop(currentOrigin);
                }
            });

            lastEvent = AppLifecycleEvent.STOP;
        }
    }

    @Override
    public void onFinish(Class<? extends Activity> origin) {
        if (!isValid(origin, AppLifecycleEvent.STOP)) {
            return;
        }

        if (origin.equals(currentOrigin)) {
            notifyListeners(new ListenerNotifier() {
                @Override
                public void apply(AppLifecycleListener listener) {
                    listener.onAppFinish(currentOrigin);
                }
            });

            lastEvent = AppLifecycleEvent.FINISH;
        }
    }

    @Override
    public void addListener(AppLifecycleListener listener) {
        listeners.add(Objects.requireNonNull(listener));
    }

    @Override
    public void removeListener(AppLifecycleListener listener) {
        listeners.remove(Objects.requireNonNull(listener));
    }

    protected void notifyListeners(ListenerNotifier notifier) {
        for (AppLifecycleListener listener : listeners) {
            notifier.apply(listener);
        }
    }

    private boolean isValid(Class<? extends Activity> origin, AppLifecycleEvent... allowedLastEvents) {
        if (!Activity.class.isAssignableFrom(origin)) {
            throw new IllegalArgumentException("Lifecycle event methods can only be called from an activity");
        }

        return Arrays.asList(allowedLastEvents).contains(lastEvent);
    }

    protected interface ListenerNotifier {
        void apply(AppLifecycleListener listener);
    }

}
