package com.cookingfox.android.app_lifecycle.impl;

import android.app.Activity;

import com.cookingfox.android.app_lifecycle.api.AppLifecycleListener;
import com.cookingfox.android.app_lifecycle.api.AppLifecycleManager;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by abeldebeer on 02/05/16.
 */
public class CrossActivityAppLifecycleManager implements AppLifecycleManager {

    protected Class<? extends Activity> currentOrigin;
    protected final Set<AppLifecycleListener> listeners = new LinkedHashSet<>();

    @Override
    public void onCreate(Class<? extends Activity> origin) {
        if (currentOrigin == null) {
            currentOrigin = Objects.requireNonNull(origin);

            notifyListeners(new ListenerNotifier() {
                @Override
                public void apply(AppLifecycleListener listener) {
                    listener.onAppCreate(currentOrigin);
                }
            });
        }
    }

    @Override
    public void onStart(Class<? extends Activity> origin) {
        if (origin.equals(currentOrigin)) {
            notifyListeners(new ListenerNotifier() {
                @Override
                public void apply(AppLifecycleListener listener) {
                    listener.onAppStart(currentOrigin);
                }
            });
        } else if (currentOrigin != null) {
            currentOrigin = origin;
        } else {
            // TODO: 03/05/16 Warning? Current origin should never be null at this stage
        }
    }

    @Override
    public void onResume(Class<? extends Activity> origin) {
        if (origin.equals(currentOrigin)) {
            notifyListeners(new ListenerNotifier() {
                @Override
                public void apply(AppLifecycleListener listener) {
                    listener.onAppResume(currentOrigin);
                }
            });
        }
    }

    @Override
    public void onPause(Class<? extends Activity> origin) {
        if (origin.equals(currentOrigin)) {
            notifyListeners(new ListenerNotifier() {
                @Override
                public void apply(AppLifecycleListener listener) {
                    listener.onAppPause(currentOrigin);
                }
            });
        }
    }

    @Override
    public void onStop(Class<? extends Activity> origin) {
        if (origin.equals(currentOrigin)) {
            notifyListeners(new ListenerNotifier() {
                @Override
                public void apply(AppLifecycleListener listener) {
                    listener.onAppStop(currentOrigin);
                }
            });
        }
    }

    @Override
    public void onFinish(Class<? extends Activity> origin) {
        if (origin.equals(currentOrigin)) {
            notifyListeners(new ListenerNotifier() {
                @Override
                public void apply(AppLifecycleListener listener) {
                    listener.onAppFinish(currentOrigin);
                }
            });
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

    private void notifyListeners(ListenerNotifier notifier) {
        for (AppLifecycleListener listener : listeners) {
            notifier.apply(listener);
        }
    }

    private interface ListenerNotifier {
        void apply(AppLifecycleListener listener);
    }

}
