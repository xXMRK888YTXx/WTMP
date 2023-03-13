@file:Suppress("DEPRECATION")

package com.xxmrk888ytxx.observer.domain.BiometricAuthorizationManager

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.xxmrk888ytxx.coredeps.SharedInterfaces.BiometricAuthorizationManager
import com.xxmrk888ytxx.observer.R
import javax.inject.Inject

@Suppress("DEPRECATION")
internal class BiometricAuthorizationManagerImpl @Inject constructor(
    private val context: Context
) : BiometricAuthorizationManager {

    override fun isFingerPrintScannerAvailable(): Boolean {
        return try {
            val fingerPrintManager = context.getSystemService(Context.FINGERPRINT_SERVICE)
                    as FingerprintManager
            fingerPrintManager.hasEnrolledFingerprints()
        }catch (e:Exception) {
            false
        }

    }

    override fun sendBiometricAuthorizationRequest(
        activity: FragmentActivity,
        callBack:BiometricPrompt.AuthenticationCallback
    ) {
        val biometricPrompt = BiometricPrompt(activity,callBack)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.Verify_your_identity))
            .setSubtitle(context.getString(R.string.Use_biometric_data))
            .setNegativeButtonText(context.getString(R.string.Use_password))
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}