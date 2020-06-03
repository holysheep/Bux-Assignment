buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(GradlePlugins.gradle)
        classpath(GradlePlugins.kotlinGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}