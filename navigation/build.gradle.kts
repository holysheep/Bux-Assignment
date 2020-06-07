apply("$rootDir/common.gradle")

plugins {
    id(Plugins.androidLibrary)
}

dependencies {
    api(Libraries.navigation)
    api(Libraries.navigationUi)
}