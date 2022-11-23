package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.camera.CameraManagerImpl
import com.xxmrk888ytxx.coredeps.Interfaces.Repository.CameraManager
import dagger.Binds
import dagger.Module

@Module
interface CameraModule {
    @Binds
    fun bindCameraManger(cameraManagerImpl: CameraManagerImpl) : CameraManager
}