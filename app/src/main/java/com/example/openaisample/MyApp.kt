package com.example.openaisample

import android.app.Application
import com.example.openaisample.data.OpenAi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


// Created by Shahid Iqbal on 2/14/2023.

class MyApp : Application() {


    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.openai.com/")
                .client(
                    OkHttpClient.Builder()
                        .callTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100, TimeUnit.SECONDS)
                        .build()
                )

                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: OpenAi by lazy {
            retrofit.create(OpenAi::class.java)
        }
    }

}