package com.xxmrk888ytxx.observer.domain.Repositoryes

import com.xxmrk888ytxx.api_telegram.TelegramApi
import com.xxmrk888ytxx.coredeps.Repository.TelegramRepository
import com.xxmrk888ytxx.observer.utils.Exceptions.TelegramCancelMessage
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TelegramRepositoryImpl @AssistedInject constructor(
    private val telegramApi: TelegramApi,
    @Assisted private val botKey:String,
    @Assisted private val userId:Long
) : TelegramRepository {

    override fun sendMessage(
        text: String,
        coroutinesScope: CoroutineScope,
        onSuccessful: () -> Unit,
        onError: (e: Exception, body: String?) -> Unit,
    ) {
        coroutinesScope.launch(Dispatchers.IO) {
            try {
                val response = telegramApi.sendMessage(botKey, userId, text)
                val isSuccessful = response.body()?.isSuccessful ?: false
                if(isSuccessful)
                    onSuccessful()
                else
                    onError(
                        TelegramCancelMessage("TelegramCancelMessage"),
                        response.errorBody()?.string()
                    )
            }catch (e:Exception) {
                onError(e,null)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(botKey: String,userId: Long) : TelegramRepositoryImpl
    }

}