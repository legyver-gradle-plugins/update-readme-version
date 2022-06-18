package com.legyver.gradle.updatereadme;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class VersionSchemeTest {
    @Test
    public void literalScheme() throws Exception {
        VersionScheme scheme = VersionScheme.LITERAL;
        assertThat(scheme.getMatchRegex("1.0")).isEqualTo("1.0");
        assertThat(scheme.getMatchRegex("1.0.1")).isEqualTo("1.0.1");

        assertThat(scheme.getSupportedVersions()).isEqualTo("N/A");
    }

    @Test
    public void semVer() throws Exception {
        VersionScheme scheme = VersionScheme.SEMVER;
        assertThat(scheme.getMatchRegex("2.0")).isEqualTo( "(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?");
        assertThat(scheme.getMatchRegex("3.1")).isNull();

        assertThat(scheme.getSupportedVersions()).isEqualTo("2.0");
    }
}
