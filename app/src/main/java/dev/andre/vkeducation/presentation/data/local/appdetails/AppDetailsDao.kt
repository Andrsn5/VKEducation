package dev.andre.vkeducation.presentation.data.local.appdetails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDetailsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAppDetails(appDetailsEntity: AppDetailsEntity)

    @Query("SELECT * FROM app_details WHERE id = :id")
    fun observeDetailsWithWishlist(id : String) : Flow<AppDetailsWithWish?>
}