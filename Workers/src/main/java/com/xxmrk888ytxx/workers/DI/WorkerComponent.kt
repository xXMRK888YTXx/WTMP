package com.xxmrk888ytxx.workers.DI

import com.xxmrk888ytxx.coredeps.SharedInterfaces.CameraManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepositoryFactory
import dagger.Component

@Component(
    dependencies = [
        WorkerComponentDeps::class
    ]
)
@WorkerScope
internal interface WorkerComponent {
    @dagger.Component.Factory
    interface Factory {
        fun create(workerComponentDeps: WorkerComponentDeps) : WorkerComponent
    }

    val telegramRepositoryFactory:TelegramRepositoryFactory

    val cameraManager:CameraManager
}