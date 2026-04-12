package dev.andre.vkeducation.presentation.data.impl

import dev.andre.vkeducation.presentation.common.CoroutineDispatchers
import dev.andre.vkeducation.presentation.data.local.wishlist.WishListDao
import dev.andre.vkeducation.presentation.domain.repository.WishListRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val dao: WishListDao,
    private val dispatchers: CoroutineDispatchers,
) : WishListRepository {
    override suspend fun toggle(id: String) {
        withContext(dispatchers.io()){
            dao.toggle(id)
        }
    }
}