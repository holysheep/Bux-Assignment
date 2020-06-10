package com.assignment.core.data.di

import android.app.Application
import android.content.Context
import com.assignment.core.BuildConfig
import com.assignment.core.BuildConfig.TOKEN
import com.assignment.core.data.Constants
import com.assignment.core.data.Constants.ACCEPT_LANGUAGE
import com.assignment.core.data.Constants.BACKOFF_DURATION_BASE
import com.assignment.core.data.Constants.BACKOFF_DURATION_MAX
import com.assignment.core.data.Constants.HEADER_ACCEPT
import com.assignment.core.data.Constants.HEADER_ACCEPT_LANGUAGE
import com.assignment.core.data.Constants.HEADER_AUTH
import com.assignment.core.data.Constants.MIME_TYPE
import com.assignment.core.data.Constants.OKHTTP_CONNECT_TIMEOUT
import com.assignment.core.data.Constants.TOKEN_ACCESS
import com.assignment.core.data.service.RestService
import com.assignment.core.data.service.SocketService
import com.assignment.core.data.utils.FlowStreamAdapter
import com.assignment.core.data.utils.MoshiAdapter
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.LifecycleRegistry
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideOkHttpClient(androidApplication()) }
    single {
        provideRetrofit(
            okHttpClient = get(),
            moshi = get()
        )
    }
    single { provideRetrofitService(retrofit = get()) }
    single { provideSocketService(scarlet = get()) }
    single { provideMoshi() }

    single {
        provideScarlet(
            moshi = get(),
            okHttpClient = get(),
            application = androidApplication(),
            lifecycle = get(),
            backoffStrategy = get(),
            connectivityLifecycle = get()
        )
    }
    single {
        provideAndroidLifecycle(
            application = androidApplication(),
            lifecycleRegistry = get()
        )
    }

    single { provideLifecycleRegistry() }
    single { provideBackOffStrategy() }
}

private fun provideOkHttpClient(context: Context): OkHttpClient {
    return OkHttpClient
        .Builder()
        .addInterceptor(ChuckInterceptor(context))
        .connectTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .pingInterval(7000, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                    redactHeader(HEADER_AUTH) // redact headers that may contain sensitive information
                })
        .addInterceptor { chain ->
            val token = TOKEN_ACCESS.format(TOKEN)
            val newRequest = chain.request().newBuilder()
                .addHeader(HEADER_ACCEPT, MIME_TYPE)
                .addHeader(HEADER_ACCEPT_LANGUAGE, ACCEPT_LANGUAGE)
                .addHeader(HEADER_AUTH, token)
                .build()
            chain.proceed(newRequest)
        }
        .build()
}

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    moshi: Moshi
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()
}

private fun provideRetrofitService(retrofit: Retrofit): RestService =
    retrofit.create(RestService::class.java)

private fun provideSocketService(scarlet: Scarlet): SocketService =
    scarlet.create(SocketService::class.java)

private fun provideScarlet(
    moshi: Moshi,
    okHttpClient: OkHttpClient,
    application: Application,
    lifecycle: Lifecycle,
    connectivityLifecycle: Lifecycle,
    backoffStrategy: ExponentialWithJitterBackoffStrategy
): Scarlet {
    return Scarlet.Builder()
        .webSocketFactory(
            okHttpClient
                .newWebSocketFactory(BuildConfig.WS_BASE_URL + Constants.WS_PATH)
        )
        .lifecycle(
            AndroidLifecycle
                .ofApplicationForeground(application)
                .combineWith(lifecycle, connectivityLifecycle)
        )
        .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
        .addStreamAdapterFactory(FlowStreamAdapter.Factory)
        .backoffStrategy(backoffStrategy)
        .build()
}

fun provideLifecycleRegistry(throttleDurationMillis: Long = 0): LifecycleRegistry {
    return LifecycleRegistry(throttleDurationMillis)
}

private fun provideAndroidLifecycle(
    application: Application,
    lifecycleRegistry: LifecycleRegistry
): Lifecycle {
    return AndroidLifecycle
        .ofApplicationForeground(application)
        .combineWith(lifecycleRegistry)
}

fun provideBackOffStrategy(): ExponentialWithJitterBackoffStrategy {
    return ExponentialWithJitterBackoffStrategy(
        BACKOFF_DURATION_BASE,
        BACKOFF_DURATION_MAX
    )
}

private fun provideMoshi(): Moshi? {
    return Moshi.Builder()
        .add(MoshiAdapter())
        .build()
}