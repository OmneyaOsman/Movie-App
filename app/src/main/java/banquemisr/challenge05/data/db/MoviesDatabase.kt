package banquemisr.challenge05.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import banquemisr.challenge05.data.db.dao.MoviesDao
import banquemisr.challenge05.data.db.dao.RemoteKeysDao
import banquemisr.challenge05.data.entities.GenreEntity
import banquemisr.challenge05.data.entities.MovieEntity
import banquemisr.challenge05.data.entities.RemoteKeys

@Database(
    entities = [MovieEntity::class, GenreEntity::class, RemoteKeys::class],
    version = 7,
    exportSchema = false
)
@TypeConverters(value = [GenereListConverter::class])
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}