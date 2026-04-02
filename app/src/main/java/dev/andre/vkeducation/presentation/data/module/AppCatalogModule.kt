package dev.andre.vkeducation.presentation.data.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.andre.vkeducation.presentation.data.api.AppCatalogApi
import dev.andre.vkeducation.presentation.data.impl.AppCatalogRepositoryImpl
import dev.andre.vkeducation.presentation.data.mapper.AppCatalogMapper
import dev.andre.vkeducation.presentation.domain.repository.AppCatalogRepository
import javax.inject.Singleton


// лучше использовать @Binds - здесь для примера , попробовать
@Module
@InstallIn(SingletonComponent::class)
class AppCatalogModule {
    @Provides
    @Singleton
    fun provideAppCatalogRepository(
        api: AppCatalogApi,
        mapper: AppCatalogMapper
    ): AppCatalogRepository {
        return AppCatalogRepositoryImpl(api, mapper)
    }
}