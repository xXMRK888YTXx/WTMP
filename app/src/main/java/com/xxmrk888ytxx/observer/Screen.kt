package com.xxmrk888ytxx.observer

sealed class Screen(val route:String) {
    object MainScreen : Screen("MainScreen")
    object SettingsScreen : Screen("SettingsScreen")
    object EventListScreen : Screen("EventListScreen")
    object TelegramSetupScreen : Screen("TelegramSetupScreen")
}
