package com.xxmrk888ytxx.observer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CameraManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UserActivityStats
import com.xxmrk888ytxx.coredeps.SharedInterfaces.WorkerManager
import com.xxmrk888ytxx.mainscreen.MainScreen
import com.xxmrk888ytxx.mainscreen.MainViewModel
import com.xxmrk888ytxx.observer.utils.appComponent
import com.xxmrk888ytxx.settingsscreen.SettingsScreen
import com.xxmrk888ytxx.settingsscreen.SettingsViewModel
import composeViewModel
import theme.BackGroundColor
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : ComponentActivity() {

    @Inject lateinit var workerManager: WorkerManager
    @Inject lateinit var cameraManager: CameraManager
    @Inject lateinit var userActivityStats: UserActivityStats
    private val activityViewModel:ActivityViewModel by viewModels()
    //Screens ViewModels
    @Inject lateinit var mainViewModel: Provider<MainViewModel>
    @Inject lateinit var settingsViewModel: Provider<SettingsViewModel>

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
                            composeViewModel {
                                mainViewModel.get()
                            },
                            navigator = activityViewModel
                        )
                    }
                    composable(Screen.SettingsScreen.route) {
                        SettingsScreen(
                            composeViewModel {
                                settingsViewModel.get()
                            },
                            navigator = activityViewModel
                        )
                    }

                }
            }
        }
    }
}