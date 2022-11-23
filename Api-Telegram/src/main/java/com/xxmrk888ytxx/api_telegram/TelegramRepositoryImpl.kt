package com.xxmrk888ytxx.api_telegram

import android.graphics.Bitmap
import com.xxmrk888ytxx.coredeps.Exceptions.TelegramCancelMessage
import com.xxmrk888ytxx.coredeps.Repository.TelegramRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class TelegramRepositoryImpl @AssistedInject constructor(
    private val telegramApi: TelegramApi,
    @Assisted private val botKey:String,
    @Assisted private val userId:Long
) : TelegramRepository {

    override suspend fun sendMessage(
        text: String,
    ) {
        val response = telegramApi.sendMessage(botKey, userId, text)
        val isSuccessful = response.body()?.isSuccessful ?: false
        if (!isSuccessful) {
            throw TelegramCancelMessage(response.errorBody()?.string())
        }
    }


    override suspend fun sendPhoto(
        image: Bitmap,
        caption: String,
    ) {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG,70,stream)
        val byteArray = stream.toByteArray()
        stream.close()
        val body = MultipartBody.Part.createFormData(
            "photo",filename = "photo",
            byteArray.toRequestBody("multipart/form-data".toMediaType())
        )
        val response = telegramApi.sendPhoto(botKey, userId,caption,body)
        val isSuccessful = response.body()?.isSuccessful ?: false
        if(!isSuccessful) {
            throw TelegramCancelMessage(response.errorBody()?.string())
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(botKey: String,userId: Long) : TelegramRepositoryImpl
    }
}
