package com.xxmrk888ytxx.coredeps.models

import android.graphics.Bitmap

sealed class DeviceEvent(open val eventId: Int,open val time:Long) {

    sealed class AttemptUnlockDevice(
        override val eventId: Int,
        override val time: Long
        ) : DeviceEvent(eventId,time) {

        data class Failed(
            override val eventId: Int,
            override val time: Long
        ) : AttemptUnlockDevice(eventId, time)

        data class Succeeded (
            override val eventId: Int,
            override val time: Long
        ) : AttemptUnlockDevice(eventId, time)

    }

    data class AppOpen(
        override val eventId:Int,
        val appName:String?,
        val icon: Bitmap?,
        override val time:Long
    ) : DeviceEvent(eventId,time)
}
