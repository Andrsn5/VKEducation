package dev.andre.vkeducation.presentation.data.local.appdetails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDetailsDao {
    @Query("SELECT * FROM app_details WHERE id = :id")
    fun getAppDetails(id : String) : Flow<AppDetailsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppDetails(appDetailsEntity: AppDetailsEntity)

    @Query("SELECT app_details.*," +
            " CASE WHEN wish_list.id IS NOT NULL THEN 1 ELSE 0 END AS isInWishList" +
            " FROM app_details" +
            " LEFT JOIN wish_list ON app_details.id = wish_list.id" +
            " WHERE app_details.id = :id")
    fun observeDetailsWithWishlist(id : String) : Flow<AppDetailsWithWish?>
}