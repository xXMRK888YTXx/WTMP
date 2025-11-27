package com.xxmrk888ytxx.observer.presentation

import SharedInterfaces.Navigator
import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback.ActivityLifecycleCallback
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PurchaseCallback.PurchaseListener
import com.xxmrk888ytxx.observer.DI.AppComponent
import com.xxmrk888ytxx.observer.Screen

internal class ActivityViewModel : ViewModel(),Navigator, PurchaseListener {

    private val activityCallbacks = mutableSetOf<ActivityLifecycleCallback>()

    internal val isShowCongratulationsDialog = mutableStateOf(false)

    var navController:NavController? = null

    private var appComponent:AppComponent? = null

    @SuppressLint("StaticFieldLeak")
    internal var activity:Activity? = null

    fun initAppComponent(appComponent: AppComponent) {
        if(this.appComponent != null) return
        this.appComponent = appComponent
        appComponent.purchaseListenerManager.registerListener(this)
    }

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
        navController?.navigate("${Screen.EventDetailsScreen.route}/$eventId") {
            launchSingleTop = true
        }
    }

    override fun toSelectTrackedAppScreen() {
        navController?.navigate(Screen.SelectTrackedAppScreen.route) {
            launchSingleTop = true
        }
    }

    override fun toSetupAppPasswordScreen(
        setupAppPasswordScreenMode: Navigator.Companion.SetupAppPasswordScreenMode
    ) {
        navController?.navigate("${Screen.SetupAppPasswordScreen.route}/${setupAppPasswordScreenMode.modeNum}") {
            launchSingleTop = true
        }
    }

    fun registerCallback(activityLifecycleCallback: ActivityLifecycleCallback, activity: Activity) {
        if(activityCallbacks.add(activityLifecycleCallback))
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

    override fun onCleared() {
        super.onCleared()
        appComponent?.purchaseListenerManager?.unregisterListener(this)
        activity = null
    }

    override fun onPurchase() {
        isShowCongratulationsDialog.value = true
    }
}