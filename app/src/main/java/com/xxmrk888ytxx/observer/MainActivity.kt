package com.xxmrk888ytxx.observer

import SharedInterfaces.Navigator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleRegister
import com.xxmrk888ytxx.eventdetailsscreen.EventDetailsScreen
import com.xxmrk888ytxx.eventdetailsscreen.EventDetailsViewModel
import com.xxmrk888ytxx.eventlistscreen.EventListScreen
import com.xxmrk888ytxx.eventlistscreen.EventViewModel
import com.xxmrk888ytxx.mainscreen.MainScreen
import com.xxmrk888ytxx.mainscreen.MainViewModel
import com.xxmrk888ytxx.observer.utils.appComponent
import com.xxmrk888ytxx.selecttrackedappscreen.SelectTrackedAppScreen
import com.xxmrk888ytxx.selecttrackedappscreen.SelectTrackedAppViewModel
import com.xxmrk888ytxx.settingsscreen.SettingsScreen
import com.xxmrk888ytxx.settingsscreen.SettingsViewModel
import com.xxmrk888ytxx.setupapppasswordscreen.SetupAppPasswordScreen
import com.xxmrk888ytxx.setupapppasswordscreen.SetupAppPasswordViewModel
import com.xxmrk888ytxx.telegramsetupscreen.TelegramSetupScreen
import com.xxmrk888ytxx.telegramsetupscreen.TelegramViewModel
import composeViewModel
import theme.BackGroundColor
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : FragmentActivity(),ActivityLifecycleRegister {

    private val activityViewModel:ActivityViewModel by viewModels()
    //Screens ViewModels
    /**
     * [Ru]
     * Всё ViewModels экранов должны создаваться в функции [composeViewModel]
     * [En]
     * All screen ViewModels must be created in the [composeViewModel] function
     */
    @Inject lateinit var mainViewModel: Provider<MainViewModel>
    @Inject lateinit var settingsViewModel: Provider<SettingsViewModel>
    @Inject lateinit var eventViewModel: Provider<EventViewModel>
    @Inject lateinit var telegramViewModel: Provider<TelegramViewModel>
    @Inject lateinit var eventDetailsViewModel: EventDetailsViewModel.Factory
    @Inject lateinit var selectTrackedAppViewModel: Provider<SelectTrackedAppViewModel>
    @Inject lateinit var setupAppPasswordViewModel: Provider<SetupAppPasswordViewModel>

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContent {
            val navController = rememberNavController()
            activityViewModel.navController = navController
            Column(Modifier
                .fillMaxSize()
                .background(BackGroundColor))
            {
                NavHost(navController = navController, startDestination = Screen.MainScreen.route) {

                    composable(Screen.MainScreen.route) {
                        MainScreen(
                            composeViewModel { mainViewModel.get() },
                            navigator = activityViewModel,
                            activityLifecycleRegister = this@MainActivity
                        )
                    }

                    composable(Screen.SettingsScreen.route) {
                        SettingsScreen(
                            composeViewModel { settingsViewModel.get() },
                            navigator = activityViewModel
                        )
                    }

                    composable(Screen.EventListScreen.route) {
                        EventListScreen(
                            eventViewModel = composeViewModel { eventViewModel.get() },
                            navigator = activityViewModel
                        )
                    }

                    composable(Screen.TelegramSetupScreen.route) {
                        TelegramSetupScreen(
                            telegramViewModel = composeViewModel {
                                telegramViewModel.get()
                            },
                            navigator = activityViewModel
                        )
                    }

                    composable(Screen.EventDetailsScreen.route) {
                        val eventId = it.arguments?.getInt(Navigator.EventDetailsScreenKey)
                            ?: return@composable

                        EventDetailsScreen(composeViewModel {
                            eventDetailsViewModel.create(eventId)
                        },
                        navigator = activityViewModel
                        )
                    }

                    composable(Screen.SelectTrackedAppScreen.route) {
                        SelectTrackedAppScreen(
                            selectTrackedAppViewModel = composeViewModel {
                                selectTrackedAppViewModel.get()
                            },
                            navigator = activityViewModel)
                    }

                    composable(Screen.SetupAppPasswordScreen.route) {
                        SetupAppPasswordScreen(
                            setupAppPasswordViewModel = composeViewModel {
                                setupAppPasswordViewModel.get()
                            }
                        )
                    }

                }
            }
        }
        activityViewModel.onCreate(this)
    }

    override fun onStart() {
        super.onStart()
        activityViewModel.onStart()
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        activityViewModel.onPause()
    }

    override fun onStop() {
        super.onStop()
        activityViewModel.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        activityViewModel.onDestroy()
    }

    override fun registerCallback(activityLifecycleCallback: ActivityLifecycleCallback) {
        activityViewModel.registerCallback(activityLifecycleCallback,this)
    }

    override fun unregisterCallback(activityLifecycleCallback: ActivityLifecycleCallback) {
        activityViewModel.unregisterCallback(activityLifecycleCallback)
    }
}