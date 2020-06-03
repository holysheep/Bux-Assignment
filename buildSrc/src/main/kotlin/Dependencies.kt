object CoreLibraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object SupportLibraries {
    const val core = "androidx.core:core-ktx:${Versions.coreVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintVersion}"
}

object Libraries {
    // Coroutines
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"

    // Lifecycle
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVersion}"

    // Navigation
    const val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    // DI
    const val koin = "org.koin:koin-core:${Versions.koinVersion}"
    const val koinScope = "org.koin:koin-androidx-scope:${Versions.koinVersion}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koinVersion}"
    const val koinAndroidExt = "org.koin:koin-androidx-ext:${Versions.koinVersion}"

    // Network
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttpVersion}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpVersion}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshi = "com.squareup.moshi:moshi::${Versions.moshi}"
    const val moshiCodeGen = "com.squareup.moshi:moshi::${Versions.moshi}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    const val scarlet = "com.squareup.retrofit2:converter-moshi:${Versions.scarlet}"
    const val scarletWebSocket = "com.squareup.retrofit2:converter-moshi:${Versions.scarlet}"
    const val scarletLifecycle = "com.squareup.retrofit2:converter-moshi:${Versions.scarlet}"
    const val scarletMessageAdapter = "com.squareup.retrofit2:converter-moshi:${Versions.scarlet}"

    // Test todo
}
