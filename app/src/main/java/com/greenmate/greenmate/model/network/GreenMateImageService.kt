package com.greenmate.greenmate.model.network

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface GreenMateImageService {

    @POST("/{imageName}")
    suspend fun changePlantImage(
        @Path("imageName") imageName: String,
        @Body body: RequestBody,
    ): Response<Map<String, Any>>
}