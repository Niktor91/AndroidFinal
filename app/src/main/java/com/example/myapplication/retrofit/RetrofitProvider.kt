package com.example.myapplication.retrofit

import com.example.myapplication.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitProvider {
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }
    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val movieService: MovieService = retrofit.create(MovieService::class.java)

}