package parinexus.tmdb.movies.domain.usecase

import kotlinx.coroutines.flow.Flow
import parinexus.tmdb.movies.domain.models.DomainMovieEntity
import parinexus.tmdb.movies.domain.repository.MoviesRepository

class GetTrendingMoviesUseCase(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(): Flow<List<DomainMovieEntity>> {
        return moviesRepository.fetchTrendingMovies()
    }
}