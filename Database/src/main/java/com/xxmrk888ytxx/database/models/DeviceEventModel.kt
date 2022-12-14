package com.xxmrk888ytxx.database.models

import androidx.room.Embedded
import androidx.room.Relation
import com.xxmrk888ytxx.database.Entity.AppOpenEventEntity
import com.xxmrk888ytxx.database.Entity.DeviceEventEntity
import com.xxmrk888ytxx.database.Entity.UnlockDeviceEventEntity

internal data class DeviceEventModel(
    @Embedded val eventInfo:DeviceEventEntity,

    @Relation(
        entity = UnlockDeviceEventEntity::class,
        parentColumn = "eventId",
        entityColumn = "eventId"
    )
    val unlockEvents:UnlockDeviceEventEntity?,

    @Relation(
        entity = AppOpenEventEntity::class,
        parentColumn = "eventId",
        entityColumn = "eventId"
    )
    val appOpenEvents: AppOpenEventEntity?
)