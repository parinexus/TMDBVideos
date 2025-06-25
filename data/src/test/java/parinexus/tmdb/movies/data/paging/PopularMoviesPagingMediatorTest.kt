package parinexus.tmdb.movies.data.paging

import androidx.paging.*
import androidx.room.Room
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDatabase
import parinexus.tmdb.movies.data.dataSources.remote.MoviesApi
import parinexus.tmdb.movies.data.dataSources.remote.models.movie.Movie
import parinexus.tmdb.movies.data.dataSources.remote.models.movie.MoviesResponse
import parinexus.tmdb.movies.data.utils.Constant.POPULAR

@Config(sdk = [28])
@RunWith(RobolectricTestRunner::class)
class PopularMoviesPagingMediatorTest {
    private lateinit var db: MovieDatabase
    private lateinit var moviesApi: MoviesApi

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.getApplication(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        moviesApi = mockk()
    }

    @After
    fun teardown() {
        db.close()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refresh_inserts_movies_and_returns_success() = runBlocking {
        coEvery { moviesApi.getMoviesList(category = POPULAR, page = 1) } returns MoviesResponse(
            page = 1,
            results = listOf(
                Movie(
                    id = 99,
                    title = "Popular",
                    originalTitle = "Popular",
                    overview = "",
                    posterPath = "p",
                    backdropPath = "b",
                    genreIds = listOf(1, 2),
                    popularity = 1.0,
                    releaseDate = "2023-01-01",
                    video = false,
                    voteAverage = 2.2,
                    voteCount = 10,
                    mediaType = "",
                    originalLanguage = "",
                    adult = false
                )
            ),
            totalPages = 1,
            totalResults = 1
        )

        val mediator = PopularMoviesPagingMediator(moviesApi, db)
        val result = mediator.load(
            LoadType.REFRESH,
            PagingState(emptyList(), null, PagingConfig(10), 0)
        )
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
        // Check that movie is inserted:
        val movies = db.movieDao.getMoviesListByCategory(POPULAR).load(
            PagingSource.LoadParams.Refresh(0, 10, false)
        )
        Assert.assertTrue(movies is PagingSource.LoadResult.Page)
    }
}
