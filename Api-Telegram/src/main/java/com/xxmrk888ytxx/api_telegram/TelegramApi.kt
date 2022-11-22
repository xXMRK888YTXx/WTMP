package com.xxmrk888ytxx.api_telegram

import com.xxmrk888ytxx.api_telegram.models.TelegramRequestResult
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface TelegramApi {
    @GET("/bot{botKey}/sendMessage?")
    suspend fun sendMessage(
        @Path("botKey",encoded = true)botKey:String,
        @Query("chat_id",encoded = true)userId:Long,
        @Query("text",encoded = true)text:String) : Response<TelegramRequestResult>

    @POST("/bot{botKey}/sendPhoto?")
    @Multipart
    suspend fun sendPhoto(
        @Path("botKey",encoded = true)botKey:String,
        @Query("chat_id",encoded = true)userId:Long,
        @Query("caption",encoded = true)caption:String,
        @Part body:MultipartBody.Part,
    ) : Response<TelegramRequestResult>
}