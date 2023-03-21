package com.xxmrk888ytxx.observer.domain.UseCase.IsNowWorkTimeCheckUseCase

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.WorkDayConfig.WorkTimeConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.IsNowWorkTimeCheckUseCase
import com.xxmrk888ytxx.coredeps.models.TimeSpan
import com.xxmrk888ytxx.coredeps.models.TimeSpan.Companion.NO_SETUP
import com.xxmrk888ytxx.coredeps.models.WeekDay
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.inject.Inject

class IsNowWorkTimeCheckUseCaseImpl @Inject constructor(
    private val appStateProvider: AppStateProvider,
    private val workTimeConfigProvider: WorkTimeConfigProvider,
) : IsNowWorkTimeCheckUseCase {
    override suspend fun execute(): Boolean {
        val appState = appStateProvider.isAppEnable.first()
        val workTimeConfig = workTimeConfigProvider.workConfigFlow.first()

        if (!appState) return false
        if (!workTimeConfig.isLimitTimeEnabled) return true

        val currentWeekDay = WeekDay.fromInt(LocalDateTime.now().dayOfWeek.value - 1)

        if (!workTimeConfig.workWeekDays.contains(currentWeekDay)) return false

        val currentDaySecond = LocalTime.now().toSecondOfDay().toLong()

        return workTimeConfig.workTimeSpan.inTimeSpan(currentDaySecond)
                || workTimeConfig.workTimeSpan == NO_SETUP
    }

}