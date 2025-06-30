package parinexus.tmdb.movies.domain.usecase

import kotlinx.coroutines.flow.Flow
import parinexus.tmdb.movies.domain.models.DomainMovieCast
import parinexus.tmdb.movies.domain.repository.MoviesRepository

class FetchMovieCastUseCase(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(movieId: Int): Flow<List<DomainMovieCast>> {
        return moviesRepository.fetchMovieCastList(movieId)
    }
}
