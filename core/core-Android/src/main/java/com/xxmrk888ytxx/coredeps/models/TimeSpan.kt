package com.xxmrk888ytxx.coredeps.models

data class TimeSpan(
    val start:Long,
    val end:Long
) {
    companion object {
        val NO_SETUP = TimeSpan(-1,-1)
    }
}
