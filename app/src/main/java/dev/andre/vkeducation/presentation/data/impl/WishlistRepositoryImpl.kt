package dev.andre.vkeducation.presentation.data.impl

import dev.andre.vkeducation.presentation.data.local.wishlist.WishListDao
import dev.andre.vkeducation.presentation.domain.repository.WishListRepository
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val dao: WishListDao
) : WishListRepository {
    override suspend fun toggle(id: String) {
        dao.toggle(id)
    }
}