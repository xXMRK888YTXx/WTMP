package com.xxmrk888ytxx.settingsscreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coredeps.Const.DEVELOPER_EMAIL
import com.xxmrk888ytxx.coredeps.Const.DONTKILLMYAPP_URL
import com.xxmrk888ytxx.coredeps.Const.PRIVACY_POLICY_URL
import com.xxmrk888ytxx.coredeps.Const.SOURCE_CODE_REPOSITORY_URL
import com.xxmrk888ytxx.coredeps.Const.TERMS_URL
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
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.StorageConfig.StorageConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.StorageConfig.StorageConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig.TelegramConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.WorkDayConfig.WorkTimeConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.WorkDayConfig.WorkTimeConfigProvider
import com.xxmrk888ytxx.coredeps.models.*
import com.xxmrk888ytxx.coredeps.sendCreateEmailIntent
import com.xxmrk888ytxx.coredeps.sendOpenWebSiteIntent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private val toastManager: ToastManager,
    private val storageConfigProvider: StorageConfigProvider,
    private val storageConfigChanger: StorageConfigChanger,
    private val workTimeConfigChanger: WorkTimeConfigChanger,
    private val workTimeConfigProvider: WorkTimeConfigProvider
) : ViewModel() {

    @SuppressLint("ResourceType")
    @MustBeLocalization
    internal fun sendIntentToWriteDeveloper(context: Context) {
        context.sendCreateEmailIntent(
            DEVELOPER_EMAIL,
            resourcesProvider.getString(R.string.Write_to_the_developer)
        )
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

    internal val bootDeviceConfig: Flow<BootDeviceTrackedConfig>
        get() = bootDeviceTrackedConfigProvider.config

    internal val currentSelectedLocale: MutableState<SupportedLanguage> =
        mutableStateOf(SupportedLanguage.System)

    private val _selectLocaleDialogShowState = mutableStateOf(false)

    internal val selectLocaleDialogShowState = _selectLocaleDialogShowState.toState()

    internal val operationLimitFailedUnlockDropDownState = mutableStateOf(false)

    internal val operationLimitSucceededUnlockDropDownState = mutableStateOf(false)

    internal val operationLimitAppOpenDropDownState = mutableStateOf(false)

    internal val maxReportDropDownDialogState = mutableStateOf(false)

    internal val maxTimeStoreReportsDropDownDialogState = mutableStateOf(false)

    private val _isSuspendParamsDialogVisible = mutableStateOf(false)

    internal val isSuspendParamsDialogVisible = _isSuspendParamsDialogVisible.toState()

    private val _selectedWeekDayInSuspendParamsDialog = MutableStateFlow(setOf<WeekDay>())

    internal val selectedWeekDayInSuspendParamsDialog = _selectedWeekDayInSuspendParamsDialog
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), setOf())

    private val _workTimeSpanInSetSuspendDialog = mutableStateOf(TimeSpan.NO_SETUP)

    internal val workTimeSpanInSetSuspendDialog = _workTimeSpanInSetSuspendDialog.toState()

    internal val workTimeConfig:Flow<WorkTimeConfig> = workTimeConfigProvider.workConfigFlow

    fun showSuspendParamsDialog() {
        viewModelScope.launch(Dispatchers.IO) {
            val config = workTimeConfigProvider.workConfigFlow.first()
            _selectedWeekDayInSuspendParamsDialog.emit(config.workWeekDays)

            withContext(Dispatchers.Main) {
                _workTimeSpanInSetSuspendDialog.value = config.workTimeSpan
                _isSuspendParamsDialogVisible.value = true
            }
        }

    }

    fun hideSuspendParamsDialog() {
        _isSuspendParamsDialogVisible.value = false
        _workTimeSpanInSetSuspendDialog.value = TimeSpan.NO_SETUP
    }

    internal fun setStartTimeSpanInSuspendDialog(time:Int) {
        if(!_isSuspendParamsDialogVisible.value) return

        _workTimeSpanInSetSuspendDialog.value = _workTimeSpanInSetSuspendDialog.value.copy(
            start = time.toLong()
        )
    }

    internal fun setEndTimeSpanInSuspendDialog(time:Int) {
        if(!_isSuspendParamsDialogVisible.value) return

        _workTimeSpanInSetSuspendDialog.value = _workTimeSpanInSetSuspendDialog.value.copy(
            end = time.toLong()
        )
    }

    internal fun resetTimeSpanInSuspendDialog() {
        _workTimeSpanInSetSuspendDialog.value = TimeSpan.NO_SETUP
    }

    @SuppressLint("ResourceType")
    private fun isInputDataInSuspendDialogValid(): Boolean {
        if(_selectedWeekDayInSuspendParamsDialog.value.isEmpty()) {
            toastManager.showToast(R.string.Choose_at_least_one_working_day)
            return false
        }

        if(!_workTimeSpanInSetSuspendDialog.value.isValid) {
            toastManager.showToast(R.string.Incorrect_time_entered)
            return false
        }

        return true
    }

    internal fun saveChangesInTimeSpanInSuspendDialog() {
        if(!isInputDataInSuspendDialogValid()) return

        val newWorkTimeSpan = _workTimeSpanInSetSuspendDialog.value
        val newWorkWeekDays = _selectedWeekDayInSuspendParamsDialog.value
        hideSuspendParamsDialog()
        viewModelScope.launch(Dispatchers.IO) {
            workTimeConfigChanger.updateWorkTimeSpan(newWorkTimeSpan)
            workTimeConfigChanger.updateWorkWeekDays(newWorkWeekDays)
        }
    }

    internal fun updateIsLimitTimeEnabled(newState:Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            workTimeConfigChanger.updateIsLimitTimeEnabled(newState)
        }
    }

    internal fun updateSelectedWeekDayInSuspendParamsDialog(onChange: (Set<WeekDay>) -> Set<WeekDay>) {
        viewModelScope.launch(Dispatchers.Default) {
            val current = selectedWeekDayInSuspendParamsDialog.value
            _selectedWeekDayInSuspendParamsDialog.emit(onChange(current))
        }
    }

    internal fun updateIsTrackedFailedUnlockTrackedConfig(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            failedUnlockTrackedConfigChanger.updateIsTracked(state)
        }
    }

    internal fun updateTimeOperationLimitFailedUnlockTrackedConfig(newTime: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            failedUnlockTrackedConfigChanger.updateTimeOperationLimit(newTime)
        }
    }

    internal fun updateCountFailedUnlockToTrigger(newCount: Int) {
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

    internal fun updateTimeOperationLimitSucceededUnlockTrackedConfig(newTime: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            succeededUnlockTrackedConfigChanger.updateTimeOperationLimit(newTime)
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

    internal fun updateTimeOperationLimitAppOpenConfig(newTime: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            appOpenConfigChanger.updateTimeOperationLimit(newTime)
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

    internal fun updateMaxReportCountStorageConfig(newValue: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            storageConfigChanger.updateMaxReportCount(newValue)
        }
    }

    internal fun updateMaxReportStorageTimeStorageConfig(newValue: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            storageConfigChanger.updateMaxReportStorageTime(newValue)
        }
    }

    internal val storageConfig = storageConfigProvider.storageConfig

    internal fun isAppPasswordSetup(): Flow<Boolean> = appPasswordProvider.isPasswordSetupFlow()

    internal fun getFingerPrintAuthorizationState() =
        appPasswordProvider.isFingerPrintAuthorizationEnabled()

    internal fun updateFingerPrintAuthorizationState(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            appPasswordChanger.updateFingerPrintAuthorizationState(state)
        }
    }

    internal fun removeApp() {
        removeAppManager.requestRemoveApp()
    }

    internal var cashedFailedUnlockTrackedConfig: FailedUnlockTrackedConfig =
        FailedUnlockTrackedConfig(
            isTracked = false,
            timeOperationLimit = 0,
            countFailedUnlockToTrigger = 1,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false
        )

    internal var cashedSucceededUnlockTrackedConfig: SucceededUnlockTrackedConfig =
        SucceededUnlockTrackedConfig(
            isTracked = false,
            timeOperationLimit = 0,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false
        )

    internal var cashedAppOpenConfig: AppOpenConfig = AppOpenConfig(
        isTracked = false,
        timeOperationLimit = 0,
        makePhoto = false,
        notifyInTelegram = false,
        joinPhotoToTelegramNotify = false
    )

    internal var cashedBootDeviceConfig: BootDeviceTrackedConfig = BootDeviceTrackedConfig(
        isTracked = false, makePhoto = false,
        notifyInTelegram = false, joinPhotoToTelegramNotify = false
    )

    internal var cashedWorkTimeConfig = WorkTimeConfig()

    internal var cashedIsAppPasswordSetup: Boolean = false

    internal var cashedStorageConfig = StorageConfig()

    internal val isFingerPrintScannerAvailable: Boolean by lazy {
        biometricAuthorizationManager.isFingerPrintScannerAvailable()
    }

    internal val numberInvalidAttemptsDropDownState = mutableStateOf(false)

    internal fun openPolicyPrivacy(context: Context) {
        context.sendOpenWebSiteIntent(PRIVACY_POLICY_URL)
    }

    internal fun openTerms(context: Context) {
        context.sendOpenWebSiteIntent(TERMS_URL)
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
        if (!permissionsManager.isIgnoreBatteryOptimizationEnable()) {
            permissionsManager.requestIgnoreBatteryOptimization()
        } else {
            toastManager.showToast(R.string.Ignore_battery_optimization_already_enabled)
        }
    }

    internal fun openDontKillMyAppWebSite(context: Context) {
        context.sendOpenWebSiteIntent(DONTKILLMYAPP_URL)
    }

    internal fun openSourceCodePage(context: Context) {
        context.sendOpenWebSiteIntent(SOURCE_CODE_REPOSITORY_URL)
    }

}