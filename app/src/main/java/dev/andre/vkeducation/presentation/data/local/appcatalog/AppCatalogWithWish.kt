package dev.andre.vkeducation.presentation.data.local.appcatalog

import androidx.room.Embedded

data class AppCatalogWithWish(
    @Embedded val app: AppCatalogEntity,
    val isInWishList: Boolean
)