package dev.andre.vkeducation.presentation.data.local.appcatalog

import androidx.room.Embedded
import androidx.room.Relation
import dev.andre.vkeducation.presentation.data.local.downloads.DownloadsListEntity

data class AppCatalogWithDownloads(
    @Embedded val app: AppCatalogEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = DownloadsListEntity::class
    )
    val downloadsList: List<DownloadsListEntity>
)
