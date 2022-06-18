package com.legyver.gradle.updatereadme;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateReadmeVersionPluginTest {

    @Test
    public void pluginApplies() throws Exception {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("com.legyver.update-readme-version");

        assertTrue(project.getPluginManager()
                .hasPlugin("com.legyver.update-readme-version"));

        assertNotNull(project.getTasks().getByName("readme"));
    }
}
