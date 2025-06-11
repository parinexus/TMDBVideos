package parinexus.tmdb.movies.data.dataSources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import parinexus.tmdb.movies.data.dataSources.local.entities.FavoriteMovieEntity
import parinexus.tmdb.movies.data.dataSources.local.entities.MovieCastEntity
import parinexus.tmdb.movies.data.dataSources.local.entities.DbMovieEntity

@Database(
    entities = [DbMovieEntity::class, MovieCastEntity::class, FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}
