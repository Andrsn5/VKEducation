package dev.andre.vkeducation.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.andre.vkeducation.presentation.common.CoroutineDispatchers
import dev.andre.vkeducation.presentation.common.CoroutineDispatchersImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    @MainDispatchers
    fun provideMainDispatchers() : CoroutineDispatcher = Dispatchers.Main

    @Provides
    @IoDispatchers
    fun provideIoDispatchers() : CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatchers() : CoroutineDispatcher = Dispatchers.Default

    @Provides
    @UnconfinedDispatcher
    fun provideUnconfinedDispatchers() : CoroutineDispatcher = Dispatchers.Unconfined

    @Provides
    @Singleton
    fun provideCoroutineDispatchers(
        @MainDispatchers main: CoroutineDispatcher,
        @IoDispatchers io: CoroutineDispatcher,
        @DefaultDispatcher default: CoroutineDispatcher,
        @UnconfinedDispatcher unconfined: CoroutineDispatcher
    ) : CoroutineDispatchers = CoroutineDispatchersImpl(main, io, default, unconfined)
}