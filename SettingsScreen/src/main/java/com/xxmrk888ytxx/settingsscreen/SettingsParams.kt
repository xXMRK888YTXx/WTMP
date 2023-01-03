package com.xxmrk888ytxx.settingsscreen

import SharedInterfaces.Navigator
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.settingsscreen.models.SettingsParamType

/**
 * [Ru]
 * В данном файле находятся функции которые предоставляют список элементов для
 * функции [SettingsCategory]
 */

/**
 * [En]
 * This file contains functions that provide a list of elements for
 * features [SettingsCategory]
 */

@SuppressLint("ResourceType")
@Composable
@MustBeLocalization
internal fun getFailedUnlockDeviceParams(settingsViewModel: SettingsViewModel): List<SettingsParamType> {
    val config = settingsViewModel.failedUnlockTrackedConfig.collectAsState(
        settingsViewModel.lastFailedUnlockTrackedConfig
    )
    val isTelegramConfigSetup = settingsViewModel.isTelegramConfigSetup.collectAsState(false)
    SideEffect {
        settingsViewModel.lastFailedUnlockTrackedConfig = config.value
    }
    return listOf(
        SettingsParamType.Switch(
            stringResource(R.string.Track_failed_attempts),
            R.drawable.ic_phone_lock,
            config.value.isTracked,
            onStateChanged = settingsViewModel::updateIsTrackedFailedUnlockTrackedConfig
        ),

        SettingsParamType.Switch(
            stringResource(R.string.Take_photo),
            R.drawable.ic_camera,
            config.value.makePhoto,
            isVisible = config.value.isTracked,
            onStateChanged = settingsViewModel::updateMakePhotoFailedUnlockTrackedConfig
        ),

        SettingsParamType.Switch(
            stringResource(R.string.Notify_in_Telegram),
            R.drawable.ic_telegram,
            config.value.notifyInTelegram,
            isEnable = isTelegramConfigSetup.value,
            isVisible = config.value.isTracked,
            onStateChanged = settingsViewModel::updateNotifyInTelegramFailedUnlockTrackedConfig
        ),

        SettingsParamType.Switch(
            stringResource(R.string.Attach_photo_to_message),
            R.drawable.ic_telegram,
            config.value.joinPhotoToTelegramNotify,
            isVisible = config.value.isTracked && config.value.makePhoto && config.value.notifyInTelegram,
            onStateChanged = settingsViewModel::updateJoinPhotoToTelegramNotifyFailedUnlockTrackedConfig
        ),

        )
}

@SuppressLint("ResourceType")
@Composable
@MustBeLocalization
internal fun getSucceededUnlockDeviceParams(settingsViewModel: SettingsViewModel): List<SettingsParamType> {
    val config = settingsViewModel.succeededUnlockTrackedConfig.collectAsState(
        settingsViewModel.lastSucceededUnlockTrackedConfig
    )
    val isTelegramConfigSetup = settingsViewModel.isTelegramConfigSetup.collectAsState(false)
    SideEffect {
        settingsViewModel.lastSucceededUnlockTrackedConfig = config.value
    }
    return listOf(
        SettingsParamType.Switch(
            stringResource(R.string.Track_device_unlock),
            R.drawable.ic_lock_open,
            config.value.isTracked,
            onStateChanged = settingsViewModel::updateIsTrackedSucceededUnlockTrackedConfig
        ),

        SettingsParamType.Switch(
            stringResource(R.string.Take_photo),
            R.drawable.ic_camera,
            config.value.makePhoto,
            isVisible = config.value.isTracked,
            onStateChanged = settingsViewModel::updateMakePhotoSucceededUnlockTrackedConfig,
        ),

        SettingsParamType.Switch(
            stringResource(R.string.Notify_in_Telegram),
            R.drawable.ic_telegram,
            config.value.notifyInTelegram,
            isEnable = isTelegramConfigSetup.value,
            isVisible = config.value.isTracked,
            onStateChanged = settingsViewModel::updateNotifyInTelegramSucceededUnlockTrackedConfig
        ),

        SettingsParamType.Switch(
            stringResource(R.string.Attach_photo_to_message),
            R.drawable.ic_telegram,
            config.value.joinPhotoToTelegramNotify,
            isVisible = config.value.isTracked && config.value.makePhoto && config.value.notifyInTelegram,
            onStateChanged = settingsViewModel::updateJoinPhotoToTelegramNotifySucceededUnlockTrackedConfig
        ),
    )
}

