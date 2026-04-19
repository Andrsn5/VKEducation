package dev.andre.vkeducation.presentation.data.local.appcatalog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppCatalogDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAppCatalog(appCatalogEntity: List<AppCatalogEntity>)

    @Query("SELECT * FROM app_catalog")
    fun observeAppCatalogWithWishList(): Flow<List<AppCatalogWithWish>?>
}