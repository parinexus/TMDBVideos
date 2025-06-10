package parinexus.tmdb.movies.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDatabase
import parinexus.tmdb.movies.domain.models.MovieEntity
import parinexus.tmdb.movies.data.dataSources.remote.MoviesApi
import parinexus.tmdb.movies.data.mapper.toMovieEntity
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
import parinexus.tmdb.movies.domain.repository.MoviesRepository

@ExperimentalPagingApi
class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi,
    private val movieDatabase: MovieDatabase,
) : MoviesRepository {

    override fun fetchTrendingMovies(): Flow<List<MovieEntity>> = channelFlow {
        val trendingMovies = moviesApi.getTrendingMovies(1).results

        movieDatabase.withTransaction {

            val listMovieEntity = trendingMovies.map { movieDto ->
                movieDto.toMovieEntity(TRENDING)
            }

            // insert trending movies to database
            movieDatabase.movieDao.upsertMoviesList(movies = listMovieEntity)

            val listTrendingMovies = movieDatabase.movieDao.getTrendingMovies()

            send(listTrendingMovies)
        }
    }


    override fun fetchMoviesByCategory(
        category: String,
    ): Flow<PagingData<MovieEntity>> {
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
        ).flow
    }
}
