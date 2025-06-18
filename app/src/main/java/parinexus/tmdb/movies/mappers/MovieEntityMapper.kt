package parinexus.tmdb.movies.mappers

import parinexus.tmdb.movies.domain.models.DomainMovieEntity
import parinexus.tmdb.movies.models.PresentationMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.paging.PagingData
import androidx.paging.map

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

fun Flow<PagingData<DomainMovieEntity>>.asPresentationFlow(): Flow<PagingData<PresentationMovieEntity>> =
    this.map { pagingData ->
        pagingData.map { domainMovie ->
            domainMovie.toPresentation()
        }
    }
