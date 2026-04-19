package dev.andre.vkeducation.presentation.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogDao
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogEntity
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsDao
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsEntity
import dev.andre.vkeducation.presentation.data.local.wishlist.WishListDao
import dev.andre.vkeducation.presentation.data.local.wishlist.WishListEntity

@Database(
    entities = [AppDetailsEntity::class, AppCatalogEntity::class, WishListEntity::class],
    version = 1
)

@TypeConverters(CategoryConverter::class, ListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDetailsDao(): AppDetailsDao
    abstract fun appCatalogDao(): AppCatalogDao
    abstract fun wishListDao(): WishListDao


    companion object{
        const val DATABASE_NAME = "app_database"
    }


}