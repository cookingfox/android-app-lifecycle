# Android App Lifecycle: Change Log

## v0.2.1 (2016-12-08)

- Updates Gradle, plugins, build tools and dependencies.
- Increases minimum Android support to SDK 9 (Gingerbread).

## v0.1.11 (2016-08-09)

- Fixes support for distribution of sources and javadoc.

## v0.1.10 (2016-08-08)

- Adds support for distribution of sources and javadoc.

## v0.1.9 (2016-07-19)

- Adds support for `Application.ActivityLifecycleCallbacks` from SDK 14 (Ice Cream Sandwich).

## v0.1.8 (2016-07-19)

- Reverts the Android support dependencies and build tools version to keep JDK 6 support.

## v0.1.7 (2016-07-19)

- Adds `dispose()` functionality to manager and provider.
- Updates the Android support dependencies and build tools version.

## v0.1.6 (2016-06-17)

- Updates Guava Preconditions library.

## v0.1.5 (2016-06-16)

- Updates the Android support dependencies.

## v0.1.4 (2016-06-14)

- Removes maven gradle plugin related code, since it is not necessary when using JitPack.

## v0.1.3 (2016-06-13)

- Sets the correct version number in the root gradle file.

## v0.1.2 (2016-06-13)

- Replaces imported Guava Preconditions class with library.
- Renames library module to "app-lifecycle".

## v0.1.1 (2016-05-06)

- Sets `minSdkVersion` to SDK 8 and compiler version to JDK 1.6.
- Adds Google Guava `Preconditions` class as a replacement for missing functionality in SDK 8.
- Removes ProGuard files (unused).

## v0.1.0 (2016-05-06)

- Renamed lifecycle activity classes from `Lifecycle*Activity` and `AppLifecycle*Activity`.
- Fixed concurrency issues with listeners: adding / removing listeners while they are being called.
- Listeners are now called in reversed order: added first, called last.
- Separate interfaces for lifecycle event listeners (e.g. `OnAppCreated`, `OnAppStopped`).

## v0.0.1 (2016-05-04)

- First implementation containing basic features and tests.
