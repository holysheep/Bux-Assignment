package com.assignment.core.data.di

import android.app.Application
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
import com.assignment.core.data.Constants.TOKEN_ACCESS
import com.assignment.core.data.utils.FlowStreamAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { provideOkHttpClient() }
    single {
        provideRetrofit(
            okHttpClient = get()
        )
    }
    single { provideMoshi() }

    single {
        provideScarlet(
            moshi = get(),
            okHttpClient = get(),
            lifecycle = get(),
            backoffStrategy = get()
        )
    }
    single {
        provideAndroidLifecycle(
            application = get()
        )
    }
    single { provideBackOffStrategy() }
}

private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                    redactHeader(HEADER_AUTH) // redact headers that may contain sensitive information
                })
        .addInterceptor { chain ->
            val token = TOKEN_ACCESS.format(TOKEN)
            val newRequest = chain.request().newBuilder()
                .addHeader(HEADER_ACCEPT, ACCEPT_LANGUAGE)
                .addHeader(HEADER_ACCEPT_LANGUAGE, MIME_TYPE)
                .addHeader(HEADER_AUTH, token)
                .build()
            chain.proceed(newRequest)
        }
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(okHttpClient)
        .build()
}

private fun provideScarlet(
    moshi: Moshi,
    okHttpClient: OkHttpClient,
    lifecycle: Lifecycle,
    backoffStrategy: ExponentialWithJitterBackoffStrategy
): Scarlet.Builder {
    return Scarlet.Builder()
        .webSocketFactory(
            okHttpClient
                .newWebSocketFactory(BuildConfig.WS_BASE_URL + Constants.WS_PATH)
        )
        .lifecycle(lifecycle)
        .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
        .addStreamAdapterFactory(FlowStreamAdapter.Factory)
        .backoffStrategy(backoffStrategy)
}

private fun provideAndroidLifecycle(application: Application): Lifecycle {
    return AndroidLifecycle.ofApplicationForeground(application)
}

fun provideBackOffStrategy(): ExponentialWithJitterBackoffStrategy {
    return ExponentialWithJitterBackoffStrategy(
        BACKOFF_DURATION_BASE,
        BACKOFF_DURATION_MAX
    )
}

private fun provideMoshi(): Moshi? {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}