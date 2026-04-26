package dev.andre.vkeducation.presentation.data.local.downloads

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadsListDao {
    @Query("SELECT * FROM downloads_list")
    fun observeDownloadsList(): Flow<List<DownloadsListEntity>>

    @Query("SELECT * FROM downloads_list WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): DownloadsListEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDownloadsList(entity: DownloadsListEntity)

    @Query("DELETE FROM downloads_list WHERE id = :id")
    suspend fun removeFromDownloadsList(id: String)


    @Transaction
    suspend fun toggle(id: String){
        val existing = getById(id)
        if (existing == null){
            addToDownloadsList(DownloadsListEntity(id))
        }else{
            removeFromDownloadsList(id)
        }
    }
}
