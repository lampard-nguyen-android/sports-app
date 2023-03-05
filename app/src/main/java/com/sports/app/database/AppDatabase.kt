package com.sports.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sports.app.data.entity.ReminderEntity
import com.sports.app.database.dao.ReminderDao

@Database(entities = [ReminderEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao
}
