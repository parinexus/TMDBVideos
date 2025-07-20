package parinexus.tmdb.movies.data.model

import parinexus.tmdb.movies.domain.models.DomainMovieEntity
import parinexus.tmdb.movies.data.mapper.domainToFavMovieEntity

object TestData {
    val sampleDomainMovie = DomainMovieEntity(
        id = 1,
        category = "",
        adult = false,
        backdropPath = "/backdrop.jpg",
        genreIds = "[1, 2, 3]",
        originalLanguage = "en",
        originalTitle = "Sample Original Title",
        overview = "Sample overview",
        popularity = 9.5,
        posterPath = "/poster.jpg",
        releaseDate = "2023-01-01",
        title = "Sample Movie",
        video = false,
        voteAverage = 8.0,
        voteCount = 100
    )

    val sampleFavoriteEntity = sampleDomainMovie.domainToFavMovieEntity()
}
