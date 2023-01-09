package com.xxmrk888ytxx.setupapppasswordscreen

import SharedInterfaces.Navigator
import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ResourcesProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ToastManager
import com.xxmrk888ytxx.enterpasswordscreen.EnterPasswordScreenCallBack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import theme.errorColor
import theme.primaryFontColor
import javax.inject.Inject

class SetupAppPasswordViewModel @Inject constructor(
    private val appPasswordChanger: AppPasswordChanger,
    private val resourcesProvider: ResourcesProvider,
    private val toastManager: ToastManager,
) : ViewModel() {

    private val _screenMode:MutableState<ScreenMode> = mutableStateOf(ScreenMode.None)

    internal var navigator:Navigator? = null

    internal val inputPassword = mutableStateOf("")

    internal val callBack = object : EnterPasswordScreenCallBack {

        @SuppressLint("ResourceType")
        override fun onInputNumber(number: Int) {
            if(inputPassword.value.length == passwordSize) return
            resetDefaultParams()
            inputPassword.value += number.toString()

            if(inputPassword.value.length == passwordSize) {
                when(_screenMode.value) {
                    is ScreenMode.SetupPassword -> {
                        handleSetupPasswordState(_screenMode.value as ScreenMode.SetupPassword)
                    }

                    is ScreenMode.RemovePassword -> {
                        handleRemovePasswordState()
                    }

                    is ScreenMode.None -> {}
                }
            }
        }

        override fun onClearNumber() {
            inputPassword.value = inputPassword.value.dropLast(1)
        }

        override fun onClearAll() {
            inputPassword.value = ""
        }

        override fun onSendFingerPrintRequest() {}

        override val passwordSize: Int = 4

        override val enableFingerPrintAuthorization: Boolean = false

    }

    @SuppressLint("ResourceType")
    private fun handleRemovePasswordState() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                appPasswordChanger.removePassword(inputPassword.value)
                withContext(Dispatchers.Main) {
                    toastManager.showToast(resourcesProvider.getString(R.string.Password_removed))
                    navigator?.navigateUp()
                }
            }catch (e:Exception) {
                descriptionText.value = resourcesProvider.getString(R.string.Wrong_password)
                descriptionColor.value = errorColor
                emptyPasswordCircleColor.value = errorColor
                callBack.onClearAll()
            }
        }
    }

    internal fun setupScreenMode(mode:Int) {
        if(_screenMode.value !is ScreenMode.None) return

        _screenMode.value = when(mode) {
            Navigator.Companion.SetupAppPasswordScreenMode.SetupPassword.modeNum
            -> ScreenMode.SetupPassword()

            Navigator.Companion.SetupAppPasswordScreenMode.RemovePassword.modeNum
            -> ScreenMode.RemovePassword

            else -> throw IllegalArgumentException("Incorrect mode screen number.Sanded number = $mode")
        }
    }


    @SuppressLint("ResourceType")
    internal val descriptionText = mutableStateOf(resourcesProvider.getString(R.string.Enter_password))

    internal val descriptionColor = mutableStateOf(primaryFontColor)

    internal val emptyPasswordCircleColor = mutableStateOf(primaryFontColor)



    @SuppressLint("ResourceType")
    private fun resetDefaultParams() {
        descriptionColor.value = primaryFontColor
        emptyPasswordCircleColor.value = primaryFontColor
        when(_screenMode.value) {
            is ScreenMode.SetupPassword -> {
                if((_screenMode.value as ScreenMode.SetupPassword).inputtedFirstPassword == null) {
                    descriptionText.value = resourcesProvider.getString(R.string.Enter_password)
                } else {
                    descriptionText.value = resourcesProvider.getString(R.string.Repeat_one_more)
                }

            }
            else -> {
                descriptionText.value = resourcesProvider.getString(R.string.Enter_password)
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun handleSetupPasswordState(state:ScreenMode.SetupPassword) {
        if(state.inputtedFirstPassword == null) {
            val inputtedPassword = inputPassword.value
            callBack.onClearAll()
            descriptionText.value = resourcesProvider.getString(R.string.Repeat_one_more)
            _screenMode.value = ScreenMode.SetupPassword(inputtedPassword)
        } else {
            if(state.inputtedFirstPassword == inputPassword.value) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        appPasswordChanger.setupAppPassword(inputPassword.value)
                        withContext(Dispatchers.Main) {
                            toastManager.showToast(R.string.Password_is_setup)
                        }
                    }catch (e:Exception) {
                        withContext(Dispatchers.Main) {
                            toastManager.showToast(R.string.Password_setup_error)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        navigator?.navigateUp()
                    }
                }
            } else {
                callBack.onClearAll()
                descriptionText.value = resourcesProvider.getString(R.string.Passwords_not_equal)
                descriptionColor.value = errorColor
                emptyPasswordCircleColor.value = errorColor
            }
        }
    }


}