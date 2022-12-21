package com.xxmrk888ytxx.coredeps.models

data class FailedUnlockTrackedConfig(
    val isTracked:Boolean,
    val makePhoto:Boolean,
    val notifyInTelegram:Boolean,
    val joinPhotoToTelegramNotify:Boolean
)