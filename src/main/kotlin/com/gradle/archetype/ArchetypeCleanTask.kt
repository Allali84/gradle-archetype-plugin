package com.gradle.archetype

import com.gradle.archetype.services.ProjectGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

class ArchetypeCleanTask: DefaultTask() {

    @TaskAction
    fun action() {
        logger.info("Cleaning...")
        // TODO DIR_TARGET value
        val targetDir = File(this::class.java.getResource("$project.projectDir.name/$ProjectGenerator.DIR_TARGET").file)
        if (targetDir.deleteRecursively()) {
            logger.info("$targetDir.absolutePath and its contents was deleted")
        } else {
            logger.info("Deleting $targetDir.getAbsolutePath() was skipped")
        }
    }

    override fun getGroup() = "Archetype"

    override fun getDescription() = "Cleans generated project(s)"
}