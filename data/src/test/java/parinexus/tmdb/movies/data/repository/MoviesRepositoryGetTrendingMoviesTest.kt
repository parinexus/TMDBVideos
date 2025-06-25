package parinexus.tmdb.movies.data.repository

import androidx.paging.ExperimentalPagingApi
import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDao
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDatabase
import parinexus.tmdb.movies.data.dataSources.local.entities.DbMovieEntity
import parinexus.tmdb.movies.data.dataSources.remote.MoviesApi
import parinexus.tmdb.movies.data.utils.Constant.TRENDING

@OptIn(ExperimentalPagingApi::class)
class MoviesRepositoryGetTrendingMoviesTest {

    private lateinit var repository: MoviesRepositoryImpl
    private lateinit var moviesApi: MoviesApi
    private lateinit var movieDao: MovieDao
    private lateinit var database: MovieDatabase

    @Before
    fun setup() {
        moviesApi = mockk()
        movieDao = mockk()
        database = mockk()
        every { database.movieDao } returns movieDao
        repository = MoviesRepositoryImpl(moviesApi, database)
    }

    @Test
    fun `getTrendingMovies emits movie list from DAO`() = runBlocking {
        val dbMovie = DbMovieEntity(
            id = 10,
            category = TRENDING,
            title = "Trend",
            originalTitle = "",
            overview = "",
            posterPath = "",
            backdropPath = "",
            genreIds = "",
            popularity = 0.0,
            releaseDate = "",
            video = false,
            voteAverage = 0.0,
            voteCount = 0,
            originalLanguage = "",
            adult = false
        )

        every { movieDao.getTrendingMovies() } returns flowOf(listOf(dbMovie))

        repository.getTrendingMovies().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("Trend", result[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
