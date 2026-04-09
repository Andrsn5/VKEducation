package dev.andre.vkeducation.presentation.data.local.appcatalog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppCatalogDao {

    @Query("SELECT * FROM app_catalog")
    fun getAll() : Flow<List<AppCatalogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAppCatalog(appCatalogEntity: List<AppCatalogEntity>)
}