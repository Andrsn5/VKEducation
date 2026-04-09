package dev.andre.vkeducation.presentation.domain.repository

import dev.andre.vkeducation.presentation.domain.model.App
import kotlinx.coroutines.flow.Flow

interface AppDetailsRepository {
    suspend fun get(id: String): Flow<App>
}