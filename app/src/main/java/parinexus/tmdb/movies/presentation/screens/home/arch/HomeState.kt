package parinexus.tmdb.movies.presentation.screens.home.arch

import parinexus.tmdb.movies.models.PresentationMovieEntity
import parinexus.tmdb.movies.utils.UiState

data class HomeState(
    val trendingMovies: UiState<List<PresentationMovieEntity>> = UiState.Loading
)
