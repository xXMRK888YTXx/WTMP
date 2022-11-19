package com.xxmrk888ytxx.observer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.xxmrk888ytxx.androidextension.LogcatExtension.logcatMessageD
import com.xxmrk888ytxx.androidextension.ToastManager.ToastManager
import com.xxmrk888ytxx.camera.CameraManager
import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.observer.ui.theme.ObserverTheme
import com.xxmrk888ytxx.observer.utils.appComponent
import dagger.Component
import dagger.Component.Builder
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Button(onClick = {
                CameraManager(this).
                createPhoto(File(cacheDir,"${System.currentTimeMillis()}.jpeg"),
                onErrorCreate = {
                    logcatMessageD(it.stackTraceToString())
                },
                onSuccess = {
                    logcatMessageD("Фото сохранено")
                }
                    )
            }) {

            }

        }
    }
}