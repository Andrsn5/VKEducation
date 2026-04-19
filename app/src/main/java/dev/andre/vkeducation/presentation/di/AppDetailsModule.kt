package dev.andre.vkeducation.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.andre.vkeducation.presentation.data.impl.AppDetailsRepositoryImpl
import dev.andre.vkeducation.presentation.domain.repository.AppDetailsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDetailsModule {
    @Binds
    abstract fun bindAppDetailsRepository(
        appDetailsRepositoryImpl: AppDetailsRepositoryImpl
    ): AppDetailsRepository
}