package com.xxmrk888ytxx.database.Dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.xxmrk888ytxx.database.Entity.PackageNameAddedByUserEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface PackageNameAddedByUserDao {

    @Query("SELECT * FROM PackageNameAddedByUserTable")
    fun getAllPackageNames(): Flow<List<PackageNameAddedByUserEntry>>

    @Upsert
    suspend fun addPackageName(packageNameAddedByUserEntry: PackageNameAddedByUserEntry)

    @Query("DELETE FROM PackageNameAddedByUserTable WHERE packageName = :packageName")
    suspend fun removePackageName(packageName: String)
}