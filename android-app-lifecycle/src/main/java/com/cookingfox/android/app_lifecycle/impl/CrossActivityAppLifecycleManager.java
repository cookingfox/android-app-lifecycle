package com.cookingfox.android.app_lifecycle.impl;

import android.app.Activity;

import com.cookingfox.android.app_lifecycle.api.AppLifecycleListener;
import com.cookingfox.android.app_lifecycle.api.AppLifecycleManager;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Created by abeldebeer on 02/05/16.
 */
public class CrossActivityAppLifecycleManager implements AppLifecycleManager {

    protected Class<? extends Activity> currentOrigin;
    protected final LinkedList<AppLifecycleListener> listeners = new LinkedList<>();

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
        }

        currentOrigin = origin;
    }

    @Override
    public void onResume(Class<? extends Activity> origin) {

    }

    @Override
    public void onPause(Class<? extends Activity> origin) {

    }

    @Override
    public void onStop(Class<? extends Activity> origin) {

    }

    @Override
    public void onFinish(Class<? extends Activity> origin) {

    }

    @Override
    public void addListener(AppLifecycleListener listener) {
        Objects.requireNonNull(listener);

        listeners.add(listener);
    }

    @Override
    public void removeListener(AppLifecycleListener listener) {

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
