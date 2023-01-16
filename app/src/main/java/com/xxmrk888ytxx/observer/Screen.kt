package com.xxmrk888ytxx.observer

sealed class Screen(val route:String) {
    object MainScreen : Screen("MainScreen")
    object SettingsScreen : Screen("SettingsScreen")
    object EventListScreen : Screen("EventListScreen")
    object TelegramSetupScreen : Screen("TelegramSetupScreen")
    object EventDetailsScreen : Screen("EventDetailsScreen")
    object SelectTrackedAppScreen : Screen("SelectTrackedAppScreen")
    object SetupAppPasswordScreen : Screen("SetupAppPasswordScreen")
    object AppLoginScreen : Screen("AppLoginScreen")

    object SupportDeveloperScreen : Screen("SupportDeveloperScreen")
    object Stub : Screen("Stub")
}
