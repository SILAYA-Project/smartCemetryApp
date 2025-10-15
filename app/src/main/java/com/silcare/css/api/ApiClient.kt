package com.silcare.css.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CloudinaryApiClient {
    private const val CLOUDINARY_BASE_URL = "https://api.cloudinary.com/v1_1/drwwnzu1r/"

    val apiService: CloudinaryApiService by lazy {
        Retrofit.Builder()
            .baseUrl(CLOUDINARY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CloudinaryApiService::class.java)
    }
}