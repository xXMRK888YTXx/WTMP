package com.xxmrk888ytxx.telegramsetupscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TelegramViewModel @Inject constructor() : ViewModel() {

    val isDataSave = mutableStateOf(false)

    private var isError = true

    fun saveTelegramConfig(
        onSucceededShackBar:(CoroutineScope) -> Unit,
        onErrorSnackBar:(CoroutineScope) -> Unit
    ) {
        if(isError) {
            SnackBarScope.coroutineContext.cancelChildren()
            onErrorSnackBar(SnackBarScope)
            isError = false
        } else {
            SnackBarScope.coroutineContext.cancelChildren()
            onSucceededShackBar(SnackBarScope)
            isDataSave.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        SnackBarScope.cancel()
    }

    private object SnackBarScope : CoroutineScope {
        override val coroutineContext: CoroutineContext = SupervisorJob()
    }
}