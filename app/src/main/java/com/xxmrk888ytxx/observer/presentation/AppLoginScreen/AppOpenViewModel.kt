package com.xxmrk888ytxx.observer.presentation.AppLoginScreen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.xxmrk888ytxx.coredeps.SharedInterfaces.*
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordProvider
import com.xxmrk888ytxx.enterpasswordscreen.EnterPasswordScreenCallBack
import com.xxmrk888ytxx.observer.Screen
import com.xxmrk888ytxx.setupapppasswordscreen.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import theme.errorColor
import theme.primaryFontColor
import javax.inject.Inject


internal class AppOpenViewModel @Inject constructor(
    private val appPasswordProvider: AppPasswordProvider,
    private val biometricAuthorizationManager: BiometricAuthorizationManager,
    private val resourcesProvider: ResourcesProvider,
    private val toastManager: ToastManager
) : ViewModel(),ActivityLifecycleCallback {

    @SuppressLint("StaticFieldLeak")
    private var fragmentActivity:FragmentActivity? = null

    override fun onCreate(activity: Activity) {
        super.onCreate(activity)
        fragmentActivity = activity as? FragmentActivity
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentActivity = null
    }

    override fun onRegister(activity: Activity) {
        super.onRegister(activity)
        fragmentActivity = activity as? FragmentActivity

        if(isFingerPrintEnabled)
            callBack.onSendFingerPrintRequest()
    }

    private var isFingerPrintEnabled:Boolean = false

    init {
        viewModelScope.launch(Dispatchers.IO) {
            isFingerPrintEnabled = appPasswordProvider.isFingerPrintAuthorizationEnabled().first()
        }
    }

    var navController:NavController? = null

    private var activityLifecycleRegister:ActivityLifecycleRegister? = null

    val inputPassword = mutableStateOf("")

    @SuppressLint("ResourceType")
    internal val descriptionText = mutableStateOf(resourcesProvider.getString(R.string.Enter_password))

    internal val descriptionColor = mutableStateOf(primaryFontColor)

    internal val emptyPasswordCircleColor = mutableStateOf(primaryFontColor)

    val callBack by lazy {
        object : EnterPasswordScreenCallBack {
            @SuppressLint("ResourceType")
            override fun onInputNumber(number: Int) {
                if(inputPassword.value.length == passwordSize) return

                descriptionText.value = resourcesProvider.getString(R.string.Enter_password)
                descriptionColor.value = primaryFontColor
                emptyPasswordCircleColor.value = primaryFontColor

                inputPassword.value += number.toString()

                if(inputPassword.value.length == passwordSize) {
                    viewModelScope.launch(Dispatchers.IO) {
                        if(appPasswordProvider.isAppPassword(inputPassword.value)) {
                            withContext(Dispatchers.Main) {
                                onAuthCompleted()
                            }
                        } else {
                            descriptionText.value = resourcesProvider.getString(R.string.Wrong_password)
                            descriptionColor.value = errorColor
                            emptyPasswordCircleColor.value = errorColor
                            onClearAll()
                        }
                    }
                }
            }

            override fun onClearNumber() {
                inputPassword.value = inputPassword.value.dropLast(1)
            }

            override fun onClearAll() {
                inputPassword.value = ""
            }

            override fun onSendFingerPrintRequest() {
                val activity = fragmentActivity ?: return
                biometricAuthorizationManager.sendBiometricAuthorizationRequest(
                    activity = activity,
                    callBack = fingerPrintCallBack
                )
            }

            override val passwordSize: Int
                get() = 4

            override val enableFingerPrintAuthorization: Boolean
                get() = isFingerPrintEnabled

        }
    }

    val fingerPrintCallBack by lazy {
        object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            @SuppressLint("ResourceType")
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                toastManager.showToast(resourcesProvider
                    .getString(com.xxmrk888ytxx.observer.R.string.Biometric_error))
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onAuthCompleted()
            }

            @SuppressLint("ResourceType")
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                toastManager.showToast(resourcesProvider
                    .getString(com.xxmrk888ytxx.observer.R.string.Biometric_error))
            }
        }
    }

    private fun onAuthCompleted() {
        navController?.popBackStack()
        navController?.navigate(Screen.MainScreen.route) { launchSingleTop = true }
    }

    fun registerInActivityLifeCircle(activityLifecycleRegister: ActivityLifecycleRegister) {
        this.activityLifecycleRegister = activityLifecycleRegister
        this.activityLifecycleRegister?.registerCallback(this)
    }

    override fun onCleared() {
        super.onCleared()
        activityLifecycleRegister?.unregisterCallback(this)
        activityLifecycleRegister = null
        fragmentActivity = null
    }
}