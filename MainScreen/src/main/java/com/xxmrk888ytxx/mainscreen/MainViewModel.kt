package com.xxmrk888ytxx.mainscreen

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.xxmrk888ytxx.adutils.AdStateManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.*
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.mainscreen.models.RequestedPermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import toState
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val packageInfoProvider: PackageInfoProvider,
    private val deviceEventRepository: DeviceEventRepository,
    private val appStateProvider: AppStateProvider,
    private val permissionsManager: PermissionsManager,
    private val appStateChanger: AppStateChanger,
    private val resourcesProvider: ResourcesProvider,
    private val adStateManager: AdStateManager
) : ViewModel(),ActivityLifecycleCallback {

    private var activityLifecycleRegister:ActivityLifecycleRegister? = null

    @SuppressLint("StaticFieldLeak")
    private var activity:Activity? = null

    private val cameraPermissionState = MutableStateFlow(false)

    private val adminPermissionState = MutableStateFlow(false)

    private val accessibilityPermissionsState = MutableStateFlow(false)

    private val notificationPermissionState = MutableStateFlow(false)

    internal val appState = appStateProvider.isAppEnable

    private val _isRemoveDialogShow:MutableState<Pair<Boolean,Int>> = mutableStateOf(Pair(false,0))

    internal val isRemoveDialogShow = _isRemoveDialogShow.toState()

    internal val isAccessibilityPermissionsDialogShow = mutableStateOf(false)

    private val _isShowRequestPermissionDialog = mutableStateOf(false)

    internal val isShowRequestPermissionDialog = _isShowRequestPermissionDialog.toState()

    /**
     * [Ru]
     * При открытом [RequestPermissionDialog], выходе и возрасте в приложении
     * будут проверяться наличие разрешений необходимых для работы приложении
     */
    /**
     * [En]
     * With [RequestPermissionDialog] open, exit and age in app
     * the presence of permissions necessary for the application to work will be checked
     */
    override fun onResume() {
        if(_isShowRequestPermissionDialog.value)
            checkPermission()
    }

    override fun onRegister(activity: Activity) {
        super.onRegister(activity)
        this.activity = activity
    }

    override fun onCreate(activity: Activity) {
        super.onCreate(activity)
        this.activity = activity
    }

    override fun onDestroy() {
        super.onDestroy()
        this.activity = null
    }

    internal fun showRemoveEventDialog(eventId:Int) {
        _isRemoveDialogShow.value = Pair(true,eventId)
    }

    internal fun hideRemoveEventDialog() {
        _isRemoveDialogShow.value = Pair(false,0)
    }

    internal fun removeEvent(eventId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deviceEventRepository.removeEvent(eventId)
        }
    }


    private val startDayTime : Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.time = Date(System.currentTimeMillis())
            calendar.set(Calendar.HOUR_OF_DAY,0)
            calendar.set(Calendar.MINUTE,0)
            calendar.set(Calendar.SECOND,0)
            calendar.set(Calendar.MILLISECOND,0)
            return calendar.timeInMillis
        }

    private val endDayTime:Long
        get() = startDayTime + 86_400_000L

    private val savedAppImages = mutableMapOf<String,Bitmap?>()

    private val savedAppNames = mutableMapOf<String,String?>()

    internal val dayEventList:Flow<List<DeviceEvent>> =
        deviceEventRepository.getEventInTimeSpan(startDayTime,endDayTime).map { list ->
            val newList = list.map { event ->
                if(event is DeviceEvent.AppOpen)
                    provideAppInfoIntoEvent(event)
                else event
            }
            clearSavedRes()
            return@map newList
        }

    private suspend fun clearSavedRes() {
        savedAppImages.clear()
        savedAppImages.clear()
    }

    private suspend fun provideAppInfoIntoEvent(event: DeviceEvent.AppOpen) : DeviceEvent {
        val appName = viewModelScope.async(Dispatchers.Default) {
            if(event.packageName !in savedAppNames) {
                savedAppNames[event.packageName] = packageInfoProvider.getAppName(event.packageName)
            }

            return@async savedAppNames[event.packageName]
        }

        val icon = viewModelScope.async(Dispatchers.Default) {
            if(event.packageName !in savedAppImages) {
                savedAppImages[event.packageName] =
                    packageInfoProvider.getAppIcon(event.packageName)?.toBitmap()
            }

            return@async savedAppImages[event.packageName]
        }

        return event.copy(appName = appName.await(), icon = icon.await())
    }

    @OptIn(ExperimentalPermissionsApi::class)
    internal val requestedPermission : List<RequestedPermission>
        @SuppressLint("ResourceType") @Composable get() {
            val cameraState = rememberPermissionState(
                android.Manifest.permission.CAMERA
            )
            val notificationState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                rememberPermissionState(
                    android.Manifest.permission.POST_NOTIFICATIONS
                )
            } else {
                null
            }
            val requestedPermissionList = mutableListOf (
                RequestedPermission(
                    resourcesProvider.getString(R.string.Permission_access_camera),
                    cameraPermissionState) {
                    permissionsManager.requestRuntimePermission(cameraState)
                },
                RequestedPermission(
                    resourcesProvider.getString(R.string.Access_accessibility_services),
                    accessibilityPermissionsState,
                    onRequest = { isAccessibilityPermissionsDialogShow.value = true }
                ),
                RequestedPermission(
                    resourcesProvider.getString(R.string.Access_device_administrators),
                    adminPermissionState,
                ) {
                    val activity = activity ?: return@RequestedPermission
                    permissionsManager.requestAdminPermissions(activity)
                },
            )
            if(notificationState != null) {
                requestedPermissionList.add(RequestedPermission(
                    resourcesProvider.getString(R.string.Permission_send_notifications),
                    notificationPermissionState,
                ) {
                    permissionsManager.requestRuntimePermission(notificationState)
                })
            }
            return requestedPermissionList
        }

    internal fun showRequestPermissionDialog() {
        if(checkPermission()) {
            viewModelScope.launch(Dispatchers.IO) {
                appStateChanger.updateAppState(true)
            }
            return
        }
        _isShowRequestPermissionDialog.value = true
    }

    internal fun hideRequestPermissionDialog() {
        _isShowRequestPermissionDialog.value = false
    }

    private fun checkPermission() : Boolean {
        val camera = permissionsManager.isCameraPermissionGranted()
        val accessibility = permissionsManager.isAccessibilityPermissionGranted()
        val admin = permissionsManager.isAdminPermissionGranted()
        val notification = permissionsManager.isNotificationPermissionGranted()

        viewModelScope.launch(Dispatchers.Default) {
            cameraPermissionState.emit(camera)
            accessibilityPermissionsState.emit(accessibility)
            adminPermissionState.emit(admin)
            notificationPermissionState.emit(notification)
        }


        return camera&&accessibility&&admin&&notification
    }

    internal fun disableApp() {
        viewModelScope.launch(Dispatchers.IO) {
            appStateChanger.updateAppState(false)
        }
    }

    fun registerInActivityLifecycle(activityLifecycleRegister: ActivityLifecycleRegister) {
        this.activityLifecycleRegister = activityLifecycleRegister
        activityLifecycleRegister.registerCallback(this)
    }

    internal val isShowAd = adStateManager.isNeedShowAd

    override fun onCleared() {
        activityLifecycleRegister?.unregisterCallback(this)
        activity = null
        activityLifecycleRegister = null
        super.onCleared()
    }

    fun requestAccessibilityPermission() {
        permissionsManager.requestAccessibilityPermissions()
        isAccessibilityPermissionsDialogShow.value = false
    }
}