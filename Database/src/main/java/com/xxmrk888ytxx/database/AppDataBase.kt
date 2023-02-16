package com.xxmrk888ytxx.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.xxmrk888ytxx.database.Dao.*
import com.xxmrk888ytxx.database.Entity.*

@Database(
    entities = [
        DeviceEventEntity::class,
        UnlockDeviceEventEntity::class,
        AppOpenEventEntity::class,
        TrackedAppEntity::class,
        AppOpenTimeLimitEntity::class
    ],
    version = 2,
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2
        )
    ]
)
internal abstract class AppDataBase : RoomDatabase() {
    abstract fun getDeviceEventDao() : DeviceEventDao

    abstract fun getUnlockDeviceEventDao() : UnlockDeviceEvent

    abstract fun getAppOpenEventDao() : AppOpenEventDao

    abstract fun getTrackedAppDao() : TrackedAppDao

    abstract fun getAppOpenTimeLimitDao() : AppOpenTimeLimitDao
}