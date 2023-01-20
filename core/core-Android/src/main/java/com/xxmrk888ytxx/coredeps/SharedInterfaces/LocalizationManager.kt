package com.xxmrk888ytxx.coredeps.SharedInterfaces

import com.xxmrk888ytxx.coredeps.models.SupportedLanguage

interface LocalizationManager {
    fun setupLocalization(supportedLanguage: SupportedLanguage)

    val currentLocalization : SupportedLanguage
}