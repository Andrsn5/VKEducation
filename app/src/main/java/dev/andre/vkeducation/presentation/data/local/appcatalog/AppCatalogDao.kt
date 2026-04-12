package dev.andre.vkeducation.presentation.data.local.appcatalog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogWithWish
import kotlinx.coroutines.flow.Flow

@Dao
interface AppCatalogDao {

    @Query("SELECT * FROM app_catalog")
    fun getAll() : Flow<List<AppCatalogEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppCatalog(appCatalogEntity: List<AppCatalogEntity>)

    @Query("SELECT * FROM app_catalog WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): AppCatalogEntity?

    @Query("SELECT * FROM app_catalog")
    fun observeAppCatalogWithWishList(): Flow<List<AppCatalogWithWish>?>
}