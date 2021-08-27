package com.mohammadosman.structuredconcurrency_kotlincoroutines.framework.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiInstance {
    private const val BASE_URL =
        "https://jsonplaceholder.typicode.com/"

    private fun loggingInterceptor():
            HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return interceptor
    }

    private fun addClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .build()
    }

    private fun instance(): Retrofit {
        return Retrofit.Builder()
            .client(addClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val instance by lazy {
        instance().create(MainApiService::class.java)
    }
}