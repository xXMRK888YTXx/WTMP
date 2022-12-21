package com.xxmrk888ytxx.settingsscreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coredeps.Const.DEVELOPER_EMAIL
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ApplicationInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.models.FailedUnlockTrackedConfig
import com.xxmrk888ytxx.coredeps.models.SucceededUnlockTrackedConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val applicationInfoProvider: ApplicationInfoProvider,
    private val failedUnlockTrackedConfigChanger: FailedUnlockTrackedConfigChanger,
    private val failedUnlockTrackedConfigProvider: FailedUnlockTrackedConfigProvider,
    private val succeededUnlockTrackedConfigChanger: SucceededUnlockTrackedConfigChanger,
    private val succeededUnlockTrackedConfigProvider: SucceededUnlockTrackedConfigProvider,
) : ViewModel() {
    val testParam = mutableStateOf(false)

    @MustBeLocalization
    internal fun sendIntentToWriteDeveloper(context: Context) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$DEVELOPER_EMAIL"))
        context.startActivity(Intent.createChooser(emailIntent, "Написать разработчику"))
    }

    val appVersion: String by lazy {
        applicationInfoProvider.applicationVersion
    }

    internal val failedUnlockTrackedConfig: Flow<FailedUnlockTrackedConfig>
        get() = failedUnlockTrackedConfigProvider.config

    internal val succeededUnlockTrackedConfig: Flow<SucceededUnlockTrackedConfig>
        get() = succeededUnlockTrackedConfigProvider.config

    fun updateIsTrackedFailedUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            failedUnlockTrackedConfigChanger.updateIsTracked(state)
        }
    }

    fun updateMakePhotoFailedUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            failedUnlockTrackedConfigChanger.updateMakePhoto(state)
        }
    }

    fun updateNotifyInTelegramFailedUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            failedUnlockTrackedConfigChanger.updateNotifyInTelegram(state)
        }
    }

    fun updateJoinPhotoToTelegramNotifyFailedUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            failedUnlockTrackedConfigChanger.updateJoinPhotoToTelegramNotify(state)
        }
    }

    fun updateIsTrackedSucceededUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            succeededUnlockTrackedConfigChanger.updateIsTracked(state)
        }
    }

    fun updateMakePhotoSucceededUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            succeededUnlockTrackedConfigChanger.updateMakePhoto(state)
        }
    }

    fun updateNotifyInTelegramSucceededUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            succeededUnlockTrackedConfigChanger.updateNotifyInTelegram(state)
        }
    }

    fun updateJoinPhotoToTelegramNotifySucceededUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            succeededUnlockTrackedConfigChanger.updateJoinPhotoToTelegramNotify(state)
        }
    }

}