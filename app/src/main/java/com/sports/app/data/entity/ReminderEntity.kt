package com.sports.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminderEntity")
data class ReminderEntity(
    @PrimaryKey
    val id: Int = 0,
    val home: String,
    val away: String,
    val description: String,
    val date: String,
)
