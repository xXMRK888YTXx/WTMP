package com.xxmrk888ytxx.coredeps.models

import android.graphics.Bitmap

/**
 * [Ru]
 * Данный класс представляет собой модель событий устройтв которое приложения может обработать
 */
/**
 *[En]
 * This class is a model of device events that applications can process
 */
sealed class DeviceEvent(open val eventId: Int,open val time:Long) {
    /**
     * [Ru]
     * Модель событий попыток разблокироки истройства
     * [AttemptUnlockDevice.Failed] - проваленая попытка разблокировки
     * [AttemptUnlockDevice.Succeeded] - успешная попытка разблокировки
     */

    /**
     * [En]
     * Model of events of attempts to unlock the device
     * [AttemptUnlockDevice.Failed] - failed unlock attempt
     * [AttemptUnlockDevice.Succeeded] - successful unlock attempt
     */
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

    /**
     * [Ru]
     * Модель данных используемая при открытии приложения
     */

    /**
     * [En]
     * Data model used when opening the application
     */
    data class AppOpen(
        override val eventId:Int,
        val appName:String?,
        val icon: Bitmap?,
        override val time:Long
    ) : DeviceEvent(eventId,time)
}
