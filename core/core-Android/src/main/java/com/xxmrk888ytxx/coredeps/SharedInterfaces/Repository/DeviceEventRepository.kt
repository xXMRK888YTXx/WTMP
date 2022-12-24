package com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository

import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import kotlinx.coroutines.flow.Flow

interface DeviceEventRepository {

    fun getAllEvents() : Flow<List<DeviceEvent>>

    fun getEventInTimeSpan(start:Long,end:Long) : Flow<List<DeviceEvent>>

    suspend fun addEvent(deviceEvent: DeviceEvent) : Int

    suspend fun removeEvent(eventId:Int)
}