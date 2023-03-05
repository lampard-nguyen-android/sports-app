package com.sports.app.di

import com.sports.app.reminder.ReminderManager
import com.sports.app.reminder.ReminderManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    abstract fun bindReminderManager(manager: ReminderManagerImpl): ReminderManager
}
