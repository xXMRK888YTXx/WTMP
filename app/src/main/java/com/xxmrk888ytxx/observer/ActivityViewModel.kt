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
}