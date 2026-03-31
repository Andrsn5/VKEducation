package dev.andre.vkeducation.presentation.domain

interface AppDetailsRepository {
    suspend fun get(id: String): App
}