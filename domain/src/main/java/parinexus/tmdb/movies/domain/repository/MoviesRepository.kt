package parinexus.tmdb.movies.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import parinexus.tmdb.movies.domain.models.MovieEntity

interface MoviesRepository {
    fun fetchTrendingMovies(): Flow<List<MovieEntity>>
    fun fetchMoviesByCategory(category: String): Flow<PagingData<MovieEntity>>
}
