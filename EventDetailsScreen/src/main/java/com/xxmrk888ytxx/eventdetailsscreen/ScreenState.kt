package com.xxmrk888ytxx.eventdetailsscreen

import com.xxmrk888ytxx.coredeps.models.DeviceEvent

internal sealed class ScreenState {
    object Loading : ScreenState()

    data class ShowEvent(val event: DeviceEvent) : ScreenState()


}