package com.legyver.gradle.updatereadme;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Versioning schemes supported by this plugin.
 * These are expected provide a matching regular expression for the text to be replaced.
 */
public enum VersionScheme {
    /**
     * Semantic Versioning scheme allows updating versions in README's where the existing version in the README is a valid SemVer version,
     * as per known SemVer regular expression (currently just SemVer 2.0)
     */
    SEMVER(new HashMap<>() {
        {
            //this pattern comes from https://semver.org/#is-there-a-suggested-regular-expression-regex-to-check-a-semver-string
            //only the beginning (^) and end ($) markers were removed
            this.put("2.0", "(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?");
        }
    }),
    /**
     * Literal versioning will only replace versions where the versions in the README exactly match the one identified
     */
    LITERAL (new HashMap<>()) {
        @Override
        public String getMatchRegex(String version) {
            return version;
        }
        @Override
        public String getSupportedVersions() {
            return "N/A";
        }
    };
    private final Map<String, String> versionedRegexes;

    VersionScheme(Map<String, String> versionedRegexes) {
        this.versionedRegexes = versionedRegexes;
    }

    public String getMatchRegex(String version) {
        return versionedRegexes.get(version);
    }

    public String getSupportedVersions() {
        return versionedRegexes.keySet().stream().collect(Collectors.joining(", "));
    }
}
