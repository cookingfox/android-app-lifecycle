package com.cookingfox.android.app_lifecycle.impl;

import com.cookingfox.android.app_lifecycle.api.AppLifecycleManager;
import com.cookingfox.android.app_lifecycle.fixture.FirstApp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by abeldebeer on 04/05/16.
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

    @Test(expected = IllegalStateException.class)
    public void initialize_should_throw_if_already_initialized() throws Exception {
        FirstApp app = new FirstApp();

        AppLifecycleProvider.initialize(app);
        AppLifecycleProvider.initialize(app);
    }

    @Test
    public void initialize_should_return_manager() throws Exception {
        AppLifecycleManager manager = AppLifecycleProvider.initialize(new FirstApp());

        assertNotNull(manager);
    }

}
