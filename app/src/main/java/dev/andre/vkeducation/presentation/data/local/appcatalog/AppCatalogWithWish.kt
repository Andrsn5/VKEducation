package dev.andre.vkeducation.presentation.data.local.appcatalog

import androidx.room.Embedded
import androidx.room.Relation
import dev.andre.vkeducation.presentation.data.local.wishlist.WishListEntity

data class AppCatalogWithWish(
    @Embedded val app: AppCatalogEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = WishListEntity::class
    )
    val wishList: List<WishListEntity>
)