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
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TelegramConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TelegramConfigProvider
import com.xxmrk888ytxx.coredeps.launchAndCancelChildren
import com.xxmrk888ytxx.coredeps.models.TelegramConfig
import com.xxmrk888ytxx.telegramsetupscreen.models.ScreenState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import toState
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TelegramViewModel @Inject constructor(
    private val telegramRepositoryFactory: TelegramRepositoryFactory,
    private val resourcesProvider: ResourcesProvider,
    private val telegramConfigProvider: TelegramConfigProvider,
    private val telegramConfigChanger: TelegramConfigChanger,
) : ViewModel() {

    private val _screenState: MutableState<ScreenState> =
        mutableStateOf(ScreenState.LoadConfigState)

    /**
     * [Ru]
     * Проверка на сохранённые данные от бота
     */
    /**
     * [En]
     * Checking for saved data from the bot
     */
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val config = telegramConfigProvider.getTelegramConfig().first()
            withContext(Dispatchers.Main) {
                _screenState.value = if(config == null)
                    ScreenState.ChangeTelegramConfigState else ScreenState.ConfigSavedState
            }
        }
    }

    internal val snackState = SnackbarHostState()

    private val snackBarScope = SnackBarScope()

    internal val userIdText = mutableStateOf("")

    internal val botKeyText = mutableStateOf("")

    internal val screenState = _screenState.toState()

    /**
     * [Ru]
     * Если данная переменная == true, то значит в данный момент выполнятеся
     * запрос к серверам Telegram, этот пораметр заменяет некоторые кнобки на
     * индикатор загрузки
     */
    /**
     * [En]
     * If this variable == true, then it means that the
     * request to Telegram servers, this parameter replaces some buttons with
     * loading indicator
     */
    private val _isTelegramRequestProcessNow = mutableStateOf(false)

    internal val isTelegramRequestProcessNow = _isTelegramRequestProcessNow.toState()

    /**
     * [Ru]
     * Функция проверки и сохранение данных от Telegram бота
     */
    /**
     * [En]
     * The function of checking and saving data from the Telegram bot
     */
    fun saveTelegramConfig() {
        if (!isInputtedConfigValid()) {
            snackBarScope.launchAndCancelChildren {
                snackState.showSnackbar("Заполните всё поля")
            }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _isTelegramRequestProcessNow.value = true
            val userId =  userIdText.value.toLong()
            val botKey =  botKeyText.value
            if (isTelegramConfigValid(userId, botKey)) {
                val telegramConfig = TelegramConfig(userId, botKey)
                telegramConfigChanger.updateTelegramConfig(telegramConfig)
                _screenState.value = ScreenState.ConfigSavedState
            } else {
                _screenState.value = ScreenState.ChangeTelegramConfigState
            }
            _isTelegramRequestProcessNow.value = false
        }
    }

    private fun isInputtedConfigValid(): Boolean {
        return userIdText.value.isNotEmpty() && botKeyText.value.isNotEmpty()
    }

    /**
     * [Ru]
     * Функция проверят валидность ранее сохран данных
     */
    /**
     * [En]
     * The function will check the validity of previously saved data
     */
    fun checkTelegramConfig() {
        viewModelScope.launch(Dispatchers.IO) {
            val telegramConfig = telegramConfigProvider.getTelegramConfig().first() ?: return@launch
            _isTelegramRequestProcessNow.value = true
            isTelegramConfigValid(telegramConfig.userId, telegramConfig.botKey, true)

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

    /**
     * [Ru]
     * Проверяет валидность введённых данных посредством проверки через сервера Telegram
     * По окончанию проверки покажет Snackbar о результате
     */
    /**
     * [En]
     * Checks the validity of the entered data by checking through the Telegram server
     * At the end of the test, the Snackbar will show the result
     */
    @SuppressLint("ResourceType")
    private suspend fun isTelegramConfigValid(
        userId: Long,
        botKey: String,
        isTestConfigRequest: Boolean = false,
    ): Boolean {
        return try {
            val telegramRepository = telegramRepositoryFactory.create(botKey, userId)

            telegramRepository.sendMessage("Это тестовое сообщение")
            snackBarScope.launchAndCancelChildren {
                if (isTestConfigRequest) {
                    snackState.showSnackbar(
                        resourcesProvider.getString(R.string.Telegram_config_valid_message)
                    )
                } else {
                    snackState.showSnackbar(
                        resourcesProvider.getString(R.string.Save_telegram_config_message)
                    )
                }
            }
            true
        } catch (e: TelegramCancelMessage) {

            logcatMessageD(e.message.toString())

            snackBarScope.launchAndCancelChildren {
                snackState.showSnackbar(
                    resourcesProvider.getString(R.string.Telegram_cancel_message)
                )
            }

            false
        } catch (e: UnknownHostException) {

            logcatMessageD(e.message.toString())

            snackBarScope.launchAndCancelChildren {
                snackState.showSnackbar(
                    resourcesProvider.getString(R.string.No_connection_message)
                )
            }

            false
        } catch (e: Exception) {
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

    /**
     * [Ru]
     * Специальный [CoroutineScope] для показа Snackbar
     */
    /**
     *[En]
     * Special [CoroutineScope] to show Snackbar
     */
    private class SnackBarScope : CoroutineScope {
        override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Default
    }
}