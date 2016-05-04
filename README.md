# Android App Lifecycle

In an Android app, activities control the lifecycle. However, when attempting to separate logic from
Android components, and when the application spans multiple activities, the custom code is typically
more interested in the APPLICATION lifecycle. Questions that arise can contain:

_"How do I know in component X when the application is running in the background?"_
_"How can I perform actions when the application is finishing?"_

This library makes working with the application lifecycle easier, by providing clear hooks
(`onAppCreated`, `onAppStopped`, etc.) and by allowing activities to trigger lifecycle events with
little extra effort.

## Installation

This library uses [JitPack](https://jitpack.io/) for distribution.

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
    compile 'org.bitbucket.cookingfox:android-app-lifecycle:0.0.1'
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

Don't forget to add a reference to this class to your `AndroidManifest.xml`, for example:

```xml
<application
    android:name=".App"
    android:...>
```

### Activity integration

The easiest way to integrate the library with your activities is by subclassing the provided
`LifecycleActivity` classes. Here's an example for an `AppCompatActivity`:

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
`LifecycleActivity` classes, then it is easy to integrate the library. Just call the corresponding
methods on the app lifecycle manager. The only exception is the `onStop()` method, which must also
contain the `onFinish()` call:

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

### Listening for app lifecycle events

TODO: custom components (e.g. controllers) should depend on `AppLifecycleListenable`
TODO: persistent listener (for Application)

### App lifecycle events

TODO: explain application lifecycle events
TODO: separate event interfaces (e.g. OnAppCreated)
