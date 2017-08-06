package com.cookingfox.android.app_lifecycle.impl;

import com.cookingfox.android.app_lifecycle.api.manager.AppLifecycleManager;
import com.cookingfox.android.app_lifecycle.fixture.FirstApp;
import com.cookingfox.android.app_lifecycle.impl.manager.CrossActivityAppLifecycleManager;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link AppLifecycleProvider}.
 */
public class AppLifecycleProviderTest {

    //----------------------------------------------------------------------------------------------
    // SETUP
    //----------------------------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        AppLifecycleProvider.manager = null;
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: dispose
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void dispose_should_throw_if_not_initialized() throws Exception {
        AppLifecycleProvider.dispose();
    }

    @Test
    public void dispose_should_call_manager_dispose() throws Exception {
        final AtomicBoolean called = new AtomicBoolean(false);

        // note: manually set manager
        AppLifecycleProvider.manager = new CrossActivityAppLifecycleManager() {
            @Override
            public void dispose() {
                called.set(true);
            }
        };

        AppLifecycleProvider.dispose();

        assertTrue(called.get());
    }

    @Test
    public void dispose_should_unset_manager() throws Exception {
        AppLifecycleProvider.initialize(new FirstApp());

        assertNotNull(AppLifecycleProvider.manager);

        AppLifecycleProvider.dispose();

        assertNull(AppLifecycleProvider.manager);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: getManager
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void getManager_should_throw_if_not_created() throws Exception {
        AppLifecycleProvider.getManager();
    }

    @Test
    public void getManager_should_return_manager() throws Exception {
        AppLifecycleProvider.initialize(new FirstApp());

        AppLifecycleManager manager = AppLifecycleProvider.getManager();

        assertNotNull(manager);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: initialize
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void initialize_should_throw_if_app_null() throws Exception {
        AppLifecycleProvider.initialize(null);
    }

    @Test
    public void initialize_should_return_manager() throws Exception {
        AppLifecycleManager manager = AppLifecycleProvider.initialize(new FirstApp());

        assertNotNull(manager);
    }

}
