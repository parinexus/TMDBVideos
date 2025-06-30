package parinexus.tmdb.movies.data.mapper

import parinexus.tmdb.movies.data.dataSources.local.entities.DbMovieEntity
import parinexus.tmdb.movies.data.dataSources.local.entities.FavoriteMovieEntity
import parinexus.tmdb.movies.data.dataSources.local.entities.MovieCastEntity
import parinexus.tmdb.movies.data.dataSources.remote.models.movie.Movie
import parinexus.tmdb.movies.data.dataSources.remote.models.movieCast.MovieCastDto
import parinexus.tmdb.movies.domain.models.DomainMovieCast
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

fun MovieCastEntity.toDomainMovieCast(): DomainMovieCast {
    return DomainMovieCast(
        isAdult = adult,
        castId = castId,
        character = character,
        creditId = creditId,
        gender = gender,
        id = id,
        department = knownForDepartment,
        name = name,
        order = order,
        originalName = originalName,
        popularity = popularity,
        profilePath = profilePath
    )
}

fun MovieCastDto.toMovieCastEntity(
    movieId: Int,
): MovieCastEntity {
    return MovieCastEntity(
        adult = adult,
        castId = castId,
        character = character,
        creditId = creditId,
        gender = gender,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        order = order,
        originalName = originalName,
        popularity = popularity,
        profilePath = profilePath ?: "",
        movieId = movieId
    )
}

fun FavoriteMovieEntity.favToDomainMovieEntity(): DomainMovieEntity {
    return DomainMovieEntity(
        adult = adult,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        id = id,
        originalTitle = originalTitle,
        video = video,

        genreIds = try {
            genreIds.split(",").map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        }.toString(),

        category = "",
    )
}

fun DomainMovieEntity.domainToFavMovieEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        adult = adult,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        id = id,
        originalTitle = originalTitle,
        video = video,

        genreIds = try {
            genreIds.split(",").map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        }.toString(),
    )
}
