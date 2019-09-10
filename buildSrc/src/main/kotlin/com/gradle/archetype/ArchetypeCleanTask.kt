package com.gradle.archetype

import com.gradle.archetype.services.ProjectGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ArchetypeCleanTask: DefaultTask() {

    @TaskAction
    fun action() {
        logger.info("Cleaning...")
        ProjectGenerator.emptyTargetDir(File("${project.projectDir.absolutePath}/${ProjectGenerator.DIR_TARGET}"), logger)
    }

    override fun getGroup() = "Archetype"

    override fun getDescription() = "Cleans generated project(s)"
}