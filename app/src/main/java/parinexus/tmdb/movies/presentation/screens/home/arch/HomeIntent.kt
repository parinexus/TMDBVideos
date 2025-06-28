package parinexus.tmdb.movies.presentation.screens.home.arch

import parinexus.tmdb.movies.models.MovieCategory

sealed class HomeIntent {
    object FetchTrendingMovies : HomeIntent()
    data class FetchMoviesByCategory(val category: MovieCategory) : HomeIntent()
}
