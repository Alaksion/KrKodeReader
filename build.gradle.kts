buildscript {

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${libs.versions.hilt.get()}")
    }

    plugins {
    }

}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val kotlinVersion = libs.versions.kotlin.get()
    val agpVersion = libs.versions.agp.get()
    val detekt = libs.versions.detekt.get()

    id("io.gitlab.arturbosch.detekt") version(detekt)
    id("com.android.application") version (agpVersion) apply (false)
    id("com.android.library") version (agpVersion) apply (false)
    id("org.jetbrains.kotlin.android") version (kotlinVersion) apply (false)
    id("org.jetbrains.kotlin.jvm") version (kotlinVersion) apply (false)

}