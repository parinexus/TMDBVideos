package parinexus.tmdb.movies.presentation.screens.favorite

import parinexus.tmdb.movies.models.PresentationMovieEntity
import parinexus.tmdb.movies.utils.UiState

data class FavoriteMoviesState(
    val favoriteMovies: UiState<List<PresentationMovieEntity>> = UiState.Loading,
)
