package com.xxmrk888ytxx.database.Entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.xxmrk888ytxx.coredeps.models.DeviceEvent

/**
 * [Ru]
 * Таблица которая является предаставлением [DeviceEvent.AppOpen]
 * @param eventId - id события, который является внешним ключём на базовую таблицу с событиями
 * @param packageName - Имя пакета открытого приложения
 * @param appName - Имя приложения, если такое удалось получить
 */
/**
 * [Ru]
 * Table that is a view of [DeviceEvent.AppOpen]
 * @param eventId - event id, which is a foreign key to the base table with events
 * @param packageName - Package name of the opened application
 * @param appName - Application name, if one can be obtained
 */
@Entity(
    tableName = "AppOpenEvents",
    indices = [
        Index("eventId", unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = DeviceEventEntity::class,
            parentColumns = ["eventId"],
            childColumns = ["eventId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
internal data class AppOpenEventEntity(
    @PrimaryKey val eventId:Int,
    val packageName:String
)