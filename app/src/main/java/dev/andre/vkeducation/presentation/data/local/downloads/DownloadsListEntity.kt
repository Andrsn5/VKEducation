package dev.andre.vkeducation.presentation.data.local.downloads

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloads_list")
data class DownloadsListEntity(
    @PrimaryKey val id: String,
)