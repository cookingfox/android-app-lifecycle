Take the following steps when releasing a new version of the library:

1. Increase the `versionCode` and `versionName` in the [root `build.gradle`](../build.gradle), 
following the [Semantic Versioning](http://semver.org/) standard.
2. Add the changes to the [CHANGELOG](../CHANGELOG.md).
3. Update the version number in the [README](../README.md).
4. Push the above changes.
5. Create and push a new Git release tag for the new version.
