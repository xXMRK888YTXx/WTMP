package com.xxmrk888ytxx.observer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CameraManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UserActivityStats
import com.xxmrk888ytxx.coredeps.SharedInterfaces.WorkerManager
import com.xxmrk888ytxx.observer.utils.appComponent
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject lateinit var workerManager: WorkerManager
    @Inject lateinit var cameraManager: CameraManager
    @Inject lateinit var userActivityStats: UserActivityStats
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.appComponent.inject(this)
        setContent {
            Button(onClick = {
            }) {
            }
        }
    }
}