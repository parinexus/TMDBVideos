package parinexus.tmdb.movies.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import parinexus.tmdb.movies.domain.models.DomainMovieCast
import parinexus.tmdb.movies.domain.models.DomainMovieEntity

interface MoviesRepository {
    fun getTrendingMovies(): Flow<List<DomainMovieEntity>>
    suspend fun refreshTrendingMovies()
    fun fetchMoviesByCategory(category: String): Flow<PagingData<DomainMovieEntity>>
    fun fetchMovieCastList(movieId: Int): Flow<List<DomainMovieCast>>
    suspend fun fetchFavoriteMovieById(movieId: Int): DomainMovieEntity?


    suspend fun addFavoriteMovie(movie: DomainMovieEntity)
    suspend fun deleteFavoriteMovie(movieId: Int)
}
