package com.xxmrk888ytxx.coredeps.SharedInterfaces

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity

interface BiometricAuthorizationManager {

    fun isFingerPrintScannerAvailable() : Boolean

    fun sendBiometricAuthorizationRequest(
        activity: FragmentActivity,
        callBack: BiometricPrompt.AuthenticationCallback
    )
}