import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    // Library version
    val kotlinVersion by extra("1.6.0")
    val coroutinesVersion by extra("1.5.2")
    val composeVersion by extra("1.1.0-beta04")
    val lifecycleVersion by extra("2.4.0")
    val accompanistVersion by extra("0.21.3-beta")
    val roomVersion by extra("2.4.0-rc01")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
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