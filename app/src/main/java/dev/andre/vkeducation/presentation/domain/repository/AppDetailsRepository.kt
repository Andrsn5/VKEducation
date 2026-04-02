package dev.andre.vkeducation.presentation.domain.repository

import dev.andre.vkeducation.presentation.domain.model.App

interface AppDetailsRepository {
    suspend fun get(id: String): App
}