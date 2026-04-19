package dev.andre.vkeducation.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.andre.vkeducation.presentation.data.impl.WishlistRepositoryImpl
import dev.andre.vkeducation.presentation.domain.repository.WishListRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class WishListModule {
    @Binds
    abstract fun bindWishListRepository(impl: WishlistRepositoryImpl) : WishListRepository
}