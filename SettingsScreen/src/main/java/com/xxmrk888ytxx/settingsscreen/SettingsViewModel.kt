package com.xxmrk888ytxx.settingsscreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coredeps.Const.DEVELOPER_EMAIL
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ApplicationInfoProvider
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val applicationInfoProvider: ApplicationInfoProvider
): ViewModel() {
    val testParam = mutableStateOf(false)

    @MustBeLocalization
    internal fun sendIntentToWriteDeveloper(context: Context) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$DEVELOPER_EMAIL"))
        context.startActivity(Intent.createChooser(emailIntent, "Написать разработчику"))
    }

    val appVersion:String by lazy {
        applicationInfoProvider.applicationVersion
    }
}