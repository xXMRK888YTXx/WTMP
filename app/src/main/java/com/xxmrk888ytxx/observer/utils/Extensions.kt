package com.xxmrk888ytxx.observer.utils

import android.content.Context
import com.xxmrk888ytxx.observer.App
import com.xxmrk888ytxx.observer.DI.AppComponent

val Context.appComponent : AppComponent
    get() = when(this) {
            is App -> this.appComponent
            else -> this.applicationContext.appComponent
        }