@file:Suppress("UNCHECKED_CAST")

package com.xxmrk888ytxx.observer.HandleEventUseCase

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig.TelegramConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.ImageRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.SingleWorkWorkerManager
import com.xxmrk888ytxx.coredeps.models.TelegramConfig
import com.xxmrk888ytxx.observer.domain.UseCase.HandleEventUseCase.HandleEventUseCase
import com.xxmrk888ytxx.observer.domain.UseCase.HandleEventUseCase.HandleEventUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HandleEventUseCaseTest {

    private val singleWorkWorkerManager:SingleWorkWorkerManager = mockk(relaxed = true)

    private val telegramConfigProvider:TelegramConfigProvider = mockk(relaxed = true)

    private val imageRepository:ImageRepository = mockk(relaxed = true)

    private val handleEventUseCase: HandleEventUseCase = HandleEventUseCaseImpl(
        singleWorkWorkerManager, telegramConfigProvider, imageRepository
    )

    private val workManagerForMultiRequest = mockk<SingleWorkWorkerManager>(relaxed = true)

    @Before
    fun init() {
        coEvery { singleWorkWorkerManager.createMultiRequest(any()) } coAnswers {
            (this.args[0] as suspend SingleWorkWorkerManager.() -> Unit).invoke(workManagerForMultiRequest)
        }
        val telegramConfig = MutableStateFlow(TelegramConfig(1252,"ew"))
        coEvery { telegramConfigProvider.telegramConfig } returns telegramConfig
    }

    @Test
    fun sendParamsInHandlerExpectCreateWorkersWithParams() = runBlocking {
        val eventId = 1
        val testTest = "gfh"

        handleEventUseCase.execute(
            eventId,
            true,true,true,""
        )

        verify(exactly = 1) { workManagerForMultiRequest.createImageWorker(any()) }
        coVerify(exactly = 1) { workManagerForMultiRequest
            .sendPhotoTelegram(any(),any(),any(),any()) }
        verify(exactly = 0) { workManagerForMultiRequest.sendMessageTelegram(any(),any(),any()) }
    }

    @Test
    fun SendMackPhotoParamTrueExpectThisWorkerEnabled() = runBlocking {
        val eventId = 1
        val testTest = "gfh"

        handleEventUseCase.execute(
            eventId,
            false,true,false,""
        )

        verify(exactly = 1) { workManagerForMultiRequest.createImageWorker(any()) }
    }

    @Test
    fun SendSendTelegramMessageParamTrueExpectThisWorkerEnabled() = runBlocking {
        val eventId = 1
        val testTest = "gfh"

        handleEventUseCase.execute(
            eventId,
            true,false,false,""
        )

        verify(exactly = 1) { workManagerForMultiRequest.sendMessageTelegram(any(),any(),any()) }
    }

    @Test
    fun SendJoinPhotoToTelegramNotifyParamTrueExpectThisWorkerEnabled() = runBlocking {
        val eventId = 1
        val testTest = "gfh"

        handleEventUseCase.execute(
            eventId,
            false,true,true,""
        )

        verify(exactly = 1) { workManagerForMultiRequest.sendPhotoTelegram(any(),any(),any()) }
    }
}