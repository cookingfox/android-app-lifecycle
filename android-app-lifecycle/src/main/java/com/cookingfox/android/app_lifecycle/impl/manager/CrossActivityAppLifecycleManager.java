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
 * Created by abeldebeer on 02/05/16.
 */
public class CrossActivityAppLifecycleManager implements AppLifecycleManager {

    protected Class<? extends Activity> currentOrigin;
    protected AppLifecycleEvent lastEvent;
    protected final Set<AppLifecycleListener> listeners = new LinkedHashSet<>();

    @Override
    public void onCreate(Activity origin) {
        if (!isValid(origin, new AppLifecycleEvent[]{null})) {
            return;
        }

        currentOrigin = origin.getClass();

        notifyListeners(new ListenerNotifier() {
            @Override
            public void apply(AppLifecycleListener listener) {
                listener.onAppCreate(currentOrigin);
            }
        });

        lastEvent = AppLifecycleEvent.CREATE;
    }

    @Override
    public void onStart(Activity origin) {
        if (!isValid(origin, AppLifecycleEvent.CREATE, AppLifecycleEvent.PAUSE, AppLifecycleEvent.STOP)) {
            return;
        }

        final Class<? extends Activity> originClass = origin.getClass();

        if (originClass.equals(currentOrigin)) {
            notifyListeners(new ListenerNotifier() {
                @Override
                public void apply(AppLifecycleListener listener) {
                    listener.onAppStart(currentOrigin);
                }
            });
        } else if (currentOrigin != null) {
            currentOrigin = originClass;
        }

        lastEvent = AppLifecycleEvent.START;
    }

    @Override
    public void onResume(Activity origin) {
        if (!isValid(origin, AppLifecycleEvent.START, AppLifecycleEvent.PAUSE)) {
            return;
        }

        if (origin.getClass().equals(currentOrigin)) {
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
    public void onPause(Activity origin) {
        if (!isValid(origin, AppLifecycleEvent.RESUME)) {
            return;
        }

        if (origin.getClass().equals(currentOrigin)) {
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
    public void onStop(Activity origin) {
        if (!isValid(origin, AppLifecycleEvent.PAUSE)) {
            return;
        }

        if (origin.getClass().equals(currentOrigin)) {
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
    public void onFinish(Activity origin) {
        if (!isValid(origin, AppLifecycleEvent.STOP)) {
            return;
        }

        if (!origin.getClass().equals(currentOrigin)) {
            return;
        }

        notifyListeners(new ListenerNotifier() {
            @Override
            public void apply(AppLifecycleListener listener) {
                listener.onAppFinish(currentOrigin);
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

    protected void notifyListeners(ListenerNotifier notifier) {
        for (AppLifecycleListener listener : listeners) {
            notifier.apply(listener);
        }
    }

    private boolean isValid(Activity origin, AppLifecycleEvent... allowedLastEvents) {
        Objects.requireNonNull(origin);

        return Arrays.asList(allowedLastEvents).contains(lastEvent);
    }

    protected interface ListenerNotifier {
        void apply(AppLifecycleListener listener);
    }

}
