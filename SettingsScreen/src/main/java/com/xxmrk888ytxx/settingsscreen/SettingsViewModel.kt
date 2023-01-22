package com.xxmrk888ytxx.settingsscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coredeps.Const.DEVELOPER_EMAIL
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.coredeps.SharedInterfaces.*
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.BootDeviceTrackedConfig.BootDeviceTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.BootDeviceTrackedConfig.BootDeviceTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig.TelegramConfigProvider
import com.xxmrk888ytxx.coredeps.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import toState
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val applicationInfoProvider: ApplicationInfoProvider,
    private val failedUnlockTrackedConfigChanger: FailedUnlockTrackedConfigChanger,
    private val failedUnlockTrackedConfigProvider: FailedUnlockTrackedConfigProvider,
    private val succeededUnlockTrackedConfigChanger: SucceededUnlockTrackedConfigChanger,
    private val succeededUnlockTrackedConfigProvider: SucceededUnlockTrackedConfigProvider,
    private val telegramConfigProvider: TelegramConfigProvider,
    private val appOpenConfigProvider: AppOpenConfigProvider,
    private val appOpenConfigChanger: AppOpenConfigChanger,
    private val resourcesProvider: ResourcesProvider,
    private val appPasswordProvider: AppPasswordProvider,
    private val appPasswordChanger: AppPasswordChanger,
    private val biometricAuthorizationManager: BiometricAuthorizationManager,
    private val bootDeviceTrackedConfigProvider: BootDeviceTrackedConfigProvider,
    private val bootDeviceTrackedConfigChanger: BootDeviceTrackedConfigChanger,
    private val removeAppManager: RemoveAppManager,
    private val localizationManager: LocalizationManager,
    private val permissionsManager: PermissionsManager,
    private val toastManager: ToastManager
) : ViewModel() {

    @SuppressLint("ResourceType")
    @MustBeLocalization
    internal fun sendIntentToWriteDeveloper(context: Context) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$DEVELOPER_EMAIL"))
        context.startActivity(Intent.createChooser(emailIntent,
            resourcesProvider.getString(R.string.Write_to_the_developer)))
    }

    val appVersion: String by lazy {
        applicationInfoProvider.applicationVersion
    }

    internal val isTelegramConfigSetup: Flow<Boolean>
        get() = telegramConfigProvider.telegramConfig.map { it != null }

    internal val failedUnlockTrackedConfig: Flow<FailedUnlockTrackedConfig>
        get() = failedUnlockTrackedConfigProvider.config

    internal val succeededUnlockTrackedConfig: Flow<SucceededUnlockTrackedConfig>
        get() = succeededUnlockTrackedConfigProvider.config

    internal val appOpenConfig: Flow<AppOpenConfig>
        get() = appOpenConfigProvider.config

    internal val bootDeviceConfig : Flow<BootDeviceTrackedConfig>
        get() = bootDeviceTrackedConfigProvider.config

    internal val currentSelectedLocale:MutableState<SupportedLanguage> =
        mutableStateOf(SupportedLanguage.System)

    private val _selectLocaleDialogShowState = mutableStateOf(false)

    internal val selectLocaleDialogShowState = _selectLocaleDialogShowState.toState()

    internal fun updateIsTrackedFailedUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            failedUnlockTrackedConfigChanger.updateIsTracked(state)
        }
    }

    internal fun updateCountFailedUnlockToTrigger(newCount:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            failedUnlockTrackedConfigChanger.updateCountFailedUnlockToTrigger(newCount)
        }
    }

    internal fun updateMakePhotoFailedUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            failedUnlockTrackedConfigChanger.updateMakePhoto(state)
        }
    }

    internal fun updateNotifyInTelegramFailedUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            failedUnlockTrackedConfigChanger.updateNotifyInTelegram(state)
        }
    }

    internal fun updateJoinPhotoToTelegramNotifyFailedUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            failedUnlockTrackedConfigChanger.updateJoinPhotoToTelegramNotify(state)
        }
    }

    internal fun updateIsTrackedSucceededUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            succeededUnlockTrackedConfigChanger.updateIsTracked(state)
        }
    }

    internal fun updateMakePhotoSucceededUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            succeededUnlockTrackedConfigChanger.updateMakePhoto(state)
        }
    }

    internal fun updateNotifyInTelegramSucceededUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            succeededUnlockTrackedConfigChanger.updateNotifyInTelegram(state)
        }
    }

    internal fun updateJoinPhotoToTelegramNotifySucceededUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            succeededUnlockTrackedConfigChanger.updateJoinPhotoToTelegramNotify(state)
        }
    }

    internal fun updateIsTrackedAppOpenConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            appOpenConfigChanger.updateIsTracked(state)
        }
    }

    internal fun updateMakePhotoAppOpenConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            appOpenConfigChanger.updateMakePhoto(state)
        }
    }

    internal fun updateNotifyInTelegramAppOpenConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            appOpenConfigChanger.updateNotifyInTelegram(state)
        }
    }

    internal fun updateJoinPhotoToTelegramNotifyAppOpenConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            appOpenConfigChanger.updateJoinPhotoToTelegramNotify(state)
        }
    }

    internal fun updateIsTrackedBootDeviceTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            bootDeviceTrackedConfigChanger.updateIsTracked(state)
        }
    }

    internal fun updateMakePhotoBootDeviceTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            bootDeviceTrackedConfigChanger.updateMakePhoto(state)
        }
    }

    internal fun updateNotifyInTelegramBootDeviceTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            bootDeviceTrackedConfigChanger.updateNotifyInTelegram(state)
        }
    }

    internal fun updateJoinPhotoToTelegramNotifyBootDeviceTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            bootDeviceTrackedConfigChanger.updateJoinPhotoToTelegramNotify(state)
        }
    }

    internal fun isAppPasswordSetup(): Flow<Boolean> = appPasswordProvider.isPasswordSetupFlow()

    internal fun getFingerPrintAuthorizationState() = appPasswordProvider.isFingerPrintAuthorizationEnabled()

    internal fun updateFingerPrintAuthorizationState(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            appPasswordChanger.updateFingerPrintAuthorizationState(state)
        }
    }

    internal fun removeApp() {
        removeAppManager.requestRemoveApp()
    }

    internal var lastFailedUnlockTrackedConfig: FailedUnlockTrackedConfig =
        FailedUnlockTrackedConfig(isTracked = false,
            countFailedUnlockToTrigger = 1,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false)

    internal var lastSucceededUnlockTrackedConfig: SucceededUnlockTrackedConfig =
        SucceededUnlockTrackedConfig(isTracked = false,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false)

    internal var lastAppOpenConfig: AppOpenConfig = AppOpenConfig(isTracked = false,
        makePhoto = false,
        notifyInTelegram = false,
        joinPhotoToTelegramNotify = false)

    internal var lastBootDeviceConfig: BootDeviceTrackedConfig = BootDeviceTrackedConfig(
        isTracked = false, makePhoto = false,
        notifyInTelegram = false, joinPhotoToTelegramNotify = false
    )

    internal var lastIsAppPasswordSetup : Boolean = false
    
    internal val isFingerPrintScannerAvailable : Boolean by lazy {
        biometricAuthorizationManager.isFingerPrintScannerAvailable()
    }

    internal val numberInvalidAttemptsDropDownState = mutableStateOf(false)

    internal fun openPolicyPrivacy(context: Context) {
        val url = "https://xxmrk888ytxx.github.io/wtmp_privacy.htm"
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    internal fun openTerms(context: Context) {
        val url = "https://xxmrk888ytxx.github.io/wtmp_terms.htm"
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    internal fun showSelectLocaleDialog() {
        currentSelectedLocale.value = localizationManager.currentLocalization
        _selectLocaleDialogShowState.value = true
    }

    internal fun hideSelectLocaleDialog() {
        _selectLocaleDialogShowState.value = false
        currentSelectedLocale.value = SupportedLanguage.System
    }

    fun setupCurrentSelectedLocaleAndHideLocaleDialog() {
        val currentSelectedLocale = currentSelectedLocale.value
        hideSelectLocaleDialog()
        localizationManager.setupLocalization(currentSelectedLocale)
    }

    @SuppressLint("ResourceType")
    internal fun requestIgnoreBatteryOptimisation() {
        if(!permissionsManager.isIgnoreBatteryOptimizationEnable()) {
            permissionsManager.requestIgnoreBatteryOptimization()
        } else {
            toastManager.showToast(R.string.Ignore_battery_optimization_already_enabled)
        }
    }

    internal fun openDontKillMyAppWebSite(context: Context) {
        val url = "https://dontkillmyapp.com/"
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

}