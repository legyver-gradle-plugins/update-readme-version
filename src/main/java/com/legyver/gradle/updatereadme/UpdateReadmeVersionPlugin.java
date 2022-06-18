package com.legyver.gradle.updatereadme;

import groovy.lang.Closure;
import org.gradle.api.AntBuilder;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.internal.project.DefaultAntBuilderFactory;
import org.gradle.api.internal.project.ant.AntLoggingAdapterFactory;
import org.gradle.api.internal.project.ant.DefaultAntLoggingAdapterFactory;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.util.HashMap;

/**
 * Plugin to auto-update the readme file usage example with the current project version.
 * Gradle target to execute: readme
 */
public class UpdateReadmeVersionPlugin implements Plugin<Project> {
    private static final Logger logger = Logging.getLogger(UpdateReadmeVersionPlugin.class);
    private static final AntLoggingAdapterFactory antLoggingAdapterFactory = new DefaultAntLoggingAdapterFactory();

    @Override
    public void apply(Project project) {
        UpdateReadmeVersionExtension extension = project.getExtensions()
                .create("updateReadmeVersion", UpdateReadmeVersionExtension.class);
        project.task("readme").doLast(new Closure(null) {
            @Override
            public Object call(Object... args) {
                replaceVersion(project, extension);
                return null;
            }
        });
    }

    private void replaceVersion(Project project, UpdateReadmeVersionExtension extension) {
        String stringVersionScheme = extension.getVersionScheme().get();
        logger.debug("Found version scheme: {}", stringVersionScheme);

        String schemeVersion = extension.getVersionSchemeVersion().get();
        logger.debug("Found version scheme: {}", schemeVersion);

        String projectVersion = project.getVersion().toString();
        logger.debug("Found project version: {}", projectVersion);
        VersionScheme versionScheme = VersionScheme.valueOf(stringVersionScheme);

        String matchRegex = versionScheme.getMatchRegex(schemeVersion);
        if (matchRegex == null) {
            throw new RuntimeException("Unknown version for scheme [" + versionScheme + "]: " + schemeVersion
                    + ". Supported versions are: [" + versionScheme.getSupportedVersions() + "]");
        }

        String newVersion = "version: \'" + projectVersion + "\'";
        String replaceVersionRegex = "version\\: \'" + matchRegex + "\'";

        AntBuilder ant = new DefaultAntBuilderFactory(project, antLoggingAdapterFactory).create();
        ant.invokeMethod("replaceregexp", new Object[] {
            new HashMap<String, Object>() {
                {
                    this.put("match", replaceVersionRegex);
                    this.put("replace", newVersion);
                    this.put("flags", 'g');
                    this.put("byline", true);
                }
            }, new Closure<Object>(null) {
                @Override
                public Object call(Object... args) {
                    return ant.invokeMethod("fileset", new Object[] {
                            new HashMap<String, Object>() {
                                {
                                    this.put("dir", ".");
                                    this.put("includes", "README.md");
                                    this.put("casesensitive", false);
                                }
                            }
                    });
                }
            }
        });
    }
}
