package parinexus.tmdb.movies.presentation.screens.details

import parinexus.tmdb.movies.models.PresentationMovieCast
import parinexus.tmdb.movies.utils.UiState

data class MovieDetailsUiState(
    val isFavorite: Boolean = false,
    val fetchFavoriteError: String = "",
    val toggleFavoriteError: String = "",
    val cast: UiState<List<PresentationMovieCast>> = UiState.Loading,
)