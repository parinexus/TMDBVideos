package parinexus.tmdb.movies.presentation.screens.favorite

sealed class FavoriteMoviesEvent {
    object LoadFavoriteMovies : FavoriteMoviesEvent()
}