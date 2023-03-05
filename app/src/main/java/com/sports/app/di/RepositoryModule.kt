package com.sports.app.di

import com.sports.app.repository.SportsRepository
import com.sports.app.repository.SportsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SportsRepositoryModule {

    @Binds
    abstract fun bindRepository(repository: SportsRepositoryImpl): SportsRepository
}
