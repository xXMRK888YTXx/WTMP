package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.camera.CameraManagerImpl
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CameraManager
import dagger.Binds
import dagger.Module

@Module
internal interface CameraModule {
    @Binds
    fun bindCameraManger(cameraManagerImpl: CameraManagerImpl) : CameraManager
}