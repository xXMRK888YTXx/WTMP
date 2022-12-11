package com.xxmrk888ytxx.telegramsetupscreen

import android.annotation.SuppressLint
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.androidextension.LogcatExtension.logcatMessageD
import com.xxmrk888ytxx.coredeps.Exceptions.TelegramCancelMessage
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepositoryFactory
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ResourcesProvider
import com.xxmrk888ytxx.coredeps.launchAndCancelChildren
import com.xxmrk888ytxx.telegramsetupscreen.models.ScreenState
import kotlinx.coroutines.*
import toState
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TelegramViewModel @Inject constructor(
    private val telegramRepositoryFactory: TelegramRepositoryFactory,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    private var isError = 2

    internal val snackState = SnackbarHostState()

    private val snackBarScope = SnackBarScope()

    internal val userIdText = mutableStateOf("")

    internal val botKeyText = mutableStateOf("")

    private val _screenState:MutableState<ScreenState> =
        mutableStateOf(ScreenState.ChangeTelegramConfigState)

    internal val screenState = _screenState.toState()

    private val _isTelegramRequestProcessNow = mutableStateOf(false)

    internal val isTelegramRequestProcessNow = _isTelegramRequestProcessNow.toState()

    fun saveTelegramConfig() {
        if(!isInputtedConfigValid()) {
            snackBarScope.launchAndCancelChildren {
                snackState.showSnackbar("Заполните всё поля")
            }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _isTelegramRequestProcessNow.value = true
            if(isTelegramConfigValid()) {
                //TODO()
                _screenState.value = ScreenState.ConfigSavedState
            }
            else {
                _screenState.value = ScreenState.ChangeTelegramConfigState
            }
            _isTelegramRequestProcessNow.value = false
        }
    }

    fun isInputtedConfigValid() : Boolean {
        return userIdText.value.isNotEmpty()&&botKeyText.value.isNotEmpty()
    }

    fun checkTelegramConfig() {
        viewModelScope.launch(Dispatchers.IO) {
            _isTelegramRequestProcessNow.value = true

            isTelegramConfigValid(true)

            _isTelegramRequestProcessNow.value = false
        }
    }

    fun toChangeTelegramConfigState() {
        _isTelegramRequestProcessNow.value = false
        _screenState.value = ScreenState.ChangeTelegramConfigState
    }

    fun toConfigSavedState() {
        _screenState.value = ScreenState.ConfigSavedState
        _isTelegramRequestProcessNow.value = false
    }

    @SuppressLint("ResourceType")
    private suspend fun isTelegramConfigValid(isTestConfigRequest:Boolean = false) : Boolean {
           return try {
               delay(2000)
                when(isError) {
                    2 -> {
                        isError--
                        throw TelegramCancelMessage()
                    }
                    1 -> {
                        isError--
                        throw UnknownHostException()
                    }
                    0 -> {
                        isError--
                        throw Exception()
                    }
                    -1 -> {
                        snackBarScope.launchAndCancelChildren {
                            if(isTestConfigRequest) {
                                snackState.showSnackbar(
                                    resourcesProvider.getString(R.string.Telegram_config_valid_message)
                                )
                            }else {
                                snackState.showSnackbar(
                                    resourcesProvider.getString(R.string.Save_telegram_config_message)
                                )
                            }

                        }
                    }
                }
               true
            }

            catch (e: TelegramCancelMessage) {

                logcatMessageD(e.message.toString())

                snackBarScope.launchAndCancelChildren {
                    snackState.showSnackbar(
                        resourcesProvider.getString(R.string.Telegram_cancel_message)
                    )
                }

                false
            }

            catch (e: UnknownHostException) {

                logcatMessageD(e.message.toString())

                snackBarScope.launchAndCancelChildren {
                    snackState.showSnackbar(
                        resourcesProvider.getString(R.string.No_connection_message)
                    )
                }

                false
            }

            catch (e:Exception) {
                logcatMessageD(e.message.toString())

                snackBarScope.launchAndCancelChildren {
                    snackState.showSnackbar(
                        resourcesProvider.getString(R.string.unknown_error_message)
                    )
                }

                false
            }
    }

    override fun onCleared() {
        super.onCleared()
        snackBarScope.cancel()
    }

    private class SnackBarScope : CoroutineScope {
        override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Default
    }
}