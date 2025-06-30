package parinexus.tmdb.movies.presentation.screens.details

import parinexus.tmdb.movies.models.PresentationMovieEntity

sealed class MovieDetailsIntent {
    data class LoadFavoriteStatus(val movie: PresentationMovieEntity) : MovieDetailsIntent()
    data class SetFavoriteStatus(val movie: PresentationMovieEntity, val isFavorite: Boolean) :
        MovieDetailsIntent()

    data class FetchCast(val movieId: Int) : MovieDetailsIntent()
}