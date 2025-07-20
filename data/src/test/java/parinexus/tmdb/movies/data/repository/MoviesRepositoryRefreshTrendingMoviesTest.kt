package parinexus.tmdb.movies.data.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDatabase
import parinexus.tmdb.movies.data.dataSources.remote.MoviesApi
import parinexus.tmdb.movies.data.dataSources.remote.models.movie.Movie
import parinexus.tmdb.movies.data.dataSources.remote.models.movie.MoviesResponse
import parinexus.tmdb.movies.data.utils.Constant.TRENDING
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@OptIn(ExperimentalPagingApi::class)
@Config(sdk = [28])
@RunWith(RobolectricTestRunner::class)
class MoviesRepositoryRefreshTrendingMoviesTest {

    private lateinit var repository: MoviesRepositoryImpl
    private lateinit var moviesApi: MoviesApi
    private lateinit var database: MovieDatabase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.getApplication(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        moviesApi = mockk()
        repository = MoviesRepositoryImpl(moviesApi, database)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun `refreshTrendingMovies clears and inserts movies from API`() = runTest {
        val apiMovie = Movie(
            id = 12,
            title = "Test",
            originalTitle = "Test",
            overview = "Overview",
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            genreIds = listOf(1, 2),
            popularity = 8.5,
            releaseDate = "2023-01-01",
            video = false,
            voteAverage = 7.5,
            voteCount = 100,
            mediaType = "",
            originalLanguage = "en",
            adult = false
        )

        val apiResponse = MoviesResponse(
            page = 1,
            results = listOf(apiMovie),
            totalPages = 1,
            totalResults = 1
        )

        coEvery { moviesApi.getTrendingMovies(any()) } returns apiResponse

        repository.refreshTrendingMovies()

        val movies = database.movieDao.getMoviesListByCategory(TRENDING).load(
            androidx.paging.PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        if (movies is androidx.paging.PagingSource.LoadResult.Page) {
            assertEquals(1, movies.data.size)
            assertEquals("Test", movies.data[0].title)
        } else {
            throw AssertionError("Expected PagingSource.LoadResult.Page but got $movies")
        }
    }
}
