package com.xxmrk888ytxx.observer.AppOpenTimeOperationLimitManager

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.WorkDayConfig.WorkTimeConfigProvider
import com.xxmrk888ytxx.coredeps.models.TimeSpan
import com.xxmrk888ytxx.coredeps.models.WeekDay
import com.xxmrk888ytxx.coredeps.models.WorkTimeConfig
import com.xxmrk888ytxx.observer.domain.UseCase.IsNowWorkTimeCheckUseCase.IsNowWorkTimeCheckUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.LocalTime

class IsNowWorkTimeCheckUseCaseImplTest {

    private val appStateProvider = mockk<AppStateProvider>(relaxed = true)

    private val workTimeConfigProvider = mockk<WorkTimeConfigProvider>(relaxed = true)

    private val useCase = spyk(IsNowWorkTimeCheckUseCaseImpl(appStateProvider, workTimeConfigProvider))

    @Before
    fun init() {
        every { appStateProvider.isAppEnable } returns flowOf(true)
    }

    @Test
    fun callUseCaseIfAppStateIsFalseExpectReturnsFalse() = runBlocking {
        every { appStateProvider.isAppEnable } returns flowOf(false)
        every { workTimeConfigProvider.workConfigFlow } returns flowOf(WorkTimeConfig())

        Assert.assertEquals(false,useCase.execute())
    }

    @Test
    fun callUseCaseIfIsLimitTimeEnabledFalseExpectReturnTrue() = runBlocking {
        every { workTimeConfigProvider.workConfigFlow } returns flowOf(WorkTimeConfig())

        Assert.assertEquals(true,useCase.execute())
    }

    @Test
    fun checkUseCaseWithDifarensWeekDayExpectAllAlsoMondayAndSandayReturnsFalse() = runBlocking {
        every { workTimeConfigProvider.workConfigFlow } returns flowOf(WorkTimeConfig(
            workWeekDays = setOf(WeekDay.Monday,WeekDay.Sunday),
            isLimitTimeEnabled = true
        ))

        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now().dayOfWeek.value } returns WeekDay.Monday.number + 1

        Assert.assertEquals(true,useCase.execute())

        every { LocalDateTime.now().dayOfWeek.value } returns WeekDay.Sunday.number + 1

        Assert.assertEquals(true,useCase.execute())

        every { LocalDateTime.now().dayOfWeek.value } returns WeekDay.Wednesday.number + 1

        Assert.assertEquals(false,useCase.execute())
    }

    @Test
    fun checkIfTimeSpanNotSetupExpectTrue() = runBlocking {
        every { workTimeConfigProvider.workConfigFlow } returns flowOf(WorkTimeConfig(
            isLimitTimeEnabled = true,
        ))

        Assert.assertEquals(true,useCase.execute())
    }

    @Test
    fun callIfTimeInTimeSpanAndNotExpectFirstAttemptReturnTrueSecondFalse() = runBlocking {
        every { workTimeConfigProvider.workConfigFlow } returns flowOf(WorkTimeConfig(
            isLimitTimeEnabled = true,
            workTimeSpan = TimeSpan(0,20)
        ))
        mockkStatic(LocalTime::class)
        every { LocalTime.now().toSecondOfDay() } returns 8

        Assert.assertEquals(true,useCase.execute())

        every { LocalTime.now().toSecondOfDay() } returns 21

        Assert.assertEquals(false,useCase.execute())
    }
}