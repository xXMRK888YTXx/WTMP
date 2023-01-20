package com.xxmrk888ytxx.coredeps.models

import java.util.*

sealed class SupportedLanguage(val language:String)  {

    object System : SupportedLanguage("xx")

    object EN : SupportedLanguage(Locale.ENGLISH.language)

    object RU : SupportedLanguage("ru")
}