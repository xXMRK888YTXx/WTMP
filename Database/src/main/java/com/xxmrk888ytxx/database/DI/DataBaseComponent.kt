package com.xxmrk888ytxx.database.DI

import android.content.Context
import com.xxmrk888ytxx.database.DI.modules.DataBaseModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataBaseModule::class
    ]
)
@DataBaseScope
internal interface DataBaseComponent {
    @dagger.Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context) : DataBaseComponent
    }
}