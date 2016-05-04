package com.cookingfox.android.app_lifecycle.impl.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import com.cookingfox.android.app_lifecycle.impl.AppLifecycleProvider;

/**
 * Created by abeldebeer on 04/05/16.
 */
public class LifecyclePreferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppLifecycleProvider.getManager().onCreate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        AppLifecycleProvider.getManager().onStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppLifecycleProvider.getManager().onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        AppLifecycleProvider.getManager().onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        AppLifecycleProvider.getManager().onStop(this);

        if (isFinishing()) {
            AppLifecycleProvider.getManager().onFinish(this);
        }
    }

}
