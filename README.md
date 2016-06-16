# Android App Lifecycle

In an Android app, activities control the lifecycle. However, when attempting to separate logic from
Android components, and when the application spans multiple activities, the custom code is typically
more interested in the APPLICATION lifecycle. Questions that arise can contain:

- _"How do I know in component X when the application is running in the background?"_
- _"How can I perform actions when the application is finishing?"_

This library makes working with the application lifecycle easier, by providing clear hooks
(`onAppCreated`, `onAppStopped`, etc.) and by allowing activities to trigger lifecycle events with
little extra effort.

This library is compatible with Android from SDK 8 (Froyo).

[![Build Status](https://travis-ci.org/cookingfox/android-app-lifecycle.svg?branch=master)](https://travis-ci.org/cookingfox/android-app-lifecycle)
[![](https://jitpack.io/v/cookingfox/android-app-lifecycle.svg)](https://jitpack.io/#cookingfox/android-app-lifecycle)

## Installation

This library uses [JitPack](https://jitpack.io/#cookingfox/android-app-lifecycle) for distribution.

Add the JitPack repository to your Android project's ROOT `build.gradle` file:

```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Add the dependency to your app's `build.gradle` file:

```groovy
dependencies {
    compile 'com.github.cookingfox:android-app-lifecycle:0.1.5'
}
```

## Sample

There's a sample application showing the basic usage of the library in the `/sample` directory.

## Usage

### Library initialization

The recommended way to start using the library is to subclass the `android.app.Application` class
and call the provider's initialize method in the `onCreate()` method:

```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppLifecycleProvider.initialize(this);
    }
}
```

There are probably some operations you would like to perform every time the app is created and
finished (destroyed), e.g. initializing and disposing your favorite dependency injection library.
For an example of the recommended way to implement this, see
[Persistent listener](#persistent-listener).

Don't forget to add a reference to this class to your `AndroidManifest.xml`, for example:

```xml
<application
    android:name=".App"
    android:...>
```

### Activity integration

The easiest way to integrate the library with your activities is by subclassing the provided
`AppLifecycleActivity` classes. Here's an example for an `AppCompatActivity`:

```java
public class MainActivity extends LifecycleAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // make sure you call super
        super.onCreate(savedInstanceState);

        ...
    }
}
```

As shown above, make sure you always call super when you override the `Activity` lifecycle methods.

If you have a custom `Activity` implementation which cannot subclass the provided
`AppLifecycleActivity` classes, then it is easy to integrate the library. Just call the
corresponding methods on the app lifecycle manager. The only exception is the `onStop()` method,
which must also contain the `onFinish()` call:

```java
@Override
protected void onStop() {
    super.onStop();

    AppLifecycleProvider.getManager().onStop(this);

    if (isFinishing()) {
        AppLifecycleProvider.getManager().onFinish(this);
    }
}
```

### App lifecycle events

The following application lifecycle events are supported:

- `onAppCreated`: When the first activity is created.
- `onAppStarted`: When a new activity is started, or when the current activity is brought back from
background to foreground.
- `onAppResumed`: When the current activity is resumed by bringing it back to foreground, or after
starting a new activity.
- `onAppPaused`: When the current activity is paused by bringing it to background, or by starting a
new activity.
- `onAppStopped`: When the current activity is brought to background.
- `onAppFinished`: When the last activity finished (exit application).

### Listening for app lifecycle events

If you want to listen for all app lifecycle events, it is most useful to implement
`AppLifecycleListener`:

```java
AppLifecycleProvider.getManager().addListener(new AppLifecycleListener() {
    @Override
    public void onAppCreated(Class<?> origin) {
    }

    ...

    @Override
    public void onAppFinished(Class<?> origin) {
    }
});
```

In these cases `origin` is a reference to the activity that triggered the event. The wildcard type
is used instead of typing to Activity, to make it easier to test these methods in a context that
is not directly bound to Android (e.g. a controller or presenter).

If you're only interested in a few app lifecycle events, it is useful to override
`DefaultAppLifecycleListener`, which is a no-operation implementation of `AppLifecycleListener`.

There are also listener interfaces available for every app lifecycle event, which can be used if you
are only interested in a single event, for example:

```java
AppLifecycleProvider.getManager().addListener(new OnAppCreated() {
    @Override
    public void onAppCreated(Class<?> origin) {
    }
});
```

#### Depend on `AppLifecycleListenable`

Only activities must call the event trigger methods (e.g. `AppLifecycleManager.onCreate(activity)`).
To prevent non-activity components from calling these methods, and also to decouple your app
lifecycle-interested code from Android, it is recommended to depend on the `AppLifecycleListenable`
interface, which is extended by `AppLifecycleManager`. This interface allows you to add and remove
event listeners.

For example, when using the [Chefling](https://github.com/cookingfox/chefling-di-java) dependency 
injection container, it is smart to map the manager to the listenable interface:

```java
// create chefling container
Container container = Chefling.createContainer();

// map listenable interface to manager instance
container.mapInstance(AppLifecycleListenable.class, AppLifecycleProvider.getManager());
```

And then use it somewhere else:

```java
// implement chefling lifecycle interface
public class ExampleController implements LifeCycle {
    final AppLifecycleListenable appLifecycle;

    // dependency resolved by chefling
    public ExampleController(AppLifecycleListenable appLifecycle) {
        this.appLifecycle = appLifecycle;
    }

    // chefling lifecycle: initialize
    public void initialize() {
        appLifecycle.addListener(appLifecycleListener);
    }

    // chefling lifecycle: dispose
    public void dispose() {
        appLifecycle.removeListener(appLifecycleListener);
    }

    final AppLifecycleListener appLifecycleListener = new AppLifecycleListener() { ... }
}
```

#### Persistent listener

When you press the back button on an Android device from the "main" (root) activity, the
`onAppFinished` app lifecycle event is triggered. This can be interpreted as the application having
exited. However, there will still be an application process running, which means you can start it
again from the background processes (e.g. by opening the "switch to application" menu from the
device).

The issue that occurs is that, after the `onAppFinished` app lifecycle event is triggered, all app
lifecycle listeners are removed, as clean-up. However, we want one listener to persist, to listen
for the `onAppCreated` app lifecycle event. This can be accomplished by adding a persistent
listener, which will not be removed during the clean-up process.

Typically you will not have more than one persistent listener in your application. It is recommended
to add this listener after initialization in your `android.app.Application` implementation (see
[Library initialization](#library-initialization)):

```java
AppLifecycleProvider.getManager().addListener(new PersistentAppLifecycleListener() {
    @Override
    public void onAppCreated(Class<?> origin) {
    }

    @Override
    public void onAppFinished(Class<?> origin) {
    }
});
```
