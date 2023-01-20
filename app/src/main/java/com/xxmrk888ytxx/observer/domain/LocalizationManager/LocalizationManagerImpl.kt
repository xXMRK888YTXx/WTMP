package com.xxmrk888ytxx.observer.domain.LocalizationManager

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.xxmrk888ytxx.coredeps.SharedInterfaces.LocalizationManager
import com.xxmrk888ytxx.coredeps.models.SupportedLanguage
import java.util.*
import javax.inject.Inject

internal class LocalizationManagerImpl @Inject constructor() : LocalizationManager {
    override fun setupLocalization(supportedLanguage: SupportedLanguage) {
        AppCompatDelegate
            .setApplicationLocales(LocaleListCompat.create(Locale(supportedLanguage.language)))
    }

    override val currentLocalization: SupportedLanguage
        get() {
            return when(AppCompatDelegate.getApplicationLocales()[0]?.language) {
                SupportedLanguage.EN.language -> SupportedLanguage.EN
                SupportedLanguage.RU.language -> SupportedLanguage.RU
                else -> SupportedLanguage.System
            }
        }
}