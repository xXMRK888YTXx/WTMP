package com.xxmrk888ytxx.api_telegram

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepositoryFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class TelegramRepositoryFactoryImpl @Inject constructor() : TelegramRepositoryFactory  {
    override fun create(botKey: String, userId: Long): TelegramRepository {
        return TelegramRepositoryImpl(createTelegramApi(),botKey, userId)
    }

    private fun createTelegramApi() : TelegramApi {
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder().addInterceptor(logger).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.telegram.org")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(TelegramApi::class.java)
    }
}