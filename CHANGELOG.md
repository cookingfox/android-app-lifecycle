# Android App Lifecycle: Change Log

## v0.1.7 (2016-06-17)

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
