package com.xxmrk888ytxx.coredeps.models

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.PersistentSet

data class WorkTimeConfig(
    val isLimitTimeEnabled:Boolean = false,
    val workTimeSpan: TimeSpan = TimeSpan.NO_SETUP,
    val workWeekDays: PersistentSet<WeekDay> = WeekDay.weekDaySet
)
