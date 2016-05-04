package com.cookingfox.android.app_lifecycle.sample;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.cookingfox.android.app_lifecycle.impl.activity.LifecyclePreferenceActivity;

public class SettingsActivity extends LifecyclePreferenceActivity {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate");

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");

        if (isFinishing()) {
            Log.i(TAG, "isFinishing");
        }

        super.onStop();
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            final PreferenceScreen preferenceScreen = getPreferenceManager()
                    .createPreferenceScreen(getActivity());
            setPreferenceScreen(preferenceScreen);
        }

    }

}
