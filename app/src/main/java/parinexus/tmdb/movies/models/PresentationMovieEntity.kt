package parinexus.tmdb.movies.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PresentationMovieEntity(
    val id: Int,
    val category: String,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
): Parcelable