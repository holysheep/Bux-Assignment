plugins {
    id(Plugins.androidApplication)
}

apply("$rootDir/common.gradle")

android {
    defaultConfig {
        applicationId = "com.assignment.bux"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(Modules.core))
    implementation(project(Modules.navigation))
}