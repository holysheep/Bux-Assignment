import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id(Plugins.androidLibrary)
    kotlin("android")
    kotlin("kapt")
}

apply("$rootDir/common.gradle")

dependencies {
    implementation(project(Modules.navigation))

    api(CoreLibraries.kotlin)
    api(SupportLibraries.core)
    api(SupportLibraries.appCompat)
    api(SupportLibraries.constraintLayout)

    api(Libraries.coroutinesCore)
    api(Libraries.coroutinesAndroid)

    api(Libraries.koin)
    api(Libraries.koinViewModel)
    api(Libraries.koinScope)
    api(Libraries.koinAndroidExt)

    api(Libraries.lifecycleCommon)
    api(Libraries.lifecycleCommonJava8)
    api(Libraries.lifecycleViewModel)
    api(Libraries.lifecycleLiveData)
    api(Libraries.lifecycleExtensions)
    api(Libraries.lifecycleCompiler)
    kapt(Libraries.databinding)

    api(Libraries.okHttp)
    api(Libraries.loggingInterceptor)

    api(Libraries.retrofit)
    api(Libraries.retrofitMoshiConverter)
    api(Libraries.moshi)
    api(Libraries.moshiAdapters)
    kapt(Libraries.moshiCodeGen)

    api(Libraries.scarlet)
    api(Libraries.scarletWebSocket)
    api(Libraries.scarletLifecycle)
    api(Libraries.scarletMessageAdapter)
    api(Libraries.scarletAdapterCoroutine)

    // Test
    testImplementation(TestLibraries.junit)
    testImplementation(TestLibraries.truth)
}
