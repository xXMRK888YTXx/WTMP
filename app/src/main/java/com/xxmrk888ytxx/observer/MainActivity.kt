package com.xxmrk888ytxx.observer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import com.xxmrk888ytxx.androidextension.LogcatExtension.logcatMessageD
import com.xxmrk888ytxx.observer.domain.Repositoryes.TelegramRepositoryImpl
import com.xxmrk888ytxx.observer.utils.appComponent
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject lateinit var telegramRepository: TelegramRepositoryImpl.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.appComponent.inject(this)
        setContent {
            Button(onClick = {
                   telegramRepository.create(
                       "5809437736:AAGeze6SwGWOM3CyoRK-WJRIAi_SFK2MT64",
                       599078884
                   ).sendMessage(
                       text = "Hello from android",
                       onSuccessful = {
                           logcatMessageD("Message sending")
                       },
                       onError = { e: Exception, body: String? ->
                           logcatMessageD("Exception:${e.message}, body:$body")
                       }
                   )
            }) {

            }
        }
    }
}