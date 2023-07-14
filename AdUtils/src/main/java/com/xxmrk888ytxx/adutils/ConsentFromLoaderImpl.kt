package com.xxmrk888ytxx.adutils

import android.app.Activity
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform


internal class ConsentFromLoaderImpl(
    private val activity: Activity,
    private val isDebugBuild:Boolean,
    private val alwaysShowConsentForDebug:Boolean
) : ConsentFormLoader {

    override fun checkFormState(
        onFormPrepared: () -> Unit,
        onFormNotAvailable:() -> Unit,
        onError: () -> Unit
    ) {
        val params = ConsentRequestParameters.Builder().apply {
            setTagForUnderAgeOfConsent(false)

            if(isDebugBuild && alwaysShowConsentForDebug) {
                setConsentDebugSettings(
                    ConsentDebugSettings.Builder(activity.applicationContext)
                        .setDebugGeography(DEBUG_GEOGRAPHY_EEA)
                        .build()
                )
            }
        }.build()

        val consentInformation = UserMessagingPlatform.getConsentInformation(activity.applicationContext)

        consentInformation.requestConsentInfoUpdate(
            activity,
            params,
            {
                if(consentInformation.isConsentFormAvailable) {
                    onFormPrepared()
                } else {
                    onFormNotAvailable()
                }
            },
        ) {
            onError()
        }

    }

    override fun loadAndShowForm(
        onSuccessLoad: () -> Unit,
        onLoadError: () -> Unit,
        onDismissed: () -> Unit,
    ) {
        val consentInformation = UserMessagingPlatform.getConsentInformation(activity.applicationContext)

        UserMessagingPlatform.loadConsentForm(
            activity.applicationContext,
            { form ->
                onSuccessLoad()
                if(consentInformation.consentStatus == ConsentInformation.ConsentStatus.REQUIRED) {
                    form.show(
                        activity,
                    ) {
                        onDismissed()
                    }
                }
            },
            {
                onLoadError()
            }
        )
    }
}