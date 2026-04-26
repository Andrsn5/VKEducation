package dev.andre.vkeducation.presentation.domain.usecase

import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import dev.andre.vkeducation.presentation.domain.model.Category
import dev.andre.vkeducation.presentation.domain.repository.AppCatalogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFilteredCatalogUseCase @Inject constructor(
    private val appCatalogRepository: AppCatalogRepository
) {
    data class Params(
        val category: Set<Category> = emptySet(),
        val onlyWishList: Boolean = false,
        val onlyDownloads: Boolean = false
    )

    operator fun invoke(params: Params) : Flow<List<AppCatalog>>{
        return appCatalogRepository.observeAppCatalog().map { list ->
            list.filter {
                val category = params.category.isEmpty() || it.category in params.category
                val wishList = !params.onlyWishList || it.isInWishList
                val downloads = !params.onlyDownloads || it.isDownload
                category && wishList && downloads
            }
        }
    }
}