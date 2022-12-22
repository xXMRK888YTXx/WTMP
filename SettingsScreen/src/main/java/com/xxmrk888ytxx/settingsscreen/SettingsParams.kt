package com.xxmrk888ytxx.settingsscreen

import SharedInterfaces.Navigator
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.coredeps.models.FailedUnlockTrackedConfig
import com.xxmrk888ytxx.coredeps.models.SucceededUnlockTrackedConfig
import com.xxmrk888ytxx.settingsscreen.models.SettingsParamType
import remember

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
internal fun getFailedUnlockDeviceParams(settingsViewModel: SettingsViewModel) : List<SettingsParamType> {
   val config = settingsViewModel.failedUnlockTrackedConfig.collectAsState(
       FailedUnlockTrackedConfig(isTracked = false,
           makePhoto = false,
           notifyInTelegram = false,
           joinPhotoToTelegramNotify = false)
   )
   val isTelegramConfigSetup = settingsViewModel.isTelegramConfigSetup.collectAsState(false)
    return listOf(
        SettingsParamType.Switch(
            "Отслеживать неудачные попытки",
            R.drawable.ic_phone_lock,
            config.value.isTracked,
            onStateChanged = settingsViewModel::updateIsTrackedFailedUnlockTrackedConfig
        ),

        SettingsParamType.Switch(
            "Делать фотографию при неудачной попытке входа",
            R.drawable.ic_camera,
            config.value.makePhoto,
            isVisible = config.value.isTracked,
            onStateChanged = settingsViewModel::updateMakePhotoFailedUnlockTrackedConfig
        ),

        SettingsParamType.Switch(
            "Уведомлять в Telegram",
            R.drawable.ic_telegram,
            config.value.notifyInTelegram,
            isEnable = isTelegramConfigSetup.value,
            isVisible = config.value.isTracked,
            onStateChanged = settingsViewModel::updateNotifyInTelegramFailedUnlockTrackedConfig
        ),

        SettingsParamType.Switch(
            "Прикрепить фото к сообщению в Telegram",
            R.drawable.ic_telegram,
            config.value.joinPhotoToTelegramNotify,
            isVisible = config.value.isTracked&&config.value.makePhoto&&config.value.notifyInTelegram,
            onStateChanged = settingsViewModel::updateJoinPhotoToTelegramNotifyFailedUnlockTrackedConfig
        ),

        )
}

@SuppressLint("ResourceType")
@Composable
@MustBeLocalization
internal fun getSucceededUnlockDeviceParams(settingsViewModel:SettingsViewModel) : List<SettingsParamType> {
    val config = settingsViewModel.succeededUnlockTrackedConfig.collectAsState(
        SucceededUnlockTrackedConfig(isTracked = false,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false)
    )
    val isTelegramConfigSetup = settingsViewModel.isTelegramConfigSetup.collectAsState(false)
    return listOf(
        SettingsParamType.Switch(
            "Отслеживать разблокировки устройства",
            R.drawable.ic_lock_open,
            config.value.isTracked,
            onStateChanged = settingsViewModel::updateIsTrackedSucceededUnlockTrackedConfig
        ),

        SettingsParamType.Switch(
            "Делать фотографию при разблокировке устройства",
            R.drawable.ic_camera,
            config.value.makePhoto,
            isVisible = config.value.isTracked,
            onStateChanged = settingsViewModel::updateMakePhotoSucceededUnlockTrackedConfig,
        ),

        SettingsParamType.Switch(
            "Уведомлять в Telegram",
            R.drawable.ic_telegram,
            config.value.notifyInTelegram,
            isEnable = isTelegramConfigSetup.value,
            isVisible = config.value.isTracked,
            onStateChanged = settingsViewModel::updateNotifyInTelegramSucceededUnlockTrackedConfig
        ),

        SettingsParamType.Switch(
            "Прикрепить фото к сообщению в Telegram",
            R.drawable.ic_telegram,
            config.value.joinPhotoToTelegramNotify,
            isVisible = config.value.isTracked&&config.value.makePhoto&&config.value.notifyInTelegram,
            onStateChanged = settingsViewModel::updateJoinPhotoToTelegramNotifySucceededUnlockTrackedConfig
        ),
    )
}

@SuppressLint("ResourceType")
@Composable
internal fun test(settingsViewModel: SettingsViewModel) : List<SettingsParamType> {
    val testParam = settingsViewModel.testParam.remember()
    return listOf(
        SettingsParamType.Button(
            "Test 1",R.drawable.ic_back_arrow
        ) {},
        SettingsParamType.Switch(
            "Test 1",R.drawable.ic_back_arrow,
            true
        ) {},
        SettingsParamType.Button(
            "Test 2",R.drawable.ic_back_arrow
        ) {},
        SettingsParamType.Switch(
            "Test 2",R.drawable.ic_back_arrow,
            testParam.value
        ) {
            settingsViewModel.testParam.value = it
        },
        SettingsParamType.Button(
            "Test 3",R.drawable.ic_back_arrow
        ) {},
        SettingsParamType.Switch(
            "Test 3",R.drawable.ic_back_arrow,
            false
        ) {},
    )
}

@SuppressLint("ResourceType")
@Composable
@MustBeLocalization
internal fun getAppOpenObserverParams(settingsViewModel: SettingsViewModel) : List<SettingsParamType> {
    val test = remember {
        mutableStateOf(false)
    }
    return listOf(
        SettingsParamType.Switch(
            "Включение отслежевания приложений",
            R.drawable.ic_apps,
            test.value
        ) {
            test.value = it
        },
        SettingsParamType.Button(
            "Настройка отслеживания",
            R.drawable.ic_settings,
            isVisible = test.value
        ) {}
    )
}

@SuppressLint("ResourceType")
@Composable
@MustBeLocalization
internal fun getTelegramOptionsParams(
    settingsViewModel: SettingsViewModel,
    navigator: Navigator
) : List<SettingsParamType> {
    return listOf(
        SettingsParamType.Button(
            "Настройки Telegram",
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
            "Версия приложения",
            R.drawable.ic_info,
            secondoryText = settingsViewModel.appVersion
        ),
        SettingsParamType.Button(
            "Написать разработчику",
            R.drawable.ic_developer,
            onClick = { settingsViewModel.sendIntentToWriteDeveloper(context) }
        ),
        SettingsParamType.Button(
            "Журнал приложения",
            R.drawable.ic_list,
        ) {}
    )
}
@SuppressLint("ResourceType")
@Composable
@MustBeLocalization
internal fun getSecureParams(settingsViewModel: SettingsViewModel): List<SettingsParamType> {

    return listOf(
        SettingsParamType.Button(
            "Установить пароль при входе в приложение",
            R.drawable.ic_password
        ) {}
    )
}