package dev.andre.vkeducation.presentation.data.local.appdetails

import androidx.room.Embedded
import androidx.room.Relation
import dev.andre.vkeducation.presentation.data.local.downloads.DownloadsListEntity

data class AppDetailsWithDownloads(
    @Embedded val app: AppDetailsEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = DownloadsListEntity::class
    )
    val downloadsList: List<DownloadsListEntity>
)
