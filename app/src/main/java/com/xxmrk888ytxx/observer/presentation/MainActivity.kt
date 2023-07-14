package com.xxmrk888ytxx.observer.presentation

import MutliUse.LazySpacer
import SharedInterfaces.Navigator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xxmrk888ytxx.adutils.AdAppManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback.ActivityLifecycleCallback
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback.ActivityLifecycleRegister
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.BillingManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.DialogShowStateManager
import com.xxmrk888ytxx.eventdetailsscreen.EventDetailsScreen
import com.xxmrk888ytxx.eventdetailsscreen.EventDetailsViewModel
import com.xxmrk888ytxx.eventlistscreen.EventListScreen
import com.xxmrk888ytxx.eventlistscreen.EventViewModel
import com.xxmrk888ytxx.mainscreen.MainScreen
import com.xxmrk888ytxx.mainscreen.MainViewModel
import com.xxmrk888ytxx.observer.R
import com.xxmrk888ytxx.observer.Screen
import com.xxmrk888ytxx.observer.presentation.AppLoginScreen.AppOpenScreen
import com.xxmrk888ytxx.observer.presentation.AppLoginScreen.AppOpenViewModel
import com.xxmrk888ytxx.observer.utils.appComponent
import com.xxmrk888ytxx.selecttrackedappscreen.SelectTrackedAppScreen
import com.xxmrk888ytxx.selecttrackedappscreen.SelectTrackedAppViewModel
import com.xxmrk888ytxx.settingsscreen.SettingsScreen
import com.xxmrk888ytxx.settingsscreen.SettingsViewModel
import com.xxmrk888ytxx.setupapppasswordscreen.SetupAppPasswordScreen
import com.xxmrk888ytxx.setupapppasswordscreen.SetupAppPasswordViewModel
import com.xxmrk888ytxx.supportdeveloperscreen.SupportDeveloperScreen
import com.xxmrk888ytxx.supportdeveloperscreen.SupportDeveloperViewModel
import com.xxmrk888ytxx.telegramsetupscreen.TelegramSetupScreen
import com.xxmrk888ytxx.telegramsetupscreen.TelegramViewModel
import composeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import remember
import theme.*
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : AppCompatActivity(), ActivityLifecycleRegister {

    private val activityViewModel: ActivityViewModel by viewModels()
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
    @Inject internal lateinit var appOpenViewModel: Provider<AppOpenViewModel>
    @Inject lateinit var supportDeveloperViewModel: Provider<SupportDeveloperViewModel>

    @Inject lateinit var appPasswordProvider: AppPasswordProvider
    @Inject lateinit var adAppManager: AdAppManager
    @Inject lateinit var billingManager: Provider<BillingManager>
    @Inject lateinit var dialogShowStateManager: DialogShowStateManager

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        adAppManager.initAdmob()
        activityViewModel.initAppComponent(appComponent)
        activityViewModel.loadConsentForm(this)
        activityViewModel.activity = this
        billingManager.get().connectToGooglePlay()
        setContent {
            val isShowCongratulationsDialog = activityViewModel.isShowCongratulationsDialog.remember()

            val navController = rememberNavController()

            val agreementDialogState = dialogShowStateManager.isAgreementDialogNeedShow.collectAsState(
                initial = false
            )

            LaunchedEffect(key1 = Unit, block = {
                activityViewModel.navController = navController
            })

            Column(
                Modifier
                    .fillMaxSize()
                    .background(BackGroundColor))
            {
                NavHost(
                    navController = navController,
                    startDestination = getStartDestination().route
                ) {

                    composable(Screen.AppLoginScreen.route) {
                        AppOpenScreen(
                            appOpenViewModel = composeViewModel {
                                appOpenViewModel.get()
                            },
                            navController = navController,
                            activityLifecycleRegister = this@MainActivity
                        )
                    }

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
                        val screenMode = it.arguments?.getInt(Navigator.SetupAppPasswordScreenModeKey)
                            ?: return@composable
                        SetupAppPasswordScreen(
                            screenMode = screenMode,
                            setupAppPasswordViewModel = composeViewModel {
                                setupAppPasswordViewModel.get()
                            },
                            navigator = activityViewModel
                        )
                    }

                    composable(Screen.SupportDeveloperScreen.route) {
                        SupportDeveloperScreen(
                            supportDeveloperViewModel = composeViewModel() {
                                supportDeveloperViewModel.get()
                            },
                            activityLifecycleRegister = this@MainActivity
                        )
                    }
                }
                if(isShowCongratulationsDialog.value) {
                    CongratulationsDialog(activityViewModel)
                }

                if(agreementDialogState.value) {
                    AgreementDialog(
                        onConfirm = {
                            lifecycleScope.launch(Dispatchers.IO) {
                                dialogShowStateManager.setupAgreementDialog(false)
                            }
                        },
                        onCancel = this@MainActivity::finish
                    )
                }
            }
        }
        activityViewModel.onCreate(this)
    }

    @Composable
    private fun CongratulationsDialog(activityViewModel: ActivityViewModel) {
        val onDismiss = remember {
            { activityViewModel.isShowCongratulationsDialog.value = false }
        }
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = cardColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Icon(
                        painter = painterResource(R.drawable.ic_star),
                        contentDescription = "",
                        tint = checkedSettingsSwitch,
                        modifier = Modifier.size(80.dp)
                    )

                    LazySpacer(15)

                    Text(
                        stringResource(R.string.Congratulations_text),
                        fontFamily = openSansFont,
                        color = primaryFontColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W800,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    LazySpacer(15)

                    Text(
                        stringResource(R.string.Ads_will_be_disabled),
                        fontFamily = openSansFont,
                        color = primaryFontColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W800,
                        fontStyle = FontStyle.Italic
                    )

                    LazySpacer(15)

                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = checkedSettingsSwitch,
                        ),
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(80),
                    ) {
                        Text(
                            text = stringResource(R.string.Close),
                            color = primaryFontColor,
                            fontWeight = FontWeight.W700,
                            fontFamily = openSansFont,
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    }


    private fun getStartDestination(): Screen = runBlocking {
        return@runBlocking if(appPasswordProvider.isPasswordSetupFlow().first()) Screen.AppLoginScreen
            else Screen.MainScreen
    }

    override fun onStart() {
        super.onStart()
        activityViewModel.onStart()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenResumed() {
            withContext(Dispatchers.Default) {
                billingManager.get().restorePurchases()
            }
        }
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
        activityViewModel.activity = null
        activityViewModel.onDestroy()
        super.onDestroy()
    }

    override fun registerCallback(activityLifecycleCallback: ActivityLifecycleCallback) {
        activityViewModel.registerCallback(activityLifecycleCallback,this)
    }

    override fun unregisterCallback(activityLifecycleCallback: ActivityLifecycleCallback) {
        activityViewModel.unregisterCallback(activityLifecycleCallback)
    }


}