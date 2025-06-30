package parinexus.tmdb.movies.mappers

import parinexus.tmdb.movies.domain.models.DomainMovieEntity
import parinexus.tmdb.movies.models.PresentationMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.paging.PagingData
import androidx.paging.map
import parinexus.tmdb.movies.domain.models.DomainMovieCast
import parinexus.tmdb.movies.models.PresentationMovieCast

fun DomainMovieEntity.toPresentation(): PresentationMovieEntity {
    return PresentationMovieEntity(
        id = id,
        category = category,
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

fun PresentationMovieEntity.toDomainMovieEntity(): DomainMovieEntity {
    return DomainMovieEntity(
        id = id,
        category = category,
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

fun DomainMovieCast.toPresentationMovieCast(): PresentationMovieCast {
    return PresentationMovieCast(
        isAdult = this.isAdult,
        castId = this.castId,
        character = this.character,
        creditId = this.creditId,
        gender = this.gender,
        id = this.id,
        department = this.department,
        name = this.name,
        order = this.order,
        originalName = this.originalName,
        popularity = this.popularity,
        profilePath = this.profilePath
    )
}

fun Flow<PagingData<DomainMovieEntity>>.asPresentationFlow(): Flow<PagingData<PresentationMovieEntity>> =
    this.map { pagingData ->
        pagingData.map { domainMovie ->
            domainMovie.toPresentation()
        }
    }
