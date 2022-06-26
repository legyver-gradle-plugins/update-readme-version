# update-readme-version
Gradle plugin to update the version of artifact in the readme files of a project
# Usage
- Configure the plugin in your projects **build.gradle**
```groovy
plugins {
    id 'com.legyver.update-readme-version' version '1.0.0'
}
```
4. Add your supported versioning scheme and version 
```groovy
updateReadmeVersion {
    versionScheme = "SEMVER"
    versionSchemeVersion = "2.0"
}
```
