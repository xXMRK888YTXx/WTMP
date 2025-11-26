package com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository

import kotlinx.coroutines.flow.Flow

interface PackageNameAddedByUserRepository {
    suspend fun addPackageName(packageName: String)
    suspend fun removePackageName(packageName: String)
    val packageNamesAddedByUser: Flow<List<String>>
}