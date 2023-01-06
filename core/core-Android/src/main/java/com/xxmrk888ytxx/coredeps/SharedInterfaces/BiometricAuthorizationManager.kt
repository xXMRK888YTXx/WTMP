package com.xxmrk888ytxx.coredeps.SharedInterfaces

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity

/**
 * [Ru]
 * Данный интерфейс отвечает за биометрическую авторизацию
 */
/**
 * [En]
 * This interface is responsible for biometric authorization
 */
interface BiometricAuthorizationManager {
    /**
     * [Ru]
     * Данный метод, проверяет имеется ли и настроен ли сканер отпечатков пальцев
     */

    /**
     * [En]
     * This method checks if a fingerprint scanner is available and configured
     */
    fun isFingerPrintScannerAvailable() : Boolean

    /**
     * [Ru]
     * Данный метод отправляет запрос на биометрическую авторизацию
     */

    /**
     * [En]
     * This method sends a request for biometric authorization
     */
    fun sendBiometricAuthorizationRequest(
        activity: FragmentActivity,
        callBack: BiometricPrompt.AuthenticationCallback
    )
}