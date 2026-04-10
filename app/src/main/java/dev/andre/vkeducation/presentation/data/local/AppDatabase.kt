package dev.andre.vkeducation.presentation.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogDao
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogEntity
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsDao
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsEntity

@Database(
    entities = [AppDetailsEntity::class, AppCatalogEntity::class],
    version = 1
)

@TypeConverters(CategoryConverter::class, ListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDetailsDao(): AppDetailsDao
    abstract fun appCatalogDao(): AppCatalogDao


    companion object{
        const val DATABASE_NAME = "app_database"
    }


}