package com.xxmrk888ytxx.settingsscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coredeps.Const.DEVELOPER_EMAIL
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ApplicationInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.BiometricAuthorizationManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig.TelegramConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ResourcesProvider
import com.xxmrk888ytxx.coredeps.models.AppOpenConfig
import com.xxmrk888ytxx.coredeps.models.FailedUnlockTrackedConfig
import com.xxmrk888ytxx.coredeps.models.SucceededUnlockTrackedConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
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

    fun isAppPasswordSetup(): Flow<Boolean> = appPasswordProvider.isPasswordSetupFlow()

    fun getFingerPrintAuthorizationState() = appPasswordProvider.isFingerPrintAuthorizationEnabled()

    fun updateFingerPrintAuthorizationState(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            appPasswordChanger.updateFingerPrintAuthorizationState(state)
        }
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

    internal var lastIsAppPasswordSetup : Boolean = false
    
    internal val isFingerPrintScannerAvailable : Boolean by lazy {
        biometricAuthorizationManager.isFingerPrintScannerAvailable()
    }

    internal val numberInvalidAttemptsDropDownState = mutableStateOf(false)
}