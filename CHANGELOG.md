# v0.1.1 (2016-05-06)

- Sets `minSdkVersion` to SDK 8 and compiler version to JDK 1.6.
- Adds Google Guava `Preconditions` class as a replacement for missing functionality in SDK 8.
- Removes ProGuard files (unused).

# v0.1.0 (2016-05-06)

- Renamed lifecycle activity classes from `Lifecycle*Activity` and `AppLifecycle*Activity`.
- Fixed concurrency issues with listeners: adding / removing listeners while they are being called.
- Listeners are now called in reversed order: added first, called last.
- Separate interfaces for lifecycle event listeners (e.g. `OnAppCreated`, `OnAppStopped`).

# v0.0.1 (2016-05-04)

- First implementation containing basic features and tests.
