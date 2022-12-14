package com.xxmrk888ytxx.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xxmrk888ytxx.database.Dao.AppOpenEventDao
import com.xxmrk888ytxx.database.Dao.DeviceEventDao
import com.xxmrk888ytxx.database.Dao.UnlockDeviceEvent
import com.xxmrk888ytxx.database.Entity.AppOpenEventEntity
import com.xxmrk888ytxx.database.Entity.DeviceEventEntity
import com.xxmrk888ytxx.database.Entity.UnlockDeviceEventEntity

@Database(
    entities = [
        DeviceEventEntity::class,
        UnlockDeviceEventEntity::class,
        AppOpenEventEntity::class
    ],
    version = 1
)
internal abstract class AppDataBase : RoomDatabase() {
    abstract fun getDeviceEventDao() : DeviceEventDao

    abstract fun getUnlockDeviceEventDao() : UnlockDeviceEvent

    abstract fun getAppOpenEventDao() : AppOpenEventDao
}