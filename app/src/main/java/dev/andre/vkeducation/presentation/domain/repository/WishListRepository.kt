package dev.andre.vkeducation.presentation.domain.repository

interface WishListRepository {
    suspend fun toggle(id: String)
}