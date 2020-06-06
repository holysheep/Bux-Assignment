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

    api(Libraries.lifecycleCommon)
    api(Libraries.lifecycleCommonJava8)
    api(Libraries.lifecycleViewModel)
    api(Libraries.lifecycleLiveData)
    api(Libraries.lifecycleExtensions)
    api(Libraries.lifecycleCompiler)

    api(Libraries.navigation)
    api(Libraries.navigationUi)

    api(Libraries.okHttp)
    api(Libraries.loggingInterceptor)

    api(Libraries.retrofit)
    api(Libraries.retrofitMoshiConverter)
    api(Libraries.moshi)
    api(Libraries.moshiKotlin)
    api(Libraries.moshiAdapters)

    api(Libraries.scarlet)
    api(Libraries.scarletWebSocket)
    api(Libraries.scarletLifecycle)
    api(Libraries.scarletMessageAdapter)
    api(Libraries.scarletAdapterCoroutine)
}
