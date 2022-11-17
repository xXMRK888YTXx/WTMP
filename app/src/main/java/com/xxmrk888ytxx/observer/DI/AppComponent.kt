package com.xxmrk888ytxx.observer.DI

import android.content.Context
import com.xxmrk888ytxx.observer.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@AppScope
@Component(
    modules = [
        ToastManagerModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context:Context) : AppComponent
    }
}

@Scope
annotation class AppScope