package com.xxmrk888ytxx.eventlistscreen

import android.content.Context
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.coredeps.toDateString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val context:Context,
    private val packageInfoProvider: PackageInfoProvider,
    private val deviceEventRepository: DeviceEventRepository
): ViewModel() {

    val eventList : Flow<Map<String, List<DeviceEvent>>>
        get() = deviceEventRepository.getAllEvents().map { eventList ->
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
            map
        }



}