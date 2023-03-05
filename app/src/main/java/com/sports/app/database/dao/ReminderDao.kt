package com.sports.app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sports.app.data.entity.ReminderEntity

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReminder(item: ReminderEntity)

    @Query("SELECT * FROM reminderEntity")
    suspend fun getAllReminders(): List<ReminderEntity>

    @Query("DELETE FROM reminderEntity")
    fun deleteAll()
}
