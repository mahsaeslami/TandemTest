package com.tandem.tandemtest.core.di

import com.tandem.tandemtest.core.Const
import com.tandem.tandemtest.core.api.MyCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Mahsa on 2022.02.26
 */
const val OK_HTTP = "OK_HTTP"
const val RETROFIT = "RETROFIT"

val networkModule = module {
    val logging = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.HEADERS)
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    factory(named(OK_HTTP)) {
        OkHttpClient.Builder()
            .readTimeout(30 * 1000, TimeUnit.MILLISECONDS)
            .writeTimeout(30 * 1000, TimeUnit.MILLISECONDS)
            .connectTimeout(30 * 1000, TimeUnit.MILLISECONDS)
            .addInterceptor(logging)
            .build()
    }

    single(named(RETROFIT)) {
        Retrofit.Builder()
            .client(get(named(OK_HTTP)))
            .baseUrl(Const.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(MyCallAdapterFactory())
            .build()
    }
}