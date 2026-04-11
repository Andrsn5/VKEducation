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

    @Query("SELECT app_catalog.*," +
            " CASE WHEN wish_list.id IS NOT NULL THEN 1 ELSE 0 END AS isInWishList" +
            " FROM app_catalog" +
            " LEFT JOIN wish_list ON app_catalog.id = wish_list.id")
    fun observeAppCatalogWithWishList(): Flow<List<AppCatalogWithWish>>
}