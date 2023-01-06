package com.xxmrk888ytxx.workers

import android.app.Application
import com.xxmrk888ytxx.coredeps.DepsProvider.DepsProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CameraManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepositoryFactory
import com.xxmrk888ytxx.workers.DI.WorkerComponentDeps
import io.mockk.mockk
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
internal class TestApp(val telegramRepositoryFactory: TelegramRepositoryFactory)
    : Application(), DepsProvider {
    override fun <DEPS : Any> provide(classType: KClass<DEPS>): DEPS {
        return object : WorkerComponentDeps {
            override val telegramRepositoryFactory: TelegramRepositoryFactory = this@TestApp.telegramRepositoryFactory
            override val cameraManager: CameraManager
                get() = mockk(relaxed = true)
        } as DEPS
    }

}