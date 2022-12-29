package com.xxmrk888ytxx.mainscreen

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val packageInfoProvider: PackageInfoProvider,
    private val deviceEventRepository: DeviceEventRepository
) : ViewModel() {
    val isEnable = mutableStateOf(true)


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
}