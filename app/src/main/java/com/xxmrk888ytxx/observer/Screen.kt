package com.xxmrk888ytxx.observer

sealed class Screen(val route:String) {
    object MainScreen : Screen("MainScreen")
}
