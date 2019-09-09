package com.gradle.archetype.services

import java.io.File

object ProjectGenerator {

    private val DIR_TEMPLATES = "/template"
    val DIR_TARGET = "generated"

    fun generate(projectDir: String, model: Project?) {
        val template = File(this::class.java.getResource(DIR_TEMPLATES).file)
        val targetDir = File(this::class.java.getResource("$projectDir/$DIR_TARGET").file)
        template.walkTopDown().forEach {
            println(it)
        }
        targetDir.walkTopDown().forEach {
            println(it)
        }
    }

    data class Project (
            val projectGroup: String,
            val projectName: String,
            val artifactId: String,
            val version: String,
            val packageName: String
    )
}