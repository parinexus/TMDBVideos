package parinexus.tmdb.movies.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDatabase
import parinexus.tmdb.movies.domain.models.MovieEntity
import parinexus.tmdb.movies.data.dataSources.remote.MoviesApi

@OptIn(ExperimentalPagingApi::class)
class NowPlayingMoviesPagingMediator(moviesApi: MoviesApi, movieDatabase: MovieDatabase) :
    RemoteMediator<Int, MovieEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        TODO("Not yet implemented")
    }

}

