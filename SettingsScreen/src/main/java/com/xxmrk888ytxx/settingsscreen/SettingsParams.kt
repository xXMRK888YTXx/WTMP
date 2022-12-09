package com.xxmrk888ytxx.settingsscreen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.xxmrk888ytxx.coredeps.MustBeLocalization
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
    val test = remember {
        mutableStateOf(false)
    }
    return listOf(
        SettingsParamType.Switch(
            "Отслеживать неудачные попытки",
            R.drawable.ic_phone_lock,
            test.value,
        ) {
            test.value = it
        },

        SettingsParamType.Switch(
            "Делать фотографию при неудачной попытке входа",
            R.drawable.ic_camera,
            true,
            isVisible = test.value
        ) {},

        SettingsParamType.Switch(
            "Уведомлять в Telegram",
            R.drawable.ic_telegram,
            false,
            isVisible = test.value
        ) {},

        SettingsParamType.Switch(
            "Прикрепить фото к сообщению в Telegram",
            R.drawable.ic_telegram,
            false,
            isVisible = test.value
        ) {},

        )
}

@SuppressLint("ResourceType")
@Composable
@MustBeLocalization
internal fun getSucceededUnlockDeviceParams(settingsViewModel:SettingsViewModel) : List<SettingsParamType> {
    val test = remember {
        mutableStateOf(false)
    }
    return listOf(
        SettingsParamType.Switch(
            "Отслеживать разблокировки устройства",
            R.drawable.ic_lock_open,
            test.value,
        ) {
            test.value = it
        },

        SettingsParamType.Switch(
            "Делать фотографию при разблокировке устройства",
            R.drawable.ic_camera,
            true,
            isVisible = test.value
        ) {},

        SettingsParamType.Switch(
            "Уведомлять в Telegram",
            R.drawable.ic_telegram,
            false,
            isVisible = test.value
        ) {},

        SettingsParamType.Switch(
            "Прикрепить фото к сообщению в Telegram",
            R.drawable.ic_telegram,
            false,
            isVisible = test.value
        ) {},
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
internal fun getTelegramOptionsParams(settingsViewModel: SettingsViewModel) : List<SettingsParamType> {
    return listOf(
        SettingsParamType.Button(
            "Настройки Telegram",
            R.drawable.ic_telegram,
        ) {}
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