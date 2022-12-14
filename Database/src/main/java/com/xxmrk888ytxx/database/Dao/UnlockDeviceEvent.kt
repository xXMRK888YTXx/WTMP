package com.xxmrk888ytxx.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import com.xxmrk888ytxx.database.Entity.UnlockDeviceEventEntity

@Dao
internal interface UnlockDeviceEvent {
    @Insert
    suspend fun addEvent(unlockDeviceEventEntity: UnlockDeviceEventEntity)
}