// Top-level build file for the Zenithra project
// This file contains configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()           // Google's Maven repository for Android dependencies
        mavenCentral()     // Maven Central repository for general dependencies
    }

    dependencies {
        classpath(libs.gradle)                               // Gradle plugin
        classpath(libs.kotlin.gradle.plugin)                 // Kotlin plugin
        classpath(libs.androidx.navigation.safe.args.gradle.plugin) // Navigation Safe Args plugin
        classpath(libs.google.services)                      // Google services plugin (Firebase, etc.)
        classpath(libs.hilt.android.gradle.plugin)           // Hilt DI plugin for dependency injection
    }
}

plugins {
    // Firebase Crashlytics plugin (disabled for now)
    alias(libs.plugins.firebase.crashlytics) apply false

    // Android application plugin (disabled for now)
    alias(libs.plugins.android.application) apply false

    // Kotlin Android plugin (disabled for now)
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    // Dagger Hilt plugin (disabled for now)
    alias(libs.plugins.dagger.hilt.android) apply false

    // KSP (Kotlin Symbol Processing) plugin (disabled for now)
    alias(libs.plugins.ksp) apply false

    // Google Services plugin (disabled for now)
    alias(libs.plugins.gms.google.services) apply false
}

// Register a custom task to clean the build directory
tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

// Developer Information
// Author: Anand Kumar
// Email: aanandkumarbind9682@gmail.com
// Description: Top-level build file that includes common dependencies, plugins, and build tasks for the Zenithra project.
