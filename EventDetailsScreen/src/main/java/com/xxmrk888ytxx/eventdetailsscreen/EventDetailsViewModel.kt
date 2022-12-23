package com.xxmrk888ytxx.eventdetailsscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import toState

class EventDetailsViewModel @AssistedInject constructor(
    @Assisted val eventId:Int,
) : ViewModel() {

    private val _screenState:MutableState<ScreenState> = mutableStateOf(ScreenState.ShowEvent(
        DeviceEvent.AppOpen(1,"Gachi","fdgd",null,System.currentTimeMillis())
    ))

    internal val screenState = _screenState.toState()

    @AssistedFactory
    interface Factory {
        fun create(eventId: Int) : EventDetailsViewModel
    }
}