package dev.andre.vkeducation.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.andre.vkeducation.presentation.data.impl.DownloadsListRepositoryImpl
import dev.andre.vkeducation.presentation.domain.repository.DownloadsListRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DownloadListImpl {
    @Binds
    abstract fun bindDownloadsListRepository(impl: DownloadsListRepositoryImpl) : DownloadsListRepository
}