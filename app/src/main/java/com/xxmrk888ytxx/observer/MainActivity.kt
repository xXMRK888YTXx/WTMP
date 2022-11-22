package com.xxmrk888ytxx.observer

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import com.xxmrk888ytxx.androidextension.LogcatExtension.logcatMessageD
import com.xxmrk888ytxx.camera.CameraManager
import com.xxmrk888ytxx.coredeps.Repository.TelegramRepository
import com.xxmrk888ytxx.observer.domain.Repositoryes.TelegramRepositoryImpl
import com.xxmrk888ytxx.observer.utils.appComponent
import java.io.File
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject lateinit var telegramRepositoryFactory: TelegramRepositoryImpl.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.appComponent.inject(this)
        val telegramRepository:TelegramRepository = telegramRepositoryFactory.create(
            "5809437736:AAGeze6SwGWOM3CyoRK-WJRIAi_SFK2MT64",
            599078884
        )
        val cameraManager = CameraManager(this)
        setContent {
            Button(onClick = {
                        telegramRepository.sendMessage(
                            text = BitmapFactory.decodeFile(File(cacheDir,"photo").path).toString(),
                            onSuccessful = {
                                logcatMessageD("Successful load")
                            },
                            onError = {
                                logcatMessageD("exception:${it.javaClass.simpleName} message:${it.message}")
                            }
                        )
            }) {

            }
        }
    }
}