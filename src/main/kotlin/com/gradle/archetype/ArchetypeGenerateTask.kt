package com.gradle.archetype

import com.gradle.archetype.services.ProjectGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.util.*
import java.util.regex.Pattern

class ArchetypeGenerateTask: DefaultTask() {

    private val PATTERN_NON_ALPHA_NUMERIC = Pattern.compile("[^0-9a-zA-Z]")


    @Option(option = "group", description = "Project group.")
    var projectGroup: String = ""
    @Option(option = "name", description = "Project name.")
    var projectName: String = ""
    @Option(option = "artifactId", description = "Project artifactId.")
    var artifactId: String = ""
    @Option(option = "version", description = "Project version.")
    var version: String = ""
    @Option(option = "namePackage", description = "Project package.")
    var packageName: String = ""

    @TaskAction
    fun action() {
        projectGroup = getParam("group", "Please enter the group value")
        projectName = getParam("name", "Please enter the project name value")
        version = getParam("version", "Please enter the version value", "1.0-SNAPSHOT")
        artifactId = getParam("version", "Please enter the artifactId value", projectName)
        packageName = getParam("version", "Please enter the artifactId value", replaceAllNonAlphaNumericWith("$projectGroup/$projectName"))

        val model = ProjectGenerator.Project(
                projectGroup = projectGroup,
                projectName = projectName,
                artifactId = artifactId,
                version = version,
                packageName= packageName)

        logger.info("Variables:")
        logger.info("group = $projectGroup")
        logger.info("projectName = $projectName")
        logger.info("version = $version")
        logger.info("artifactId = $artifactId")
        logger.info("packageName = $packageName")

        ProjectGenerator.generate(project.projectDir.name, model)

    }

    private fun  replaceAllNonAlphaNumericWith(name: String): String {
        return PATTERN_NON_ALPHA_NUMERIC.matcher(name).replaceAll("/")
    }

    private fun getParam( paramName: String, prompt: String, defaultValue: String? = null): String {
        var value = System.getProperty(paramName)
        if (value == null) {
            value = prompt(prompt, defaultValue)
        }
        return value
    }

    private fun prompt(message: String, defaultValue: String?) : String {
        val msg = "> $message: " + if (defaultValue != null)  "[$defaultValue]" else ""
        val console = System.console()
        return if (console != null) {
            console.readLine(msg) ?: defaultValue.toString()
        } else {
            val scanner = Scanner(System.`in`)
            println(msg)
            scanner.nextLine() ?: defaultValue.toString()
        }
    }

    override fun getGroup() = "Archetype"

    override fun getDescription() = "Generates project(s) from template(s)"

}