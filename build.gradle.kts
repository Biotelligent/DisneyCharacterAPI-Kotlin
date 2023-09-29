buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(libs.ktlint.gradle)
    }
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")