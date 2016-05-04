package com.cookingfox.android.app_lifecycle.impl.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cookingfox.android.app_lifecycle.impl.AppLifecycleProvider;

/**
 * App Compat Activity implementation with app lifecycle support.
 */
public class LifecycleAppCompatActivity extends AppCompatActivity {

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
