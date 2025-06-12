package parinexus.tmdb.movies.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import parinexus.tmdb.movies.domain.models.DomainMovieEntity
import parinexus.tmdb.movies.domain.repository.MoviesRepository

class GetMoviesByCategoryUseCase(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(category: String): Flow<PagingData<DomainMovieEntity>> {
        return moviesRepository.fetchMoviesByCategory(category)
    }
}