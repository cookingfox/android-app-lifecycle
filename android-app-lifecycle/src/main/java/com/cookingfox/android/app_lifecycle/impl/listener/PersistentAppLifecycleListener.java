package com.cookingfox.android.app_lifecycle.impl.listener;

import com.cookingfox.android.app_lifecycle.api.AppLifecycleListener;

/**
 * No-operation implementation of app lifecycle listener that will not be removed on application
 * finish.
 *
 * @see AppLifecycleListener#onAppFinished(Class)
 */
public class PersistentAppLifecycleListener extends DefaultAppLifecycleListener {
}
