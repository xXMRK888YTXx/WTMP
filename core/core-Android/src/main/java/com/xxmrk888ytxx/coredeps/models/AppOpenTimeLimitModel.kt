package com.xxmrk888ytxx.coredeps.models

data class AppOpenTimeLimitModel(
    val packageName:String,
    val endLimitTime:Long,
    val timeSetupLimit:Int
)