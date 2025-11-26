package com.xxmrk888ytxx.database

import android.content.Context
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.PackageNameAddedByUserRepository
import com.xxmrk888ytxx.database.DI.DaggerDataBaseComponent
import com.xxmrk888ytxx.database.Entity.PackageNameAddedByUserEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PackageNameAddedByUserRepositoryImpl @Inject constructor(
    private val context: Context
) : PackageNameAddedByUserRepository {

    private val dataBaseComponent by lazy {
        DaggerDataBaseComponent.factory().create(context)
    }

    private val dao by lazy {
        dataBaseComponent.packageNameAddedByUserDao
    }

    private val mutex = Mutex()

    override suspend fun addPackageName(packageName: String) = doQuery {
        dao.addPackageName(
            PackageNameAddedByUserEntry(
                packageName = packageName
            )
        )
    }

    override suspend fun removePackageName(packageName: String) = doQuery {
        dao.removePackageName(packageName)
    }

    override val packageNamesAddedByUser: Flow<List<String>> =
        dao.getAllPackageNames().map { list -> list.map { it.packageName } }

    private suspend fun doQuery(block: suspend () -> Unit) {
        withContext(Dispatchers.IO) { mutex.withLock { block() } }
    }
}