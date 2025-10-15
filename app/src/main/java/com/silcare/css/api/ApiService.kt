package com.silcare.css.api

import com.google.rpc.context.AttributeContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CloudinaryApiService {
    @Multipart
    @POST("image/upload")
    suspend fun uploadToCloudinary(
        @Part image: MultipartBody.Part,
        @Part("upload_preset") preset: RequestBody
    ): Response<CloudinaryResponse>
}
