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

    @Query("UPDATE app_details SET isInWishList = :isInWishList WHERE id = :id")
    suspend fun updateWishListStatus(id : String , isInWishList : Boolean)
}