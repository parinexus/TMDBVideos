package parinexus.tmdb.movies.data.dataSources.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import parinexus.tmdb.movies.data.dataSources.local.entities.FavoriteMovieEntity
import parinexus.tmdb.movies.data.dataSources.local.entities.MovieCastEntity
import parinexus.tmdb.movies.data.dataSources.local.entities.MovieEntity
import parinexus.tmdb.movies.data.utils.Constant.FAVORITE_MOVIES_TABLE
import parinexus.tmdb.movies.data.utils.Constant.MOVIES_CAST_TABLE
import parinexus.tmdb.movies.data.utils.Constant.MOVIES_TABLE
import parinexus.tmdb.movies.data.utils.Constant.TRENDING

@Dao
interface MovieDao {

    // Movies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMoviesList(movies: List<MovieEntity>)

    @Query("SELECT * FROM $MOVIES_TABLE")
    fun getMoviesList(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM $MOVIES_TABLE WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity

    @Query("SELECT * FROM $MOVIES_TABLE WHERE category = :category")
    fun getMoviesListByCategory(category: String): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM $MOVIES_TABLE WHERE category = '$TRENDING'")
    fun getTrendingMovies(): List<MovieEntity>

    @Query("DELETE FROM $MOVIES_TABLE WHERE category = :category")
    suspend fun clearMoviesByCategory(category: String)

    // Cast
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMovieCastList(castList: List<MovieCastEntity>)

    @Query("SELECT * FROM $MOVIES_CAST_TABLE WHERE movieId = :movieId")
    fun getMovieCastById(movieId: Int): List<MovieCastEntity>

    // Favorites
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFavoriteMovie(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM $FAVORITE_MOVIES_TABLE")
    suspend fun getAllFavoriteMovies(): List<FavoriteMovieEntity>

    @Query("SELECT * FROM $FAVORITE_MOVIES_TABLE WHERE id = :movieId")
    suspend fun getFavoriteMovieById(movieId: Int): FavoriteMovieEntity?

    @Query("DELETE FROM $FAVORITE_MOVIES_TABLE WHERE id = :movieId")
    suspend fun deleteFavoriteMovie(movieId: Int)
}