package com.xxmrk888ytxx.observer.domain.BootCallback

import android.annotation.SuppressLint
import com.xxmrk888ytxx.bootreceiver.BootCallback
import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.BootDeviceTrackedConfig.BootDeviceTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ResourcesProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.IsNowWorkTimeCheckUseCase
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.observer.R
import com.xxmrk888ytxx.observer.domain.UseCase.HandleEventUseCase.HandleEventUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class BootCallbackImpl @Inject constructor(
    private val bootDeviceTrackedConfigProvider: BootDeviceTrackedConfigProvider,
    private val appStateProvider: AppStateProvider,
    private val deviceEventRepository: DeviceEventRepository,
    private val handleEventUseCase: HandleEventUseCase,
    private val resourcesProvider: ResourcesProvider,
    private val isNowWorkTimeCheckUseCase: IsNowWorkTimeCheckUseCase
) : BootCallback {

    @SuppressLint("ResourceType")
    override fun onBootCompleted() {
        ApplicationScope.launch {
            val config = bootDeviceTrackedConfigProvider.config.first()

            if (!config.isTracked
                || !appStateProvider.isAppEnable.first() || !isNowWorkTimeCheckUseCase.execute()
            ) return@launch

            val eventId = deviceEventRepository.addEvent(
                DeviceEvent.DeviceLaunch(0, System.currentTimeMillis())
            )

            handleEventUseCase.execute(
                eventId = eventId,
                isSendTelegramMessage = config.notifyInTelegram,
                makePhoto = config.makePhoto,
                joinPhotoToTelegramNotify = config.joinPhotoToTelegramNotify,
                telegramMessageText = resourcesProvider.getString(R.string.Device_loaded)
            )
        }

    }

}