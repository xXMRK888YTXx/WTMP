package com.xxmrk888ytxx.coredeps.models

data class WorkTimeConfig(
    val isLimitTimeEnabled:Boolean = false,
    val workTimeSpan: TimeSpan = TimeSpan.NO_SETUP,
    val workWeekDays: Set<WeekDay> = WeekDay.weekDaySet
)
