package parinexus.tmdb.movies.data.mapper

import parinexus.tmdb.movies.data.dataSources.local.entities.DbMovieEntity
import parinexus.tmdb.movies.data.dataSources.remote.models.movie.Movie
import parinexus.tmdb.movies.domain.models.DomainMovieEntity

fun DbMovieEntity.toDomainModelWithCategory(category: String): DomainMovieEntity {
    return DomainMovieEntity(
        id = id,
        category = category,
        adult = adult,
        backdropPath = backdropPath ?: "",
        genreIds = genreIds,
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


fun DbMovieEntity.toDomainMovieEntity(): DomainMovieEntity {
    return DomainMovieEntity(
        id = id,
        category = category,
        adult = adult,
        backdropPath = backdropPath ?: "",
        genreIds = genreIds,
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


fun Movie.toDbEntity(category: String): DbMovieEntity {
    return DbMovieEntity(
        id = this.id,
        category = category,
        adult = this.adult,
        backdropPath = this.backdropPath ?: "",
        genreIds = this.genreIds.joinToString(","), // join ints as "12,18,28"
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath ?: "",
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}
