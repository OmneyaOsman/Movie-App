package banquemisr.challenge05.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import banquemisr.challenge05.data.entities.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: RemoteKeys)
    @Query("SELECT * From remote_key Where movie_id = :id")
    suspend fun getRemoteKeyByMovieID(id: Int): RemoteKeys?
    @Query("SELECT * From remote_key Where movie_id = :id AND label = :query")
    suspend fun getRemoteKeyByMovieIDAndQuery(id: Int , query: String): RemoteKeys?
    @Query("SELECT * FROM remote_key WHERE label = :query")
    suspend fun remoteKeyByQuery(query: String): RemoteKeys?
    @Query("DELETE FROM remote_key WHERE label = :query")
    suspend fun deleteByQuery(query: String)
    @Query("DELETE From remote_key")
    suspend fun clearRemoteKeys()
    @Query("SELECT created_at From remote_key Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

}