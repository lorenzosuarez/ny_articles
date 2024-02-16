// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.2.2" apply false
    alias(libs.plugins.kotlin.gradle.plugin)
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    // alias(libs.plugins.kotlin.serialization)
    // id("kotlin-kapt")
}
