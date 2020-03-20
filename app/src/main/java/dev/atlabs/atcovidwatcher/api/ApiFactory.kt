package dev.atlabs.atcovidwatcher.api

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    // OkhttpClient for building http request url
    private val apiClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(apiClient)
        .baseUrl("https://covid19.mathdro.id/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val apiService = retrofit().create(ApiService::class.java)
}
