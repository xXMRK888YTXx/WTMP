package com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository

import kotlinx.coroutines.flow.Flow

interface TrackedAppRepository {

    fun getAllTrackedPackageNames() : Flow<String>

    suspend fun addTrackedPackageName(packageName: String)

    suspend fun removeTrackedPackageName(packageName: String)
}