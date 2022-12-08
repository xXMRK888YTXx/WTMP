package com.xxmrk888ytxx.settingsscreen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.settingsscreen.models.SettingsParamType
import remember

@SuppressLint("ResourceType")
@Composable
@MustBeLocalization
internal fun getFailedUnlockDeviceParams(settingsViewModel: SettingsViewModel) : List<SettingsParamType> {
    val test = remember {
        mutableStateOf(false)
    }
    return listOf(
        SettingsParamType.CheckBox(
            "Отслеживать неудачные попытки",
            R.drawable.ic_phone_lock,
            test.value,
        ) {
            test.value = it
        },

        SettingsParamType.CheckBox(
            "Делать фотографию при неудачной попытке входа",
            R.drawable.ic_camera,
            true,
            isVisible = test.value
        ) {},

        SettingsParamType.CheckBox(
            "Уведомлять в Telegram",
            R.drawable.ic_telegram,
            false,
            isVisible = test.value
        ) {},

        SettingsParamType.CheckBox(
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
        SettingsParamType.CheckBox(
            "Отслеживать разблокировки устройства",
            R.drawable.ic_lock_open,
            test.value,
        ) {
            test.value = it
        },

        SettingsParamType.CheckBox(
            "Делать фотографию при разблокировке устройства",
            R.drawable.ic_camera,
            true,
            isVisible = test.value
        ) {},

        SettingsParamType.CheckBox(
            "Уведомлять в Telegram",
            R.drawable.ic_telegram,
            false,
            isVisible = test.value
        ) {},

        SettingsParamType.CheckBox(
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
        SettingsParamType.CheckBox(
            "Test 1",R.drawable.ic_back_arrow,
            true
        ) {},
        SettingsParamType.Button(
            "Test 2",R.drawable.ic_back_arrow
        ) {},
        SettingsParamType.CheckBox(
            "Test 2",R.drawable.ic_back_arrow,
            testParam.value
        ) {
            settingsViewModel.testParam.value = it
        },
        SettingsParamType.Button(
            "Test 3",R.drawable.ic_back_arrow
        ) {},
        SettingsParamType.CheckBox(
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
        SettingsParamType.CheckBox(
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