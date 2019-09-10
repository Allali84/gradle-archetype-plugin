package com.gradle.archetype.services


import org.gradle.api.logging.Logger
import java.io.File

object ProjectGenerator {

    private val DIR_TEMPLATES = "template"
    val DIR_TARGET = "generated"

    fun generate(projectDir: String, model: Project, overrideGeneratedProject: String, logger: Logger) {
        val targetDir = File("$projectDir/$DIR_TARGET")
        if (overrideGeneratedProject.toUpperCase() == "Y") {
            emptyTargetDir(targetDir, logger)
        }
        val template = File("$projectDir/$DIR_TEMPLATES")
        var fileName: String
        var destFile: File
        template.walkTopDown().forEach {
            if (it != template) {
                fileName = it.absolutePath
                fileName = fileName.replace(template.absolutePath, targetDir.absolutePath)
                fileName = fileName.replace("__projectName__", model.projectName.toLowerCase())
                if (!fileName.contains("gradle/wrapper")) {
                    fileName = fileName.replace("__ProjectName__", model.projectName.capitalize())
                    fileName = fileName.replace("__packagePath__", model.packagePath.toLowerCase())
                }
                destFile = File(fileName)
                it.copyTo(destFile, false)
                if (!destFile.isDirectory && !fileName.contains("gradle/wrapper")) {
                    var text = destFile.readText()
                    with(model) {
                        text = text.replace("@projectGroup@", projectGroup.toLowerCase())
                        text = text.replace("@projectName@", projectName.capitalize())
                        text = text.replace("@artifactId@", artifactId.toLowerCase())
                        text = text.replace("@version@", version)
                        text = text.replace("@packageName@", packageName.toLowerCase())
                    }
                    destFile.writeText(text)
                }
            }
        }
    }

    fun emptyTargetDir(targetDir: File, logger: Logger) {
        if (targetDir.deleteRecursively()) {
            logger.info("${targetDir.absolutePath} and its contents was deleted")
        } else {
            logger.info("Deleting ${targetDir.absolutePath} was skipped")
        }
    }

    data class Project (
            val projectGroup: String,
            val projectName: String,
            val artifactId: String,
            val version: String,
            val packageName: String,
            val packagePath: String
    )
}