package com.xxmrk888ytxx.adutils

import android.app.Activity

interface ConsentFormLoader {

    fun checkFormState(
        onFormPrepared: () -> Unit,
        onFormNotAvailable: () -> Unit,
        onError: () -> Unit,
    )

    fun loadAndShowForm(onSuccessLoad: () -> Unit, onLoadError: () -> Unit, onDismissed: () -> Unit)


    companion object {
        fun create(activity: Activity,isDebugBuild:Boolean,alwaysShowConsentForDebug:Boolean) : ConsentFormLoader {
            return ConsentFromLoaderImpl(activity, isDebugBuild, alwaysShowConsentForDebug)
        }
    }

}