package parinexus.tmdb.movies.data.model

import parinexus.tmdb.movies.data.dataSources.local.entities.DbMovieEntity

fun createTestDbMovieEntity(
    id: Int = 1,
    title: String = "Title",
    category: String = "popular"
): DbMovieEntity = DbMovieEntity(
    id = id,
    category = category,
    adult = false,
    backdropPath = "/backdrop.jpg",
    genreIds = "[1,2,3]",
    originalLanguage = "en",
    originalTitle = title,
    overview = "Some overview",
    popularity = 8.5,
    posterPath = "/poster.jpg",
    releaseDate = "2024-01-01",
    title = title,
    video = false,
    voteAverage = 7.5,
    voteCount = 100
)
