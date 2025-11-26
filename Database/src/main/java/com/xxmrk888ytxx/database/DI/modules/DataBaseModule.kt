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
    fun provideAppDataBase(context: Context): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java, "Database.db").build()

    @Provides
    fun provideDeviceEventDao(dataBase: AppDataBase): DeviceEventDao = dataBase.getDeviceEventDao()

    @Provides
    fun provideAppOpenEventDao(dataBase: AppDataBase): AppOpenEventDao =
        dataBase.getAppOpenEventDao()

    @Provides
    fun provideUnlockDeviceEvent(dataBase: AppDataBase): UnlockDeviceEvent =
        dataBase.getUnlockDeviceEventDao()

    @Provides
    fun provideTrackedAppDao(dataBase: AppDataBase): TrackedAppDao = dataBase.getTrackedAppDao()

    @Provides
    fun provideAppOpenTimeLimitDao(dataBase: AppDataBase): AppOpenTimeLimitDao =
        dataBase.getAppOpenTimeLimitDao()

    @Provides
    fun providePackageNameAddedByUserDao(dataBase: AppDataBase): PackageNameAddedByUserDao =
        dataBase.getPackageNameAddedByUserDao()
}