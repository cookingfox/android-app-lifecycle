package com.cookingfox.android.app_lifecycle.impl;

import android.app.Activity;

import com.cookingfox.android.app_lifecycle.fixture.FirstActivity;
import com.cookingfox.android.app_lifecycle.fixture.SecondActivity;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

/**
 * Created by abeldebeer on 02/05/16.
 */
public class CrossActivityAppLifecycleManagerTest {

    private CrossActivityAppLifecycleManager appLifecycleManager;

    @Before
    public void setUp() throws Exception {
        appLifecycleManager = new CrossActivityAppLifecycleManager();
    }

    @Test(expected = NullPointerException.class)
    public void addListener_should_throw_if_null() throws Exception {
        appLifecycleManager.addListener(null);
    }

    @Test
    public void addListener_should_support_multiple_listeners() throws Exception {
        final AtomicInteger counter = new AtomicInteger(0);
        final int numListeners = 3;

        for (int i = 0; i < numListeners; i++) {
            appLifecycleManager.addListener(new DefaultAppLifecycleListener() {
                @Override
                public void onAppCreate(Class<?> origin) {
                    counter.incrementAndGet();
                }
            });
        }

        appLifecycleManager.onCreate(FirstActivity.class);

        assertEquals(numListeners, counter.get());
    }

    @Test(expected = NullPointerException.class)
    public void onCreate_should_throw_if_null() throws Exception {
        appLifecycleManager.onCreate(null);
    }

    @Test
    public void onCreate_should_call_listener_once() throws Exception {
        final AtomicInteger counter = new AtomicInteger(0);
        final Class<? extends Activity> targetOrigin = FirstActivity.class;

        appLifecycleManager.addListener(new DefaultAppLifecycleListener() {
            @Override
            public void onAppCreate(Class<?> origin) {
                counter.incrementAndGet();
            }
        });

        appLifecycleManager.onCreate(targetOrigin);
        appLifecycleManager.onCreate(SecondActivity.class);
        appLifecycleManager.onCreate(FirstActivity.class);

        assertEquals(1, counter.get());
        assertEquals(targetOrigin, appLifecycleManager.currentOrigin);
    }

    @Test(expected = NullPointerException.class)
    public void onStart_should_throw_if_null() throws Exception {
        appLifecycleManager.onStart(null);
    }

    @Test
    public void onStart_should_set_current_origin_if_new() throws Exception {
        final Class<? extends Activity> targetOrigin = SecondActivity.class;

        appLifecycleManager.onCreate(FirstActivity.class);
        appLifecycleManager.onStart(targetOrigin);

        assertEquals(targetOrigin, appLifecycleManager.currentOrigin);
    }

}
