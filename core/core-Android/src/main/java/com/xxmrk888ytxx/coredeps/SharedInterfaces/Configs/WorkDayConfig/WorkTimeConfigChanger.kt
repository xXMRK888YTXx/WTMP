package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.WorkDayConfig

import com.xxmrk888ytxx.coredeps.models.TimeSpan
import com.xxmrk888ytxx.coredeps.models.WeekDay
import com.xxmrk888ytxx.coredeps.models.WorkTimeConfig

interface WorkTimeConfigChanger {

    suspend fun updateIsLimitTimeEnabled(state:Boolean)

    suspend fun updateWorkTimeSpan(workTimeSpan: TimeSpan)

    suspend fun updateWorkWeekDays(workDays:Set<WeekDay>)
}