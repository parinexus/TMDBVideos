package parinexus.tmdb.movies.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDatabase
import parinexus.tmdb.movies.data.dataSources.local.entities.DbMovieEntity
import parinexus.tmdb.movies.data.dataSources.remote.MoviesApi

@OptIn(ExperimentalPagingApi::class)
class UpcomingMoviesPagingMediator(moviesApi: MoviesApi, movieDatabase: MovieDatabase):
    RemoteMediator<Int, DbMovieEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DbMovieEntity>
    ): MediatorResult {
        TODO("Not yet implemented")
    }

}
