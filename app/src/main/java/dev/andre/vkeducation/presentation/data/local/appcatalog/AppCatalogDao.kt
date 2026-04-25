package dev.andre.vkeducation.presentation.data.local.appcatalog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface AppCatalogDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAppCatalog(appCatalogEntity: List<AppCatalogEntity>)

    @Query("SELECT * FROM app_catalog")
    @Transaction
    fun observeAppCatalogWithWishList(): Flow<List<AppCatalogWithWish>?>

    @Query("SELECT * FROM app_catalog")
    @Transaction
    fun observeAppCatalogWithDownloads(): Flow<List<AppCatalogWithDownloads>?>
}