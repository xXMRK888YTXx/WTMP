package com.xxmrk888ytxx.observer

import SharedInterfaces.Navigator
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

internal class ActivityViewModel : ViewModel(),Navigator {
    var navController:NavController? = null

    override fun navigateUp() {
        navController?.navigateUp()
    }

    override fun toSettingsScreen() {
        navController?.navigate(Screen.SettingsScreen.route) {
            launchSingleTop = true
        }
    }

    override fun toEventListScreen() {
        navController?.navigate(Screen.EventListScreen.route) {
            launchSingleTop = true
        }
    }

    override fun toTelegramSetupScreen() {
        navController?.navigate(Screen.TelegramSetupScreen.route) {
            launchSingleTop = true
        }
    }

    override fun toEventDetailsScreen(eventId: Int) {
        navController?.navigate(
            Screen.EventDetailsScreen.route
        ) {
            launchSingleTop = true
        }

        navController?.getBackStackEntry(Screen.EventDetailsScreen.route)
            ?.arguments?.putInt(Navigator.EventDetailsScreenKey,eventId)
    }
}