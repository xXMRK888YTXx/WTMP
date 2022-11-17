package com.xxmrk888ytxx.observer.DI

import android.content.Context
import com.xxmrk888ytxx.androidextension.ToastManager.ToastManager
import com.xxmrk888ytxx.androidextension.ToastManager.ToastManagerDefImpl
import dagger.Module
import dagger.Provides

@Module
object ToastManagerModule {
    @Provides
    fun provideToastManager(context:Context) : ToastManager {
        return ToastManagerDefImpl(context)
    }
}