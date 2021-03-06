package com.cookingfox.android.app_lifecycle.impl.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.cookingfox.android.app_lifecycle.impl.AppLifecycleProvider;

/**
 * Preference Activity implementation with app lifecycle support.
 */
public class AppLifecyclePreferenceActivity extends PreferenceActivity {

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppLifecycleProvider.getManager().onCreate(this);
    }

    @CallSuper
    @Override
    protected void onStart() {
        super.onStart();

        AppLifecycleProvider.getManager().onStart(this);
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();

        AppLifecycleProvider.getManager().onResume(this);
    }

    @CallSuper
    @Override
    protected void onPause() {
        super.onPause();

        AppLifecycleProvider.getManager().onPause(this);
    }

    @CallSuper
    @Override
    protected void onStop() {
        super.onStop();

        AppLifecycleProvider.getManager().onStop(this);

        if (isFinishing()) {
            AppLifecycleProvider.getManager().onFinish(this);
        }
    }

}
