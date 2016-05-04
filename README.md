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

## Usage

TODO: explain application lifecycle events
TODO: initialization from Application
TODO: extending provided lifecycle activities
TODO: custom components (e.g. controllers) should depend on `AppLifecycleListenable`
TODO: separate event interfaces (e.g. OnAppCreated)
TODO: persistent listener (for Application)

## Sample

There's a sample application showing the usage of the library in the `/sample` directory.
