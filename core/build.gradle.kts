plugins {
    id(Plugins.androidLibrary)
}

apply("$rootDir/common.gradle")

dependencies {
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

    api(Libraries.lifecycleViewModel)
    api(Libraries.lifecycleExtensions)
    api(Libraries.lifecycleCompiler)

    api(Libraries.navigation)
    api(Libraries.navigationUi)

    api(Libraries.okHttp)
    api(Libraries.loggingInterceptor)

    api(Libraries.retrofit)
    api(Libraries.retrofitMoshiConverter)
//    kapt(Libraries.moshiCodeGen)
//    implementation(Libraries.moshi)
    implementation(Libraries.scarlet)
    implementation(Libraries.scarletWebSocket)
    implementation(Libraries.scarletLifecycle)
    implementation(Libraries.scarletMessageAdapter)
}