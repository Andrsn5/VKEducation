package dev.andre.vkeducation.presentation.data

import dev.andre.vkeducation.presentation.domain.App
import dev.andre.vkeducation.presentation.domain.AppDetailsRepository

class AppDetailsRepostoryImpl(
    private val appDetailsApi: AppDetailsApi,
    private val appDetailsMapper: AppDetailsMapper
): AppDetailsRepository {
    override suspend fun get(id: String): App {
        val appDetailsDto = appDetailsApi.get(id)
        if (appDetailsDto == null) {
            throw IllegalArgumentException("App not found")
        }
        return appDetailsMapper.toDomain(appDetailsDto)
    }
}