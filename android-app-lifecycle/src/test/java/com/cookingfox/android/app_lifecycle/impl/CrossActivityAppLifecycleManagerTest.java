package com.cookingfox.android.app_lifecycle.impl;

import android.app.Activity;

import com.cookingfox.android.app_lifecycle.api.AppLifecycleListener;
import com.cookingfox.android.app_lifecycle.fixture.FirstActivity;
import com.cookingfox.android.app_lifecycle.fixture.SecondActivity;
import com.cookingfox.android.app_lifecycle.fixture.ThirdActivity;

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
    public void onStart_should_not_set_origin_if_current_is_null() throws Exception {
        appLifecycleManager.onStart(FirstActivity.class);

        assertNull(appLifecycleManager.currentOrigin);
    }

    @Test
    public void onStart_should_change_origin_if_different() throws Exception {
        appLifecycleManager.onCreate(FirstActivity.class);
        appLifecycleManager.onStart(FirstActivity.class);

        assertEquals(FirstActivity.class, appLifecycleManager.currentOrigin);

        appLifecycleManager.onStart(SecondActivity.class);

        assertEquals(SecondActivity.class, appLifecycleManager.currentOrigin);

        appLifecycleManager.onStart(ThirdActivity.class);

        assertEquals(ThirdActivity.class, appLifecycleManager.currentOrigin);
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
                actualEvents.add(new OriginEvent(origin, Event.CREATE));
            }

            @Override
            public void onAppStart(Class<?> origin) {
                actualEvents.add(new OriginEvent(origin, Event.START));
            }

            @Override
            public void onAppResume(Class<?> origin) {
                actualEvents.add(new OriginEvent(origin, Event.RESUME));
            }

            @Override
            public void onAppPause(Class<?> origin) {
                actualEvents.add(new OriginEvent(origin, Event.PAUSE));
            }

            @Override
            public void onAppStop(Class<?> origin) {
                actualEvents.add(new OriginEvent(origin, Event.STOP));
            }

            @Override
            public void onAppFinish(Class<?> origin) {
                actualEvents.add(new OriginEvent(origin, Event.FINISH));
            }
        });

        // launch: start first activity
        appLifecycleManager.onCreate(FirstActivity.class); // CHANGE: create
        appLifecycleManager.onStart(FirstActivity.class); // CHANGE: start
        appLifecycleManager.onResume(FirstActivity.class); // CHANGE: resume

        // start second activity
        appLifecycleManager.onPause(FirstActivity.class); // CHANGE: pause
        appLifecycleManager.onCreate(SecondActivity.class);
        appLifecycleManager.onStart(SecondActivity.class);
        appLifecycleManager.onResume(SecondActivity.class); // CHANGE: resume
        appLifecycleManager.onStop(FirstActivity.class);

        // go back to first activity
        appLifecycleManager.onPause(SecondActivity.class); // CHANGE: pause
        appLifecycleManager.onCreate(FirstActivity.class);
        appLifecycleManager.onStart(FirstActivity.class);
        appLifecycleManager.onResume(FirstActivity.class); // CHANGE: resume
        appLifecycleManager.onStop(SecondActivity.class);
        appLifecycleManager.onFinish(SecondActivity.class);

        // go back to home screen (exit)
        appLifecycleManager.onPause(FirstActivity.class); // CHANGE: pause
        appLifecycleManager.onStop(FirstActivity.class); // CHANGE: stop
        appLifecycleManager.onFinish(FirstActivity.class); // CHANGE: finish

        final List<OriginEvent> expectedEvents = new LinkedList<>();
        expectedEvents.add(new OriginEvent(FirstActivity.class, Event.CREATE));
        expectedEvents.add(new OriginEvent(FirstActivity.class, Event.START));
        expectedEvents.add(new OriginEvent(FirstActivity.class, Event.RESUME));
        expectedEvents.add(new OriginEvent(FirstActivity.class, Event.PAUSE));
        expectedEvents.add(new OriginEvent(SecondActivity.class, Event.RESUME));
        expectedEvents.add(new OriginEvent(SecondActivity.class, Event.PAUSE));
        expectedEvents.add(new OriginEvent(FirstActivity.class, Event.RESUME));
        expectedEvents.add(new OriginEvent(FirstActivity.class, Event.PAUSE));
        expectedEvents.add(new OriginEvent(FirstActivity.class, Event.STOP));
        expectedEvents.add(new OriginEvent(FirstActivity.class, Event.FINISH));

        assertEquals(expectedEvents, actualEvents);
    }

    //----------------------------------------------------------------------------------------------
    // HELPERS
    //----------------------------------------------------------------------------------------------

    enum Event {
        CREATE,
        START,
        RESUME,
        PAUSE,
        STOP,
        FINISH,
    }

    static final class OriginEvent {
        final Class<?> origin;
        final Event event;

        public OriginEvent(Class<?> origin, Event event) {
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
