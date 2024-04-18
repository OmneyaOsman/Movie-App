package banquemisr.challenge05.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import banquemisr.challenge05.data.db.dao.MoviesDao
import banquemisr.challenge05.data.db.dao.RemoteKeysDao
import banquemisr.challenge05.data.model.Genre
import banquemisr.challenge05.data.model.MovieModel
import banquemisr.challenge05.data.model.RemoteKeys

@Database(
    entities = [MovieModel::class, Genre::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun remoteKeysDao() : RemoteKeysDao
}