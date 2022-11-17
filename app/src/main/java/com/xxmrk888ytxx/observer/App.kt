package com.xxmrk888ytxx.observer

import android.app.Application
import com.xxmrk888ytxx.observer.DI.AppComponent
import com.xxmrk888ytxx.observer.DI.DaggerAppComponent

class App : Application() {
    val appComponent:AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}