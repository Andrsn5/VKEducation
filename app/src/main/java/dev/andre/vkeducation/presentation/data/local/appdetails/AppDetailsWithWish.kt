package dev.andre.vkeducation.presentation.data.local.appdetails

import androidx.room.Embedded

data class AppDetailsWithWish(
    @Embedded val app: AppDetailsEntity,
    val isInWishList: Boolean
)