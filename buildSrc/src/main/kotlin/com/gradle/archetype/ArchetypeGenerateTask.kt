package com.gradle.archetype

import com.gradle.archetype.services.ProjectGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.Console
import java.util.*
import java.util.regex.Pattern

open class ArchetypeGenerateTask: DefaultTask() {

    private val PATTERN_NON_ALPHA_NUMERIC = Pattern.compile("[^0-9a-zA-Z]")

    var projectGroup: String = ""
    var projectName: String = ""
    var artifactId: String = ""
    var version: String = ""
    var packageName: String = ""
    var packagePath: String = ""
    var overrideGeneratedProject: String = ""

    @TaskAction
    fun action() {
        projectGroup = getParam("group", "Please enter the group value", "com.example")
        projectName = getParam("name", "Please enter the project name value", "demo")
        version = getParam("version", "Please enter the version value", "1.0-SNAPSHOT")
        artifactId = getParam("artifactId", "Please enter the artifactId value", projectName)
        packageName = getParam("namePackage", "Please enter the package name value", replaceAllNonAlphaNumericWith("$projectGroup.$projectName", "."))
        packagePath = getParam("packagePath", "Please enter the package path value", replaceAllNonAlphaNumericWith("$projectGroup/$projectName", "/"))
        overrideGeneratedProject = getParam("override", "Override generated project? (Y/N)", "N")

        val model = ProjectGenerator.Project(
                projectGroup = projectGroup,
                projectName = projectName,
                artifactId = artifactId,
                version = version,
                packageName= packageName,
                packagePath = packagePath)

        logger.info("Variables:")
        logger.info("group = $projectGroup")
        logger.info("projectName = $projectName")
        logger.info("version = $version")
        logger.info("artifactId = $artifactId")
        logger.info("packageName = $packageName")
        logger.info("packagePath = $packagePath")

        ProjectGenerator.generate(project.projectDir.absolutePath, model, overrideGeneratedProject, logger)

    }

    private fun  replaceAllNonAlphaNumericWith(name: String, replacement: String): String {
        return PATTERN_NON_ALPHA_NUMERIC.matcher(name).replaceAll(replacement)
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
            getValue(console, msg) ?: defaultValue.toString()
        } else {
            val scanner = Scanner(System.`in`)
            println(msg)
            getValue(scanner) ?: defaultValue.toString()
        }
    }

    private fun getValue(scanner: Scanner): String? {
        val value = scanner.nextLine()
        if (value == null || value.isEmpty()) {
            return null
        }
        return value
    }

    private fun getValue(console: Console, msg: String): String? {
        val value = console.readLine(msg)
        if (value == null || value.isEmpty()) {
            return null
        }
        return value
    }

    override fun getGroup() = "Archetype"

    override fun getDescription() = "Generates project(s) from template(s)"

}