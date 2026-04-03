package dev.andre.vkeducation.presentation.data.impl

import dev.andre.vkeducation.presentation.data.api.ApiService
import dev.andre.vkeducation.presentation.data.mapper.AppDetailsMapper
import dev.andre.vkeducation.presentation.domain.model.App
import dev.andre.vkeducation.presentation.domain.repository.AppDetailsRepository
import javax.inject.Inject

class AppDetailsRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val mapper: AppDetailsMapper
): AppDetailsRepository {
    override suspend fun get(id: String): App {
        val appDetailsDto = api.getAppDetails(id)
        if (appDetailsDto == null) {
            throw IllegalArgumentException("Приложение не найдено")
        }
        return mapper.toDomain(appDetailsDto)
    }
}