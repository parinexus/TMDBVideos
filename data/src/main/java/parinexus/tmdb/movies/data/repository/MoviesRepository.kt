package parinexus.tmdb.movies.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDatabase
import parinexus.tmdb.movies.data.dataSources.local.entities.DbMovieEntity
import parinexus.tmdb.movies.domain.models.DomainMovieEntity
import parinexus.tmdb.movies.data.dataSources.remote.MoviesApi
import parinexus.tmdb.movies.data.mapper.domainToFavMovieEntity
import parinexus.tmdb.movies.data.mapper.favToDomainMovieEntity
import parinexus.tmdb.movies.data.mapper.toDbEntity
import parinexus.tmdb.movies.data.mapper.toDomainMovieEntity
import parinexus.tmdb.movies.data.mapper.toDomainModelWithCategory
import parinexus.tmdb.movies.data.mapper.toDomainMovieCast
import parinexus.tmdb.movies.data.mapper.toMovieCastEntity
import parinexus.tmdb.movies.data.paging.DiscoverMoviesPagingMediator
import parinexus.tmdb.movies.data.paging.NowPlayingMoviesPagingMediator
import parinexus.tmdb.movies.data.paging.PopularMoviesPagingMediator
import parinexus.tmdb.movies.data.paging.TopRatedMoviesPagingMediator
import parinexus.tmdb.movies.data.paging.UpcomingMoviesPagingMediator
import parinexus.tmdb.movies.data.utils.Constant.DISCOVER
import parinexus.tmdb.movies.data.utils.Constant.NOW_PLAYING
import parinexus.tmdb.movies.data.utils.Constant.POPULAR
import parinexus.tmdb.movies.data.utils.Constant.TOP_RATED
import parinexus.tmdb.movies.data.utils.Constant.TRENDING
import parinexus.tmdb.movies.data.utils.Constant.UPCOMING
import parinexus.tmdb.movies.domain.models.DomainMovieCast
import parinexus.tmdb.movies.domain.repository.MoviesRepository

@ExperimentalPagingApi
class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi,
    private val movieDatabase: MovieDatabase,
) : MoviesRepository {

    override fun getTrendingMovies(): Flow<List<DomainMovieEntity>> {
        return movieDatabase.movieDao.getTrendingMovies()
            .map { list -> list.map { it.toDomainModelWithCategory(TRENDING) } }
    }

    override suspend fun refreshTrendingMovies() {
        val trendingDtos = moviesApi.getTrendingMovies(1).results
        val entities = trendingDtos.map { it.toDbEntity(TRENDING) }

        movieDatabase.withTransaction {
            movieDatabase.movieDao.clearMoviesByCategory(TRENDING)
            movieDatabase.movieDao.insertMoviesList(entities)
        }
    }


    override fun fetchMoviesByCategory(
        category: String,
    ): Flow<PagingData<DomainMovieEntity>> {
        val pagingSourceFactory = { movieDatabase.movieDao.getMoviesListByCategory(category) }

        val mediator = when (category) {
            POPULAR -> PopularMoviesPagingMediator(moviesApi, movieDatabase)
            TOP_RATED -> TopRatedMoviesPagingMediator(moviesApi, movieDatabase)
            UPCOMING -> UpcomingMoviesPagingMediator(moviesApi, movieDatabase)
            DISCOVER -> DiscoverMoviesPagingMediator(moviesApi, movieDatabase)
            NOW_PLAYING -> NowPlayingMoviesPagingMediator(moviesApi, movieDatabase)
            else -> throw IllegalArgumentException("Unknown category: $category")
        }

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = mediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData: PagingData<DbMovieEntity> ->
            pagingData.map { it.toDomainMovieEntity() }
        }
    }


    override fun fetchMovieCastList(movieId: Int): Flow<List<DomainMovieCast>> = channelFlow {
        val movieCast = moviesApi.getMovieCast(movieId).cast

        movieDatabase.withTransaction {

            val listMovieCastEntity = movieCast.map { movieCastDto ->
                movieCastDto.toMovieCastEntity(movieId)
            }

            movieDatabase.movieDao.upsertMovieCastList(castList = listMovieCastEntity)

            val listMovieCast = movieDatabase.movieDao.getMovieCastById(movieId)

            send(listMovieCast.map { it.toDomainMovieCast() })
        }
    }

    override suspend fun fetchFavoriteMovieById(movieId: Int): DomainMovieEntity? {
        return movieDatabase.movieDao.getFavoriteMovieById(movieId)?.favToDomainMovieEntity()
    }

    override suspend fun addFavoriteMovie(movie: DomainMovieEntity) {
        movieDatabase.movieDao.insertFavoriteMovie(movie.domainToFavMovieEntity())
    }

    override suspend fun deleteFavoriteMovie(movieId: Int) {
        movieDatabase.movieDao.deleteFavoriteMovie(movieId)
    }

    override fun fetchFavoriteMovies(): Flow<List<DomainMovieEntity>> {
        return flow {
            val favoriteMovies = movieDatabase.movieDao.getAllFavoriteMovies()
            emit(favoriteMovies.map { it.favToDomainMovieEntity() })
        }
    }
}
