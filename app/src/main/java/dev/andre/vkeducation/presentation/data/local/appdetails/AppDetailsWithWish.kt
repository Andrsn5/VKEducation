package dev.andre.vkeducation.presentation.data.local.appdetails

import androidx.room.Embedded
import androidx.room.Relation
import dev.andre.vkeducation.presentation.data.local.wishlist.WishListEntity

data class AppDetailsWithWish(
    @Embedded val app: AppDetailsEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = WishListEntity::class
    )
    val wishList: List<WishListEntity>
)