package dev.andre.vkeducation.presentation.data.local.wishlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface WishListDao {
    @Query("SELECT * FROM wish_list")
    fun observeWishlist(): Flow<List<WishListEntity>>

    @Query("SELECT * FROM wish_list WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): WishListEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToWishlist(entity: WishListEntity)

    @Query("DELETE FROM wish_list WHERE id = :id")
    suspend fun removeFromWishlist(id: String)


    @Transaction
    suspend fun toggle(id: String){
        val existing = getById(id)
        if (existing == null){
            addToWishlist(WishListEntity(id))
        }else{
            removeFromWishlist(id)
        }
    }
}