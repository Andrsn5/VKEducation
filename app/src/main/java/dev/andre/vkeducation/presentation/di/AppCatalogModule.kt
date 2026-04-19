package dev.andre.vkeducation.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.andre.vkeducation.presentation.data.impl.AppCatalogRepositoryImpl
import dev.andre.vkeducation.presentation.domain.repository.AppCatalogRepository


@Module
@InstallIn(SingletonComponent::class)
abstract class AppCatalogModule {
    @Binds
    abstract fun bindAppCatalogRepository(
        appCatalogRepositoryImpl : AppCatalogRepositoryImpl
    ) : AppCatalogRepository
}