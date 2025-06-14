package parinexus.tmdb.movies.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import parinexus.tmdb.movies.domain.models.DomainMovieEntity

interface MoviesRepository {
    fun getTrendingMovies(): Flow<List<DomainMovieEntity>>
    suspend fun refreshTrendingMovies()
    fun fetchMoviesByCategory(category: String): Flow<PagingData<DomainMovieEntity>>
}
