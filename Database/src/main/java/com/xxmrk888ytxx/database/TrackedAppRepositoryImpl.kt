package com.xxmrk888ytxx.database

import android.content.Context
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TrackedAppRepository
import com.xxmrk888ytxx.database.DI.DaggerDataBaseComponent
import com.xxmrk888ytxx.database.DI.DataBaseComponent
import com.xxmrk888ytxx.database.Entity.TrackedAppEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrackedAppRepositoryImpl @Inject constructor(
    private val context: Context
) : TrackedAppRepository {

    private val dataBaseComponent:DataBaseComponent by lazy {
        DaggerDataBaseComponent.factory().create(context)
    }

    private val trackedAppDao by lazy {
        dataBaseComponent.trackedAppDao
    }

    private val mutex = Mutex()

    override fun getAllTrackedPackageNames(): Flow<List<String>> {
        return trackedAppDao.getAllTrackedApp().map { it.map { it.packageName } }
    }

    override suspend fun addTrackedPackageName(packageName: String) {
        withContext(Dispatchers.IO) {
            mutex.withLock {
                trackedAppDao.addTrackedApp(TrackedAppEntity(0,packageName))
            }
        }
    }

    override suspend fun removeTrackedPackageName(packageName: String) {
        withContext(Dispatchers.IO) {
            mutex.withLock {
                trackedAppDao.removeTrackedApp(packageName)
            }
        }
    }
}