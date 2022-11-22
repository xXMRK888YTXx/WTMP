package com.xxmrk888ytxx.observer.domain.Repositoryes

import android.graphics.Bitmap
import com.xxmrk888ytxx.api_telegram.TelegramApi
import com.xxmrk888ytxx.coredeps.Repository.TelegramRepository
import com.xxmrk888ytxx.observer.utils.Exceptions.TelegramCancelMessage
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class TelegramRepositoryImpl @AssistedInject constructor(
    private val telegramApi: TelegramApi,
    @Assisted private val botKey:String,
    @Assisted private val userId:Long
) : TelegramRepository {

    override fun sendMessage(
        text: String,
        scope: CoroutineScope,
        onSuccessful: () -> Unit,
        onError: (e: Exception) -> Unit,
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = telegramApi.sendMessage(botKey, userId, text)
                val isSuccessful = response.body()?.isSuccessful ?: false
                if(isSuccessful)
                    onSuccessful()
                else
                    onError(
                        TelegramCancelMessage(response.errorBody()?.string()),
                    )
            }catch (e:Exception) {
                onError(e)
            }
        }
    }

    override fun sendPhoto(
        image: Bitmap,
        caption: String,
        scope: CoroutineScope,
        onSuccessful: () -> Unit,
        onError: (e: Exception) -> Unit,
    ) {
        scope.launch(Dispatchers.IO) {
            try {
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
                if(isSuccessful)
                    onSuccessful()
                else
                    onError(
                        TelegramCancelMessage(response.errorBody()?.string()),
                    )
            }catch (e:Exception) {
                onError(e)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(botKey: String,userId: Long) : TelegramRepositoryImpl
    }

}