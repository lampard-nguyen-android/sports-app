package com.sports.app.di

import com.sports.app.utils.dispatcher.DispatcherProvider
import com.sports.app.utils.dispatcher.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherProviderModule {
    @Binds
    abstract fun bindDispatcher(implementation: DispatcherProviderImpl): DispatcherProvider
}
