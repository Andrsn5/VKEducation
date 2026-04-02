package dev.andre.vkeducation.presentation.data.impl

import dev.andre.vkeducation.presentation.data.api.AppDetailsApi
import dev.andre.vkeducation.presentation.data.mapper.AppDetailsMapper
import dev.andre.vkeducation.presentation.domain.model.App
import dev.andre.vkeducation.presentation.domain.repository.AppDetailsRepository

class AppDetailsRepositoryImpl(
    private val api: AppDetailsApi,
    private val mapper: AppDetailsMapper
): AppDetailsRepository {
    override suspend fun get(id: String): App {
        val appDetailsDto = api.get(id)
        if (appDetailsDto == null) {
            throw IllegalArgumentException("Приложение не найдено")
        }
        return mapper.toDomain(appDetailsDto)
    }
}