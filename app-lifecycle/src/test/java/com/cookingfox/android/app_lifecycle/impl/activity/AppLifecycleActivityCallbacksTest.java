package com.cookingfox.android.app_lifecycle.impl.activity;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;

import com.cookingfox.android.app_lifecycle.fixture.FirstApp;
import com.cookingfox.android.app_lifecycle.impl.manager.CrossActivityAppLifecycleManager;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link AppLifecycleActivityCallbacks}.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class AppLifecycleActivityCallbacksTest {

    //----------------------------------------------------------------------------------------------
    // TESTS: constructor
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void constructor_should_throw_for_empty_app() throws Exception {
        new AppLifecycleActivityCallbacks(null, new CrossActivityAppLifecycleManager());
    }

    @Test(expected = NullPointerException.class)
    public void constructor_should_throw_for_empty_manager() throws Exception {
        new AppLifecycleActivityCallbacks(new FirstApp(), null);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: dispose
    //----------------------------------------------------------------------------------------------

    @Test
    public void dispose_should_unregister_callbacks() throws Exception {
        Application mockApp = mock(Application.class);

        AppLifecycleActivityCallbacks subject =
                new AppLifecycleActivityCallbacks(mockApp, new CrossActivityAppLifecycleManager());

        subject.dispose();

        verify(mockApp).unregisterActivityLifecycleCallbacks(subject);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: initialize
    //----------------------------------------------------------------------------------------------

    @Test
    public void initialize_should_register_callbacks() throws Exception {
        Application mockApp = mock(Application.class);

        AppLifecycleActivityCallbacks subject =
                new AppLifecycleActivityCallbacks(mockApp, new CrossActivityAppLifecycleManager());

        subject.initialize();

        verify(mockApp).registerActivityLifecycleCallbacks(subject);
    }

}
