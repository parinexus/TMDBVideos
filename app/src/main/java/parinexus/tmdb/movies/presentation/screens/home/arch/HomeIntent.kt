package parinexus.tmdb.movies.presentation.screens.home.arch

sealed class HomeIntent {
    object FetchTrendingMovies : HomeIntent()
    object FetchPopularMovies : HomeIntent()
}
