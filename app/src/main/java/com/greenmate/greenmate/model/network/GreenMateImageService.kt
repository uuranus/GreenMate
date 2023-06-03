package com.greenmate.greenmate.model.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface GreenMateImageService {

    @Multipart
    @POST("/{imageName}")
    suspend fun saveImage(
        @Path("imageName") imageName: String,
        @Part photo: MultipartBody.Part,
        @Part("content") body: RequestBody,
    ): Response<Map<String, Any>>
}