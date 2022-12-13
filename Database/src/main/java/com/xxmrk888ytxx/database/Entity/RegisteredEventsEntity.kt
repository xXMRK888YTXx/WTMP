package com.xxmrk888ytxx.database.Entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
     indices = [
          Index("eventId", unique = true)
     ]
)
internal data class RegisteredEventsEntity(
     @PrimaryKey(autoGenerate = true) val eventId:Int = 0,
     val idFromOtherTable:Int,
     val eventType:Short
)