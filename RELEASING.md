Take the following steps when releasing a new version of the library to JitPack:

1. Run all unit tests.
2. Run the sample, if available.
3. Increase the `versionCode` and `versionName` in the [root `build.gradle`](../build.gradle), 
following the [Semantic Versioning](http://semver.org/) standard.
4. Add the changes to the [CHANGELOG](../CHANGELOG.md).
5. Update the version number in the [README](../README.md).
6. Push the above changes.
7. Create and push a new Git release tag for the new version.
8. Add the release notes on GitHub (usually the same as CHANGELOG description).

Now your apps / other libraries can include this dependency using the new release tag.
