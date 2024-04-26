package banquemisr.challenge05.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import banquemisr.challenge05.data.entities.MovieEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM Movie")
    fun getMoviesPagingSource(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM Movie")
    suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM Movie WHERE id LIKE :movieId || '%'")
    suspend fun getMovieDetails(movieId: Int): MovieEntity

    @Query("DELETE FROM Movie ")
    suspend fun deleteMovies()

}