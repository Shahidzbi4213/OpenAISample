package com.example.openaisample.data

import com.example.openaisample.model.ApiData
import com.example.openaisample.model.Choice
import com.example.openaisample.model.Request
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


// Created by Shahid Iqbal on 2/14/2023.


interface OpenAi {
    @Headers("Authorization: Bearer your_key_here")
    @POST("v1/completions")
    suspend fun getCompletion(@Body request: Request): Response<ApiData>
}
