package com.xxmrk888ytxx.mainscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import kotlinx.coroutines.flow.Flow
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

    internal val dayEventList:Flow<List<DeviceEvent>> =
        deviceEventRepository.getEventInTimeSpan(startDayTime,endDayTime)
}