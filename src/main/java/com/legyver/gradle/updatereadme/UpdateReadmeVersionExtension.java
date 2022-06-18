package com.legyver.gradle.updatereadme;

import org.gradle.api.provider.Property;

/**
 * Supported configurations
 * <code>
 *     updateReadmeVersion {
 *             versionScheme = "SEMVER"
 *             versionSchemeVersion = "2.0"
 *     }
 * </code>
 */
public interface UpdateReadmeVersionExtension {
    Property<String> getVersionSchemeVersion();
    Property<String> getVersionScheme();
}
