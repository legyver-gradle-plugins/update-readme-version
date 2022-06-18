# update-readme-version
Gradle plugin to update the version of artifact in the readme files of a project
# Usage
1. Clone the project and install it into your local plugin repo

You will need to define your local plugin directory to publish it to
```groovy
publishing {
    repositories {
        maven {
            name = 'localPluginRepository'
            url = 'C:/dev/local-plugin-repository'
        }
    }
}
```

You will then be able to install it using the publish command

```shell
gradlew publish
```
2. Add mavenLocal to your project's **settings.gradle** plugin management section
```groovy
pluginManagement {
    repositories {
        mavenCentral()
        maven {
            name = 'localPluginRepository'
            url = 'C:/dev/local-plugin-repository'
        }
    }
}
```
3. Configure the plugin in your projects **build.gradle**
```groovy
plugins {
    id 'com.legyver.update-readme-version' version '0.1.0'
}
```
4. Add your supported versioning scheme and version
```groovy
updateReadmeVersion {
    versionScheme = "SEMVER"
    versionSchemeVersion = "2.0"
}
```
