package banquemisr.challenge05.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import banquemisr.challenge05.data.model.MovieModel

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(games: List<MovieModel>)

    @Query("SELECT * FROM Movie WHERE movieType LIKE :query")
    fun getMoviesPagingSource(query: String): PagingSource<Int, MovieModel>

    @Query("SELECT * FROM Movie")
    suspend fun getMovies(): List<MovieModel>

    @Query("SELECT * FROM Movie WHERE id LIKE :movieId || '%'")
    suspend fun getMovieDetails(movieId: Int): MovieModel

    @Query("DELETE FROM Movie ")
    suspend fun deleteMovies()

}