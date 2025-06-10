package parinexus.tmdb.movies.data.mapper

import parinexus.tmdb.movies.data.dataSources.remote.models.movie.Movie
import parinexus.tmdb.movies.domain.models.MovieEntity

fun Movie.toMovieEntity(category: String): MovieEntity {
    return MovieEntity(
        id = id,
        category = category,
        adult = adult,
        backdropPath = backdropPath ?: "",
        genreIds = genreIds.joinToString(","),
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}
