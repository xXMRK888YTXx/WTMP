package com.xxmrk888ytxx.settingsscreen

import SharedInterfaces.Navigator
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.coredeps.models.StorageConfig
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
internal fun getFailedUnlockDeviceParams(settingsViewModel: SettingsViewModel): List<SettingsParamType> {
    val config = settingsViewModel.failedUnlockTrackedConfig.collectAsState(
        settingsViewModel.cashedFailedUnlockTrackedConfig
    )

    val isTelegramConfigSetup = settingsViewModel.isTelegramConfigSetup.collectAsState(false)

    SideEffect {
        settingsViewModel.cashedFailedUnlockTrackedConfig = config.value
    }


    val numberInvalidAttemptsDropDownState = settingsViewModel.numberInvalidAttemptsDropDownState
        .remember()


    val numberInvalidAttemptsToTriggerDropDownList = (1..10).map {
        SettingsParamType.DropDown.DropDownItem(
            it.toString(),
            onClick = {
                settingsViewModel.updateCountFailedUnlockToTrigger(it)
                numberInvalidAttemptsDropDownState.value = false
            }
        )
    }.remember()

    val operationLimitDropDown = operationLimitDropDownItems(
        onChangeTime = settingsViewModel::updateTimeOperationLimitFailedUnlockTrackedConfig
    ).remember()

    val operationLimitDropDownState = settingsViewModel
        .operationLimitFailedUnlockDropDownState.remember()

    return listOf(
        SettingsParamType.Switch(
            stringResource(R.string.Track_failed_attempts),
            R.drawable.ic_phone_lock,
            config.value.isTracked,
            onStateChanged = settingsViewModel::updateIsTrackedFailedUnlockTrackedConfig
        ),

        SettingsParamType.DropDown(
            text = stringResource(R.string.Number_invalid_attempts_to_trigger),
            icon = R.drawable.ic_key,
            dropDownItems = numberInvalidAttemptsToTriggerDropDownList,
            onShowDropDown = { numberInvalidAttemptsDropDownState.value = true },
            onHideDropDown = { numberInvalidAttemptsDropDownState.value = false },
            isDropDownVisible = numberInvalidAttemptsDropDownState.value,
            showSelectedDropDownParam = config.value.countFailedUnlockToTrigger.toString(),
            isVisible = config.value.isTracked
        ),

        SettingsParamType.DropDown(
            text = stringResource(R.string.Work_no_more_than),
            icon = R.drawable.ic_time_limit,
            dropDownItems = operationLimitDropDown,
            onShowDropDown = { operationLimitDropDownState.value = true },
            onHideDropDown = { operationLimitDropDownState.value = false },
            isDropDownVisible = operationLimitDropDownState.value,
            showSelectedDropDownParam = operationLimitTimeNamePair()
                .find { it.first == config.value.timeOperationLimit }?.second
                ?: stringResource(id = R.string.No),
            isVisible = config.value.isTracked,
            hideDropDownAfterSelect = true
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
        settingsViewModel.cashedSucceededUnlockTrackedConfig
    )
    val isTelegramConfigSetup = settingsViewModel.isTelegramConfigSetup.collectAsState(false)
    SideEffect {
        settingsViewModel.cashedSucceededUnlockTrackedConfig = config.value
    }

    val operationLimitDropDown = operationLimitDropDownItems(
        onChangeTime = settingsViewModel::updateTimeOperationLimitSucceededUnlockTrackedConfig
    ).remember()

    val operationLimitDropDownState = settingsViewModel
        .operationLimitSucceededUnlockDropDownState.remember()
    return listOf(
        SettingsParamType.Switch(
            stringResource(R.string.Track_device_unlock),
            R.drawable.ic_lock_open,
            config.value.isTracked,
            onStateChanged = settingsViewModel::updateIsTrackedSucceededUnlockTrackedConfig
        ),

        SettingsParamType.DropDown(
            text = stringResource(R.string.Work_no_more_than),
            icon = R.drawable.ic_time_limit,
            dropDownItems = operationLimitDropDown,
            onShowDropDown = { operationLimitDropDownState.value = true },
            onHideDropDown = { operationLimitDropDownState.value = false },
            isDropDownVisible = operationLimitDropDownState.value,
            showSelectedDropDownParam = operationLimitTimeNamePair()
                .find { it.first == config.value.timeOperationLimit }?.second
                ?: stringResource(id = R.string.No),
            isVisible = config.value.isTracked,
            hideDropDownAfterSelect = true
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
        settingsViewModel.cashedAppOpenConfig
    )
    val isTelegramConfigSetup = settingsViewModel.isTelegramConfigSetup.collectAsState(false)
    SideEffect {
        settingsViewModel.cashedAppOpenConfig = config.value
    }

    val operationLimitDropDown = operationLimitDropDownItems(
        onChangeTime = settingsViewModel::updateTimeOperationLimitAppOpenConfig
    ).remember()

    val operationLimitDropDownState = settingsViewModel
        .operationLimitAppOpenDropDownState.remember()
    return listOf(
        SettingsParamType.Switch(
            stringResource(R.string.Enable_App_Tracking),
            R.drawable.ic_apps,
            config.value.isTracked,
            onStateChanged = settingsViewModel::updateIsTrackedAppOpenConfig
        ),

        SettingsParamType.DropDown(
            text = stringResource(R.string.Work_no_more_than)
                    + " (${stringResource(R.string.one_application)})",
            icon = R.drawable.ic_time_limit,
            dropDownItems = operationLimitDropDown,
            onShowDropDown = { operationLimitDropDownState.value = true },
            onHideDropDown = { operationLimitDropDownState.value = false },
            isDropDownVisible = operationLimitDropDownState.value,
            showSelectedDropDownParam = operationLimitTimeNamePair()
                .find { it.first == config.value.timeOperationLimit }?.second
                ?: stringResource(id = R.string.No),
            isVisible = config.value.isTracked,
            hideDropDownAfterSelect = true
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
            text = stringResource(R.string.Source_code),
            icon = R.drawable.ic_source_code,
            onClick = { settingsViewModel.openSourceCodePage(context) }
        ),
        SettingsParamType.Button(
            stringResource(R.string.Write_to_the_developer),
            R.drawable.ic_developer,
            onClick = { settingsViewModel.sendIntentToWriteDeveloper(context) }
        ),
        SettingsParamType.Button(
            stringResource(R.string.Privacy_policy),
            icon = R.drawable.ic_privacy_icon,
            onClick = { settingsViewModel.openPolicyPrivacy(context) }
        ),
        SettingsParamType.Button(
            stringResource(R.string.Terms),
            icon = R.drawable.ic_terms_icon,
            onClick = { settingsViewModel.openTerms(context) }
        ),
        SettingsParamType.Button(
            text = stringResource(R.string.Remove_app),
            icon = R.drawable.ic_baseline_delete_24,
            onClick = settingsViewModel::removeApp
        )
    )
}

@SuppressLint("ResourceType")
@Composable
internal fun getBootDeviceParams(settingsViewModel: SettingsViewModel): List<SettingsParamType> {
    val config = settingsViewModel.bootDeviceConfig.collectAsState(
        settingsViewModel.cashedBootDeviceConfig
    )

    val isTelegramConfigSetup = settingsViewModel.isTelegramConfigSetup.collectAsState(false)

    SideEffect {
        settingsViewModel.cashedBootDeviceConfig = config.value
    }

    return listOf(
        SettingsParamType.Switch(
            stringResource(R.string.Device_startup_track),
            R.drawable.ic_off,
            config.value.isTracked,
            onStateChanged = settingsViewModel::updateIsTrackedBootDeviceTrackedConfig
        ),

        SettingsParamType.Switch(
            stringResource(R.string.Take_photo),
            R.drawable.ic_camera,
            config.value.makePhoto,
            isVisible = config.value.isTracked,
            onStateChanged = settingsViewModel::updateMakePhotoBootDeviceTrackedConfig,
        ),

        SettingsParamType.Switch(
            stringResource(R.string.Notify_in_Telegram),
            R.drawable.ic_telegram,
            config.value.notifyInTelegram,
            isEnable = isTelegramConfigSetup.value,
            isVisible = config.value.isTracked,
            onStateChanged = settingsViewModel::updateNotifyInTelegramBootDeviceTrackedConfig
        ),

        SettingsParamType.Switch(
            stringResource(R.string.Attach_photo_to_message),
            R.drawable.ic_telegram,
            config.value.joinPhotoToTelegramNotify,
            isVisible = config.value.isTracked && config.value.makePhoto && config.value.notifyInTelegram,
            onStateChanged = settingsViewModel::updateJoinPhotoToTelegramNotifyBootDeviceTrackedConfig
        ),
    )
}

@SuppressLint("ResourceType")
@Composable
internal fun getSecureParams(
    settingsViewModel: SettingsViewModel,
    navigator: Navigator,
): List<SettingsParamType> {
    val isAppPasswordSetup = settingsViewModel.isAppPasswordSetup()
        .collectAsState(settingsViewModel.cashedIsAppPasswordSetup)

    val fingerPrintAuthorizationState = settingsViewModel.getFingerPrintAuthorizationState()
        .collectAsState(initial = false)
    SideEffect {
        settingsViewModel.cashedIsAppPasswordSetup = isAppPasswordSetup.value
    }
    return listOf(
        SettingsParamType.Switch(
            stringResource(R.string.Set_password_when_logging_into_app),
            R.drawable.ic_password,
            isSwitched = isAppPasswordSetup.value,
        ) {
            if (isAppPasswordSetup.value) {
                navigator.toSetupAppPasswordScreen(
                    Navigator.Companion.SetupAppPasswordScreenMode.RemovePassword
                )
            } else {
                navigator.toSetupAppPasswordScreen(
                    Navigator.Companion.SetupAppPasswordScreenMode.SetupPassword
                )
            }
        },

        SettingsParamType.Switch(
            stringResource(R.string.Login_with_finger_print),
            R.drawable.ic_fingerprint,
            fingerPrintAuthorizationState.value,
            isVisible = settingsViewModel.isFingerPrintScannerAvailable && isAppPasswordSetup.value,
            isEnable = settingsViewModel.isFingerPrintScannerAvailable && isAppPasswordSetup.value,
            onStateChanged = settingsViewModel::updateFingerPrintAuthorizationState
        )

    )
}

@SuppressLint("ResourceType")
@Composable
internal fun getLocalisationParams(settingsViewModel: SettingsViewModel): List<SettingsParamType> {
    return listOf(
        SettingsParamType.Button(
            text = stringResource(R.string.Select_language),
            icon = R.drawable.ic_language,
            onClick = settingsViewModel::showSelectLocaleDialog
        )
    )
}

@Composable
internal fun getStorageParams(settingsViewModel: SettingsViewModel): List<SettingsParamType> {

    val storageConfig = settingsViewModel.storageConfig.collectAsState(StorageConfig())

    val maxReportDropDownState = settingsViewModel.maxReportDropDownDialogState.remember()

    val maxTimeStoreReportsDropDownDialogState =
        settingsViewModel.maxTimeStoreReportsDropDownDialogState.remember()

    val maxReportsNameCountPair = listOf(
        0 to stringResource(R.string.Infinity_symbol),
        100 to 100.toString(),
        200 to 200.toString(),
        250 to 250.toString(),
        500 to 500.toString(),
        750 to 750.toString(),
        1000 to 1000.toString()
    )

    val maxTimeStoreReportsNameTimePair = listOf(
        0L to stringResource(R.string.Infinity_symbol),
        86_400_000L to stringResource(R.string.One_day),
        259_200_000L to stringResource(R.string.three_days),
        604_800_000L to stringResource(R.string.One_week),
        1_209_600_000L to stringResource(R.string.Two_weeks),
        1_814_400_000L to stringResource(R.string.Three_weeks),
        2_678_400_000L to stringResource(R.string.one_month)
    )

    val maxReportDropDownItem = maxReportsNameCountPair.map {
        SettingsParamType.DropDown.DropDownItem(it.second) {
            settingsViewModel.updateMaxReportCountStorageConfig(it.first)
        }
    }

    val maxTimeStoreReportsDropDownItem = maxTimeStoreReportsNameTimePair.map {
        SettingsParamType.DropDown.DropDownItem(it.second) {
            settingsViewModel.updateMaxReportStorageTimeStorageConfig(it.first)
        }
    }

    SideEffect {
        settingsViewModel.cashedStorageConfig = storageConfig.value
    }

    return listOf(
        SettingsParamType.DropDown(
            text = stringResource(R.string.Maximum_reports),
            icon = R.drawable.ic_time_limit,
            dropDownItems = maxReportDropDownItem,
            onShowDropDown = { maxReportDropDownState.value = true },
            onHideDropDown = { maxReportDropDownState.value = false },
            isDropDownVisible = maxReportDropDownState.value,
            showSelectedDropDownParam = maxReportsNameCountPair
                .find { it.first == storageConfig.value.maxReportCount }?.second
                ?: maxReportsNameCountPair[0].second,
            hideDropDownAfterSelect = true
        ),

        SettingsParamType.DropDown(
            text = stringResource(R.string.Report_storage_time),
            icon = R.drawable.ic_timer,
            dropDownItems = maxTimeStoreReportsDropDownItem,
            onShowDropDown = { maxTimeStoreReportsDropDownDialogState.value = true },
            onHideDropDown = { maxTimeStoreReportsDropDownDialogState.value = false },
            isDropDownVisible = maxTimeStoreReportsDropDownDialogState.value,
            showSelectedDropDownParam = maxTimeStoreReportsNameTimePair
                .find { it.first == storageConfig.value.maxReportStorageTime }?.second
                ?: maxTimeStoreReportsNameTimePair[0].second,
            hideDropDownAfterSelect = true
        )
    )
}

@SuppressLint("ResourceType")
@Composable
internal fun getWorkSuspendParams(settingsViewModel: SettingsViewModel) : List<SettingsParamType> {
    return listOf(
        SettingsParamType.Switch(
            text = stringResource(R.string.Suspending_by_time),
            icon = R.drawable.ic_time_limit,
            isSwitched = false,
            onStateChanged = {  }
        ),

        SettingsParamType.Button(
            text = stringResource(R.string.Suspend_setting),
            icon = R.drawable.ic_settings_suspend,
            onClick = { settingsViewModel.showSuspendParamsDialog() }
        )
    )
}

@SuppressLint("ResourceType")
@Composable
internal fun getBatteryOptimizationParams(
    settingsViewModel: SettingsViewModel,
): List<SettingsParamType> {
    val context = LocalContext.current

    return listOf(
        SettingsParamType.Button(
            text = stringResource(R.string.Disable_battery_optimization),
            icon = R.drawable.ic_battery,
            onClick = settingsViewModel::requestIgnoreBatteryOptimisation
        ),

        SettingsParamType.Button(
            text = stringResource(R.string.Open_dontkillmyapp),
            icon = R.drawable.ic_battery,
            onClick = { settingsViewModel.openDontKillMyAppWebSite(context) }
        )
    )
}

@Composable
fun operationLimitTimeNamePair(): List<Pair<Int, String>> {
    return listOf(
        0 to stringResource(R.string.No),
        60_000 to stringResource(R.string.one_minute),
        300_000 to stringResource(R.string.five_minute),
        600_000 to stringResource(R.string.ten_minutes),
        1_800_000 to stringResource(R.string.Thirty_minutes),
        3_600_000 to stringResource(R.string.sixty_minutes)
    )
}

@Composable
internal fun operationLimitDropDownItems(
    onChangeTime: (Int) -> Unit,
): List<SettingsParamType.DropDown.DropDownItem> {

    return operationLimitTimeNamePair().map {
        SettingsParamType.DropDown.DropDownItem(
            text = it.second,
            onClick = { onChangeTime(it.first) }
        )
    }
}
