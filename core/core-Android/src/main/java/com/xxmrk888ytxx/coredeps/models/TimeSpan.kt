package com.xxmrk888ytxx.coredeps.models

data class TimeSpan(
    val start:Long,
    val end:Long
) {
    val isValid : Boolean
        get() {
            if(this == NO_SETUP) return true

            return (start > 0 && end > 0) && start < end
        }
    companion object {
        val NO_SETUP = TimeSpan(-1,-1)
    }
}
