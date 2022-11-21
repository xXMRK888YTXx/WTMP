package com.xxmrk888ytxx.api_telegram

import com.xxmrk888ytxx.api_telegram.models.TelegramRequestResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TelegramApi {
    @GET("/bot{botKey}/sendMessage?")
    suspend fun sendMessage(
        @Path("botKey",encoded = true)botKey:String,
        @Query("chat_id",encoded = true)userId:Long,
        @Query("text",encoded = true)text:String) : Response<TelegramRequestResult>
}