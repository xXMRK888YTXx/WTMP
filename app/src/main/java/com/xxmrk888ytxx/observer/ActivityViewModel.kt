package com.xxmrk888ytxx.observer

import SharedInterfaces.Navigator
import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback

internal class ActivityViewModel : ViewModel(),Navigator {

    private val activityCallbacks = mutableSetOf<ActivityLifecycleCallback>()

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

    override fun toSelectTrackedAppScreen() {
        navController?.navigate(Screen.SelectTrackedAppScreen.route) {
            launchSingleTop = true
        }
    }

    override fun toSetupAppPasswordScreen(
        setupAppPasswordScreenMode: Navigator.Companion.SetupAppPasswordScreenMode
    ) {
        navController?.navigate(Screen.SetupAppPasswordScreen.route) {
            launchSingleTop = true
        }

        navController?.getBackStackEntry(Screen.SetupAppPasswordScreen.route)
            ?.arguments?.putInt(
                Navigator.SetupAppPasswordScreenModeKey,
                setupAppPasswordScreenMode.modeNum
            )
    }

    fun registerCallback(activityLifecycleCallback: ActivityLifecycleCallback,activity: Activity) {
        activityCallbacks.add(activityLifecycleCallback)
        activityLifecycleCallback.onRegister(activity)
    }

    fun onCreate(activity: Activity) {
        activityCallbacks.forEach { it.onCreate(activity) }
    }

    fun onStart() {
        activityCallbacks.forEach { it.onStart() }
    }

    fun onResume() {
        activityCallbacks.forEach { it.onResume() }
    }

    fun onPause() {
        activityCallbacks.forEach { it.onPause() }
    }

    fun onStop() {
        activityCallbacks.forEach { it.onStop() }
    }

    fun onDestroy() {
        activityCallbacks.forEach { it.onDestroy() }
    }

    fun unregisterCallback(activityLifecycleCallback: ActivityLifecycleCallback) {
        activityCallbacks.remove(activityLifecycleCallback)
    }
}