@SuppressLint("ResourceType")
@Composable
@MustBeLocalization
internal fun getAppOpenObserverParams(
    settingsViewModel: SettingsViewModel,
    navigator: Navigator,
): List<SettingsParamType> {
    val config = settingsViewModel.appOpenConfig.collectAsState(
        settingsViewModel.lastAppOpenConfig
    )
    val isTelegramConfigSetup = settingsViewModel.isTelegramConfigSetup.collectAsState(false)
    SideEffect {
        settingsViewModel.lastAppOpenConfig = config.value
    }
    return listOf(
        SettingsParamType.Switch(
            stringResource(R.string.Enable_App_Tracking),
            R.drawable.ic_apps,
            config.value.isTracked,
            onStateChanged = settingsViewModel::updateIsTrackedAppOpenConfig
        ),
        SettingsParamType.Switch(
            stringResource(R.string.Take_photo),
            R.drawable.ic_camera,
            config.value.makePhoto,
            isVisible = config.value.isTracked,
            onStateChanged = settingsViewModel::updateMakePhotoAppOpenConfig,
        ),

        SettingsParamType.Switch(
            stringResource(R.string.Notify_in_Telegram),
            R.drawable.ic_telegram,
            config.value.notifyInTelegram,
            isEnable = isTelegramConfigSetup.value,
            isVisible = config.value.isTracked,
            onStateChanged = settingsViewModel::updateNotifyInTelegramAppOpenConfig
        ),

        SettingsParamType.Switch(
            stringResource(R.string.Attach_photo_to_message),
            R.drawable.ic_telegram,
            config.value.joinPhotoToTelegramNotify,
            isVisible = config.value.isTracked && config.value.makePhoto && config.value.notifyInTelegram,
            onStateChanged = settingsViewModel::updateJoinPhotoToTelegramNotifyAppOpenConfig
        ),
        SettingsParamType.Button(
            stringResource(R.string.Application_selection),
            R.drawable.ic_settings,
            isVisible = config.value.isTracked,
            onClick = navigator::toSelectTrackedAppScreen
        )
    )
}

@SuppressLint("ResourceType")
@Composable
@MustBeLocalization
internal fun getTelegramOptionsParams(
    navigator: Navigator,
): List<SettingsParamType> {
    return listOf(
        SettingsParamType.Button(
            stringResource(R.string.Telegram_settings),
            R.drawable.ic_telegram,
            onClick = navigator::toTelegramSetupScreen
        )
    )
}

@SuppressLint("ResourceType")
@Composable
@MustBeLocalization
internal fun getAppInfoParams(settingsViewModel: SettingsViewModel): List<SettingsParamType> {
    val context = LocalContext.current
    return listOf(
        SettingsParamType.Label(
            stringResource(R.string.Application_version),
            R.drawable.ic_info,
            secondoryText = settingsViewModel.appVersion
        ),
        SettingsParamType.Button(
            stringResource(R.string.Write_to_the_developer),
            R.drawable.ic_developer,
            onClick = { settingsViewModel.sendIntentToWriteDeveloper(context) }
        ),
        SettingsParamType.Button(
            stringResource(R.string.Application_log),
            R.drawable.ic_list,
        ) {}
    )
}

@SuppressLint("ResourceType")
@Composable
@MustBeLocalization
internal fun getSecureParams(
    settingsViewModel: SettingsViewModel,
    navigator: Navigator
): List<SettingsParamType> {
    val isAppPasswordSetup = settingsViewModel.isAppPasswordSetup().collectAsState(false)
    return listOf(
    SettingsParamType.Switch(
        stringResource(R.string.Set_password_when_logging_into_app),
        R.drawable.ic_password,
        isSwitched = isAppPasswordSetup.value,
    ) {
        if(isAppPasswordSetup.value) {
            navigator.toSetupAppPasswordScreen(
                Navigator.Companion.SetupAppPasswordScreenMode.RemovePassword
            )
        } else {
          navigator.toSetupAppPasswordScreen(
              Navigator.Companion.SetupAppPasswordScreenMode.SetupPassword
          )
        }
    }
    )
}