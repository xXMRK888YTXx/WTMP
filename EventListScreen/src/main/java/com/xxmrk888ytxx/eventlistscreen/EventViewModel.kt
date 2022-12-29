package com.xxmrk888ytxx.eventlistscreen

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.coredeps.toDateString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import toState
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val context:Context,
    private val packageInfoProvider: PackageInfoProvider,
    private val deviceEventRepository: DeviceEventRepository
): ViewModel() {

    private val savedAppImages = mutableMapOf<String, Bitmap?>()

    private val savedAppNames = mutableMapOf<String,String?>()

    private suspend fun clearSavedRes() {
        savedAppImages.clear()
        savedAppImages.clear()
    }

    val eventList : Flow<Map<String, List<DeviceEvent>>>
        get() = deviceEventRepository.getAllEvents().map { list ->
            val newList = list.map { event ->
                if(event is DeviceEvent.AppOpen)
                    provideAppInfoIntoEvent(event)
                else event
            }
            clearSavedRes()
            return@map newList
        }.map { eventList ->
            sortEventListByDate(eventList)
        }

    private suspend fun sortEventListByDate(
        eventList:List<DeviceEvent>
    ): Map<String, List<DeviceEvent>> {
        val map:MutableMap<String,List<DeviceEvent>> = mutableMapOf()
        eventList.forEach { event ->
            val date = event.time.toDateString(context)
            if(map[date] != null) {
                val newList = map[date]?.toMutableList() ?: mutableListOf()
                newList.add(event)
                map[date] = newList.sortedByDescending { it.time }
            }
            else {
                map[date] = listOf(event)
            }
        }
        return map
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

    private val _isRemoveDialogShow: MutableState<Pair<Boolean, Int>> = mutableStateOf(Pair(false,0))

    internal val isRemoveDialogShow = _isRemoveDialogShow.toState()

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

}