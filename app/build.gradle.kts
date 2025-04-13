// Top-level build file for the Zenithra project.
// This file contains configuration options common to all sub-projects/modules.

plugins {
    // Core plugins used across the project
    alias(libs.plugins.android.application) // Android Application Plugin
    alias(libs.plugins.jetbrains.kotlin.android) // Kotlin Android Plugin
    alias(libs.plugins.dagger.hilt.android) // Hilt for dependency injection
    alias(libs.plugins.ksp) // KSP for Kotlin Symbol Processing
    alias(libs.plugins.gms.google.services) // Google Services Plugin
    alias(libs.plugins.firebase.crashlytics) // Firebase Crashlytics Plugin
    id("kotlin-parcelize") // Kotlin Parcelize for Parcelable classes
}

android {
    namespace = libs.versions.namespace.get() // Project namespace
    compileSdk = libs.versions.compileSdk.get().toInt() // Compile SDK version

    defaultConfig {
        applicationId = libs.versions.applicationId.get() // Application ID
        minSdk = libs.versions.minSdk.get().toInt() // Minimum SDK version
        targetSdk = libs.versions.targetSdk.get().toInt() // Target SDK version
        versionCode = libs.versions.versionCode.get().toInt() // Version Code
        versionName = libs.versions.versionName.get() // Version Name
        multiDexEnabled = true // Enable multidex support
        vectorDrawables.useSupportLibrary = true // Use vector drawables support library
        resValue("string", "app_name", libs.versions.appName.get()) // App name resource
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Test runner
    }

    signingConfigs {
        create("release") {
            keyAlias = "zenithra"
            keyPassword = "zenithra"
            storeFile = file("../keys/Keys.jks")
            storePassword = "zenithra"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true // Enable code shrinking
            isDebuggable = false // Disable debugging for release builds
            isShrinkResources = true // Shrink resources
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro") // Proguard rules
        }
        debug {
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = true // Enable debugging for debug builds
            isMinifyEnabled = false // Disable code shrinking for debug builds
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro") // Proguard rules
        }
    }

    flavorDimensions.add("version")
    productFlavors {
        create("stage") {
            dimension = "version"
            applicationIdSuffix = ".stage"
        }
        create("prod") {
            dimension = "version"
        }
    }

    android.applicationVariants.all {
        outputs.all {
            if (outputFile != null && outputFile.name.endsWith(".apk")) {
                // Rename the output APK with version details
                (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName = outputFileName.replace("app", "zenithra_v${defaultConfig.versionName}(${defaultConfig.versionCode})")
            }
        }
    }

    androidComponents {
        onVariants { variant ->
            // Dynamically add build config fields based on variant
            variant.buildConfigFields.put("GLOB_APPNAME", com.android.build.api.variant.BuildConfigField("String", "\"${libs.versions.appName.get()}\"", "App name"))
            variant.buildConfigFields.put("APPLICATION_ID", com.android.build.api.variant.BuildConfigField("String","\"${libs.versions.namespace.get()}\"", "Package name"))
            variant.buildConfigFields.put("LANGUAGES_SUPPORT", com.android.build.api.variant.BuildConfigField("String[]", "{\"en\",\"ta\"}", "Supported languages"))

            // Add flavor-specific configurations
            when (variant.flavorName) {
                "stage" -> {
                    variant.buildConfigFields.put("IS_STAGING", com.android.build.api.variant.BuildConfigField("boolean", "true", "Is staging flavor"))
                    variant.buildConfigFields.put("BASE_URL", com.android.build.api.variant.BuildConfigField("String", "\"https://mangaverse-api.p.rapidapi.com/\"", "Base URL"))
                    variant.buildConfigFields.put("X_Rapidapi_Host", com.android.build.api.variant.BuildConfigField("String", "\"mangaverse-api.p.rapidapi.com\"", "Host"))
                    variant.buildConfigFields.put("X_Rapidapi_Key", com.android.build.api.variant.BuildConfigField("String", "\"c413f7090cmshd53154121ca86eap1352a6jsn32434e6ccb26\"", "API Key"))
                }
                "prod" -> {
                    variant.buildConfigFields.put("IS_STAGING", com.android.build.api.variant.BuildConfigField("boolean", "false", "Is production flavor"))
                    variant.buildConfigFields.put("BASE_URL", com.android.build.api.variant.BuildConfigField("String", "\"https://mangaverse-api.p.rapidapi.com/\"", "Base URL"))
                    variant.buildConfigFields.put("X_Rapidapi_Host", com.android.build.api.variant.BuildConfigField("String", "\"mangaverse-api.p.rapidapi.com\"", "Host"))
                    variant.buildConfigFields.put("X_Rapidapi_Key", com.android.build.api.variant.BuildConfigField("String", "\"c413f7090cmshd53154121ca86eap1352a6jsn32434e6ccb26\"", "API Key"))
                }
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // Set source compatibility
        targetCompatibility = JavaVersion.VERSION_17 // Set target compatibility
    }

    kotlinOptions {
        jvmTarget = "17" // Set Kotlin JVM target version
    }

    buildFeatures {
        buildConfig = true // Enable build config generation
        compose = true // Enable Jetpack Compose
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get() // Jetpack Compose compiler version
    }

    packagingOptions {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}" // Exclude certain resources
    }

    kotlin {
        sourceSets {
            getByName("main") {
                kotlin.srcDirs("build/generated/ksp/main/kotlin") // KSP source sets
            }
        }
    }
}

dependencies {
    // Firebase dependencies
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.database)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)

    // Jetpack Compose dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.ui.toolingPreview)
    implementation(libs.androidx.compose.material)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material.icons.extended)

    // AndroidX dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt DI dependencies
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Networking dependencies
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.gson.converter)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Room dependencies
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Logging
    implementation(libs.timber)

    // Navigation Compose
    implementation(libs.navigation.compose)

    // LeakCanary for memory leak detection
    debugImplementation(libs.leakcanary.android)

    // ConstraintLayout for Compose
    implementation(libs.constraintlayout.compose)
    implementation(libs.ccp)

    // Additional dependencies (CameraX, MLKit, etc.)
    implementation(libs.zxing.android.embedded)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.coil.compose)

    // Google ML Kit Face Detection
    implementation(libs.mlkit.face.detection)

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json)

    // Play Services Auth
    implementation(libs.play.services.auth)

    implementation (libs.accompanist.navigation.animation)

    // CameraX dependencies
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.extensions)
}

// Developer Information:
// Author: Anand Kumar
// Email: aanandkumarbind9682@gmail.com
// Description: Optimized build configuration for the Zenithra project using Gradle, with various dependencies for Firebase, Hilt, Jetpack Compose, Room, and more.
