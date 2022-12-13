package com.xxmrk888ytxx.eventlistscreen

import android.content.Context
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.coredeps.toDateString
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val context:Context,
    private val packageInfoProvider: PackageInfoProvider
): ViewModel() {
    private val _eventList = listOf(
        DeviceEvent.AttemptUnlockDevice.Succeeded(166844,1608442945140),
        DeviceEvent.AttemptUnlockDevice.Succeeded(166814,1308442945140),
        DeviceEvent.AttemptUnlockDevice.Succeeded(166864,1508442945541),
        DeviceEvent.AttemptUnlockDevice.Succeeded(666814,1308442945140),
        DeviceEvent.AttemptUnlockDevice.Succeeded(866864,1508442945142),
        DeviceEvent.AttemptUnlockDevice.Failed(1,System.currentTimeMillis()),
        DeviceEvent.AttemptUnlockDevice.Failed(2,System.currentTimeMillis()),
        DeviceEvent.AttemptUnlockDevice.Succeeded(3,System.currentTimeMillis()),
        DeviceEvent.AttemptUnlockDevice.Succeeded(16684579,1668430176579),
        DeviceEvent.AttemptUnlockDevice.Succeeded(16684580,1668430179979),
        DeviceEvent.AttemptUnlockDevice.Succeeded(45,System.currentTimeMillis()+10000),
        DeviceEvent.AttemptUnlockDevice.Succeeded(77,System.currentTimeMillis()+10000),
        DeviceEvent.AttemptUnlockDevice.Succeeded(100,System.currentTimeMillis()+10000),
        DeviceEvent.AttemptUnlockDevice.Succeeded(34,System.currentTimeMillis()+10000),
        DeviceEvent.AttemptUnlockDevice.Succeeded(678,System.currentTimeMillis()+10000),
        DeviceEvent.AppOpen(
            5,"Brawl Stars", packageName = "e",null,System.currentTimeMillis(),
        ),
        DeviceEvent.AppOpen(
            6,"MineCraft", packageName = "e",null,System.currentTimeMillis()
        ),
        DeviceEvent.AppOpen(
            121131456,
            packageInfoProvider.getAppName("com.xxmrk888ytxx.privatenote"),
            packageName = "e",
            packageInfoProvider.getAppIcon("com.xxmrk888ytxx.privatenote")?.toBitmap(),
            1508442940000
        ),
    ).sortedBy { it.eventId }

    val eventList : Map<String,List<DeviceEvent>>
        get() {
            val map:MutableMap<String,List<DeviceEvent>> = mutableMapOf()
            _eventList.sortedByDescending { it.time }.forEach { event ->
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



}