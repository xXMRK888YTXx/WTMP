package com.xxmrk888ytxx.mainscreen.DI

import dagger.Component

@Component(
    dependencies = [
        MainScreenDeps::class
    ]
)
internal interface MainScreenComponent {
    @dagger.Component.Factory
    interface Factory {
        fun create(mainScreenDeps: MainScreenDeps) : MainScreenComponent
    }
}