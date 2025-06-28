package parinexus.tmdb.movies.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDatabase
import parinexus.tmdb.movies.data.dataSources.local.entities.DbMovieEntity
import parinexus.tmdb.movies.data.dataSources.remote.MoviesApi
import parinexus.tmdb.movies.data.mapper.toDbEntity
import parinexus.tmdb.movies.data.utils.Constant.UPCOMING
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UpcomingMoviesPagingMediator(
    private val moviesApi: MoviesApi,
    private val movieDatabase: MovieDatabase,
) : RemoteMediator<Int, DbMovieEntity>() {

    private val movieDao = movieDatabase.movieDao
    private var currentPage = 1
    private var totalPages = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DbMovieEntity>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                if (currentPage >= totalPages) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                currentPage + 1
            }
        }

        return try {
            val response = moviesApi.getMoviesList(category = UPCOMING, page = page)

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.clearMoviesByCategory(UPCOMING)
                }

                val movieEntities = response.results.map { it.toDbEntity(UPCOMING) }
                movieDao.insertMoviesList(movieEntities)
            }

            currentPage = page
            totalPages = response.totalPages

            MediatorResult.Success(endOfPaginationReached = currentPage >= totalPages)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
