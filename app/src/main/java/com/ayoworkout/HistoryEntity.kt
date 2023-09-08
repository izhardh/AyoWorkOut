package com.ayoworkout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History-Workout")
data class HistoryEntity(
    @PrimaryKey
    val date: String
)
