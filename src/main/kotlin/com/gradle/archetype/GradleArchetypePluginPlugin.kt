/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.gradle.archetype

import org.gradle.api.Project
import org.gradle.api.Plugin

/**
 * A simple 'hello world' plugin.
 */
class GradleArchetypePluginPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        // Register a task
        project.tasks.register("greeting") {
            it.doLast {
                println("Hello from plugin 'com.gradle.archetype.greeting'")
            }
        }
    }
}
