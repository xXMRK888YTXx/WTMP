package com.xxmrk888ytxx.eventlistscreen

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coredeps.DefaultPaginator
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ToastManager
import com.xxmrk888ytxx.coredeps.logcatMessageD
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.coredeps.toDateString
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import toState
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val context: Context,
    private val packageInfoProvider: PackageInfoProvider,
    private val deviceEventRepository: DeviceEventRepository,
) : ViewModel() {

    private val savedAppImages = mutableMapOf<String, Bitmap?>()

    private val savedAppNames = mutableMapOf<String, String?>()

    private suspend fun clearSavedRes() {
        savedAppImages.clear()
        savedAppImages.clear()
    }


    private val _sortedByDateEventList =
        MutableStateFlow<Map<String, List<DeviceEvent>>>(emptyMap())

    val sortedByDateEventList: Flow<Map<String, List<DeviceEvent>>> =
        _sortedByDateEventList.asStateFlow()

    private val _screenState = MutableStateFlow(ScreenState())

    internal val screenState = _screenState.asStateFlow()

    private val handler = Handler(Looper.getMainLooper())

    private val updatePagingDataScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val paginator:DefaultPaginator<Int,List<DeviceEvent>> = DefaultPaginator(
        initKey = 0,
        pageSize = 30,
        onLoadStateChanged = {
            handler.post { _screenState.value = _screenState.value.copy(isLoadNewData = it) }
        },
        onUpdateDataRequest = { page,pageSize:Int ->
            deviceEventRepository.getPagingData(page,pageSize)
        },
        onNextKey = { currentKey, newData ->
            if(newData.isEmpty()) {
                return@DefaultPaginator null
            }
            else currentKey + 1
        },
        onError = {
              logcatMessageD(it.stackTraceToString())
        },
        onNewPageDataLoaded = { list ->
            //drop not unique
            val uniqueEvents = list.filter { event ->
                _sortedByDateEventList.value.values.all { 
                    it.all { it.eventId != event.eventId }
                }
            }
            
            val listWithAppData = uniqueEvents.map { event ->
                if(event is DeviceEvent.AppOpen)
                    provideAppInfoIntoEvent(event)
                else event
            }

            clearSavedRes()

            addNewData(listWithAppData)
        },
        onKeyReset = {

            updatePagingDataScope.coroutineContext.cancelChildren()

            viewModelScope.launch(Dispatchers.IO) {
                _sortedByDateEventList.emit(emptyMap())
            }

            handler.post { _screenState.value = _screenState.value.copy(
                isAllPageLoaded = false,
                isLoadNewData = false
            ) }

            requestNewPageData()
        },

        onAllPageLoaded = {
            handler.post { _screenState.value = _screenState.value.copy(isAllPageLoaded = true) }
        }
    )
    
    private suspend fun addNewData(
        eventList: List<DeviceEvent>
    ) {
        val map: MutableMap<String, List<DeviceEvent>> = _sortedByDateEventList.value.toMutableMap()
        eventList.forEach { event ->
            val dataString = event.time.toDateString(context)
            if (map[dataString] != null) {
                val newList = map[dataString]?.toMutableList() ?: mutableListOf()
                newList.add(event)
                map[dataString] = newList.sortedByDescending { it.time }
            } else {
                map[dataString] = listOf(event)
            }
        }

        _sortedByDateEventList.emit(map)
    }

    private suspend fun provideAppInfoIntoEvent(event: DeviceEvent.AppOpen): DeviceEvent {
        val appName = viewModelScope.async(Dispatchers.Default) {
            if (event.packageName !in savedAppNames) {
                savedAppNames[event.packageName] = packageInfoProvider.getAppName(event.packageName)
            }

            return@async savedAppNames[event.packageName]
        }

        val icon = viewModelScope.async(Dispatchers.Default) {
            if (event.packageName !in savedAppImages) {
                savedAppImages[event.packageName] =
                    packageInfoProvider.getAppIcon(event.packageName)?.toBitmap()
            }

            return@async savedAppImages[event.packageName]
        }

        return event.copy(appName = appName.await(), icon = icon.await())
    }

    private val _isRemoveDialogShow: MutableState<Pair<Boolean, Int>> =
        mutableStateOf(Pair(false, 0))

    internal val isRemoveDialogShow = _isRemoveDialogShow.toState()

    internal fun showRemoveEventDialog(eventId: Int) {
        _isRemoveDialogShow.value = Pair(true, eventId)
    }

    internal fun hideRemoveEventDialog() {
        _isRemoveDialogShow.value = Pair(false, 0)
    }

    internal fun removeEvent(eventId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentData = _sortedByDateEventList.value.toMutableMap()

            deviceEventRepository.removeEvent(eventId)

            currentData.forEach {
                if(it.value.any { event ->  event.eventId == eventId }) {
                    val currentList = it.value

                    currentData[it.key] = currentList.filter { event ->  event.eventId != eventId }

                    if(currentData[it.key]?.isEmpty() == true) {
                        currentData.remove(it.key)
                    }

                    _sortedByDateEventList.emit(currentData)

                    return@launch
                }
            }
        }
    }

    override fun onCleared() {
        updatePagingDataScope.cancel()
        super.onCleared()
    }

    fun requestNewPageData() {
        if(_screenState.value.isLoadNewData||_screenState.value.isAllPageLoaded) return
        updatePagingDataScope.launch(Dispatchers.IO) {
            paginator.load()
        }
    }

    init {
        updatePagingDataScope.launch(Dispatchers.IO) { paginator.load() }
    }

}