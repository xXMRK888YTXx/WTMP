package com.xxmrk888ytxx.api_telegram

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun telegramApiFactory() : TelegramApi {
    val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttpClient = OkHttpClient.Builder().addInterceptor(logger).build()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.telegram.org")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(TelegramApi::class.java)
}