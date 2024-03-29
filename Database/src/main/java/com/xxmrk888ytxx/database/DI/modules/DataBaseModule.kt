package com.xxmrk888ytxx.database.DI.modules

import android.content.Context
import androidx.room.Room
import com.xxmrk888ytxx.database.AppDataBase
import com.xxmrk888ytxx.database.DI.DataBaseScope
import com.xxmrk888ytxx.database.Dao.*
import dagger.Module
import dagger.Provides

@Module
internal class DataBaseModule {
    @Provides
    @DataBaseScope
    fun provideAppDataBase(context: Context) : AppDataBase {
        return Room.databaseBuilder(context,AppDataBase::class.java,"Database.db").build()
    }

    @Provides
    fun provideDeviceEventDao(dataBase: AppDataBase) : DeviceEventDao {
        return dataBase.getDeviceEventDao()
    }

    @Provides
    fun provideAppOpenEventDao(dataBase: AppDataBase) : AppOpenEventDao {
        return dataBase.getAppOpenEventDao()
    }

    @Provides
    fun provideUnlockDeviceEvent(dataBase: AppDataBase) : UnlockDeviceEvent {
        return dataBase.getUnlockDeviceEventDao()
    }

    @Provides
    fun provideTrackedAppDao(dataBase: AppDataBase) : TrackedAppDao {
        return dataBase.getTrackedAppDao()
    }

    @Provides
    fun provideAppOpenTimeLimitDao(dataBase: AppDataBase) : AppOpenTimeLimitDao {
        return dataBase.getAppOpenTimeLimitDao()
    }
}