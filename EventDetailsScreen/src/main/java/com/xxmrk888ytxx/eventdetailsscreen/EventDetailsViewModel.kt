package com.xxmrk888ytxx.eventdetailsscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.ImageRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import toState

class EventDetailsViewModel @AssistedInject constructor(
    @Assisted val eventId:Int,
    private val deviceEventRepository: DeviceEventRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) { loadInfo() }
    }

    private val _screenState:MutableState<ScreenState> = mutableStateOf(ScreenState.Loading)

    internal val screenState = _screenState.toState()

    private suspend fun loadInfo() {
        val eventDef =  viewModelScope.async(Dispatchers.IO) {
            return@async deviceEventRepository.getEvent(eventId).first()
        }
        val imageDef = viewModelScope.async(Dispatchers.IO) {
            return@async imageRepository.getEventBitmap(eventId)
        }
        val event = eventDef.await()
        val image = imageDef.await()

        withContext(Dispatchers.Main) {
            _screenState.value = ScreenState.ShowEvent(event,image)
        }

    }


    @AssistedFactory
    interface Factory {
        fun create(eventId: Int) : EventDetailsViewModel
    }
}