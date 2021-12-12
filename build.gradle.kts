import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    // Library version
    val kotlin_version by extra("1.6.0")
    val coroutines_version by extra("1.5.2")
    val compose_version by extra("1.1.0-beta04")
    val lifecycle_version by extra("2.4.0")
    val accompanist_version by extra("0.21.3-beta")
    val room_version by extra("2.4.0-rc01")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.4")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}