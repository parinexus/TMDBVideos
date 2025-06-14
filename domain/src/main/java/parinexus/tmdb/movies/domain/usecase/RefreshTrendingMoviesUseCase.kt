package parinexus.tmdb.movies.domain.usecase

import parinexus.tmdb.movies.domain.repository.MoviesRepository

class RefreshTrendingMoviesUseCase(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke() {
        moviesRepository.refreshTrendingMovies()
    }
}