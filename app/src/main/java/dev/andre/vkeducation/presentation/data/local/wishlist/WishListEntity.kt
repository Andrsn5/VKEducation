package dev.andre.vkeducation.presentation.data.local.wishlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish_list")
data class WishListEntity (
    @PrimaryKey val id: String
)