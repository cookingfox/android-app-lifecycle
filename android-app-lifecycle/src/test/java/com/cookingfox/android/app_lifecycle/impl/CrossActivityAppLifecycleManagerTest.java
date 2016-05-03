package com.cookingfox.android.app_lifecycle.impl;

import android.app.Activity;

import com.cookingfox.android.app_lifecycle.api.AppLifecycleListener;
import com.cookingfox.android.app_lifecycle.fixture.FirstActivity;
import com.cookingfox.android.app_lifecycle.fixture.SecondActivity;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalArgumentException.class)
    public void onCreate_should_throw_if_origin_not_activity() throws Exception {
        appLifecycleManager.onCreate((Class) IllegalArgumentException.class);
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
    public void onStart_should_only_call_listeners_if_same_origin() throws Exception {
        final AtomicInteger counter = new AtomicInteger(0);
        final Class<? extends Activity> targetOrigin = FirstActivity.class;

        appLifecycleManager.addListener(new DefaultAppLifecycleListener() {
            @Override
            public void onAppStart(Class<?> origin) {
                counter.incrementAndGet();
            }
        });

        // these should not be called
        appLifecycleManager.onStart(SecondActivity.class);
        appLifecycleManager.onStart(SecondActivity.class);

        appLifecycleManager.onCreate(targetOrigin);
        appLifecycleManager.onStart(targetOrigin);
        appLifecycleManager.onStart(SecondActivity.class);

        assertEquals(1, counter.get());
    }

    @Test
    public void onStart_should_call_listeners_only_once() throws Exception {
        final AtomicInteger counter = new AtomicInteger(0);
        final Class<? extends Activity> targetOrigin = FirstActivity.class;

        appLifecycleManager.addListener(new DefaultAppLifecycleListener() {
            @Override
            public void onAppStart(Class<?> origin) {
                counter.incrementAndGet();
            }
        });

        appLifecycleManager.onCreate(targetOrigin);
        appLifecycleManager.onStart(targetOrigin);
        appLifecycleManager.onStart(targetOrigin);

        assertEquals(1, counter.get());
    }

    @Test
    public void onStart_should_not_set_origin_if_current_is_null() throws Exception {
        appLifecycleManager.onStart(FirstActivity.class);

        assertNull(appLifecycleManager.currentOrigin);
    }

    @Test(expected = NullPointerException.class)
    public void onResume_should_throw_if_null() throws Exception {
        appLifecycleManager.onResume(null);
    }

    @Test
    public void onResume_should_call_listeners_only_once() throws Exception {
        final AtomicInteger counter = new AtomicInteger(0);
        final Class<? extends Activity> targetOrigin = FirstActivity.class;

        appLifecycleManager.addListener(new DefaultAppLifecycleListener() {
            @Override
            public void onAppResume(Class<?> origin) {
                counter.incrementAndGet();
            }
        });

        appLifecycleManager.onCreate(targetOrigin);
        appLifecycleManager.onStart(targetOrigin);
        appLifecycleManager.onResume(targetOrigin);
        appLifecycleManager.onResume(targetOrigin);

        assertEquals(1, counter.get());
    }

    //----------------------------------------------------------------------------------------------
    // FUNCTIONAL TESTS
    //----------------------------------------------------------------------------------------------

    @Test
    public void functional_expected_flow() throws Exception {
        final List<OriginEvent> actualEvents = new LinkedList<>();

        appLifecycleManager.addListener(new AppLifecycleListener() {
            @Override
            public void onAppCreate(Class<?> origin) {
                actualEvents.add(new OriginEvent(origin, AppLifecycleEvent.CREATE));
            }

            @Override
            public void onAppStart(Class<?> origin) {
                actualEvents.add(new OriginEvent(origin, AppLifecycleEvent.START));
            }

            @Override
            public void onAppResume(Class<?> origin) {
                actualEvents.add(new OriginEvent(origin, AppLifecycleEvent.RESUME));
            }

            @Override
            public void onAppPause(Class<?> origin) {
                actualEvents.add(new OriginEvent(origin, AppLifecycleEvent.PAUSE));
            }

            @Override
            public void onAppStop(Class<?> origin) {
                actualEvents.add(new OriginEvent(origin, AppLifecycleEvent.STOP));
            }

            @Override
            public void onAppFinish(Class<?> origin) {
                actualEvents.add(new OriginEvent(origin, AppLifecycleEvent.FINISH));
            }
        });

        // launch: start first activity
        appLifecycleManager.onCreate(FirstActivity.class); // NOTIFY: create
        appLifecycleManager.onStart(FirstActivity.class); // NOTIFY: start
        appLifecycleManager.onResume(FirstActivity.class); // NOTIFY: resume

        // start second activity
        appLifecycleManager.onPause(FirstActivity.class); // NOTIFY: pause
        appLifecycleManager.onCreate(SecondActivity.class);
        appLifecycleManager.onStart(SecondActivity.class);
        appLifecycleManager.onResume(SecondActivity.class); // NOTIFY: resume
        appLifecycleManager.onStop(FirstActivity.class);

        // go back to first activity
        appLifecycleManager.onPause(SecondActivity.class); // NOTIFY: pause
        appLifecycleManager.onCreate(FirstActivity.class);
        appLifecycleManager.onStart(FirstActivity.class);
        appLifecycleManager.onResume(FirstActivity.class); // NOTIFY: resume
        appLifecycleManager.onStop(SecondActivity.class);
        appLifecycleManager.onFinish(SecondActivity.class);

        // go back to home screen (exit)
        appLifecycleManager.onPause(FirstActivity.class); // NOTIFY: pause
        appLifecycleManager.onStop(FirstActivity.class); // NOTIFY: stop
        appLifecycleManager.onFinish(FirstActivity.class); // NOTIFY: finish

        final List<OriginEvent> expectedEvents = new LinkedList<>();
        expectedEvents.add(new OriginEvent(FirstActivity.class, AppLifecycleEvent.CREATE));
        expectedEvents.add(new OriginEvent(FirstActivity.class, AppLifecycleEvent.START));
        expectedEvents.add(new OriginEvent(FirstActivity.class, AppLifecycleEvent.RESUME));
        expectedEvents.add(new OriginEvent(FirstActivity.class, AppLifecycleEvent.PAUSE));
        expectedEvents.add(new OriginEvent(SecondActivity.class, AppLifecycleEvent.RESUME));
        expectedEvents.add(new OriginEvent(SecondActivity.class, AppLifecycleEvent.PAUSE));
        expectedEvents.add(new OriginEvent(FirstActivity.class, AppLifecycleEvent.RESUME));
        expectedEvents.add(new OriginEvent(FirstActivity.class, AppLifecycleEvent.PAUSE));
        expectedEvents.add(new OriginEvent(FirstActivity.class, AppLifecycleEvent.STOP));
        expectedEvents.add(new OriginEvent(FirstActivity.class, AppLifecycleEvent.FINISH));

        assertEquals(expectedEvents, actualEvents);
    }

    //----------------------------------------------------------------------------------------------
    // HELPERS
    //----------------------------------------------------------------------------------------------

    static final class OriginEvent {
        final Class<?> origin;
        final AppLifecycleEvent event;

        public OriginEvent(Class<?> origin, AppLifecycleEvent event) {
            this.origin = Objects.requireNonNull(origin);
            this.event = Objects.requireNonNull(event);
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof OriginEvent && o.hashCode() == hashCode();
        }

        @Override
        public int hashCode() {
            int result = origin.hashCode();
            result = 31 * result + event.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "OriginEvent{" +
                    "origin=" + origin.getSimpleName() +
                    ", event=" + event +
                    '}';
        }
    }

}
