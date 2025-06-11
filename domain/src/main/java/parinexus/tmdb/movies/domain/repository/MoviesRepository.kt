package parinexus.tmdb.movies.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import parinexus.tmdb.movies.domain.models.DomainMovieEntity

interface MoviesRepository {
    fun fetchTrendingMovies(): Flow<List<DomainMovieEntity>>
    fun fetchMoviesByCategory(category: String): Flow<PagingData<DomainMovieEntity>>
}
