package parinexus.tmdb.movies.data.dataSources.local.database

import androidx.paging.PagingSource
import androidx.room.Room
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import parinexus.tmdb.movies.data.model.createTestDbMovieEntity
import parinexus.tmdb.movies.data.utils.Constant.TRENDING

@Config(sdk = [28])
@RunWith(RobolectricTestRunner::class)
class MovieDaoTest {

    private lateinit var db: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.getApplication(),
            MovieDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        movieDao = db.movieDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndGetMoviesList() = runTest {
        val movie = createTestDbMovieEntity(
            id = 1,
            title = "Title",
            category = "popular",
        )
        movieDao.insertMoviesList(listOf(movie))

        val pagingSource = movieDao.getMoviesList()
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        val data = (loadResult as PagingSource.LoadResult.Page).data
        assertEquals(1, data.size)
        assertEquals("Title", data[0].title)
    }

    @Test
    fun getMovieById_returnsCorrectMovie() = runTest {
        val movie = createTestDbMovieEntity(id = 10, title = "Test", category = "popular")
        movieDao.insertMoviesList(listOf(movie))
        val result = movieDao.getMovieById(10)
        assertEquals("Test", result.title)
    }

    @Test
    fun getMoviesListByCategory_filtersCorrectly() = runTest {
        val movies = listOf(
            createTestDbMovieEntity(id = 1, title = "A", category = "popular"),
            createTestDbMovieEntity(id = 2, title = "B", category = "trending")
        )
        movieDao.insertMoviesList(movies)
        val pagingSource = movieDao.getMoviesListByCategory("popular")
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )
        val data = (loadResult as PagingSource.LoadResult.Page).data
        assertEquals(1, data.size)
        assertEquals("A", data[0].title)
    }

    @Test
    fun getTrendingMovies_returnsFlow() = runTest {
        val trendingMovie = createTestDbMovieEntity(id = 5, title = "Trend", category = TRENDING)
        movieDao.insertMoviesList(listOf(trendingMovie))
        val result = movieDao.getTrendingMovies().first()
        assertTrue(result.any { it.id == 5 })
    }

    @Test
    fun clearMoviesByCategory_deletesOnlyThatCategory() = runTest {
        val movies = listOf(
            createTestDbMovieEntity(id = 1, title = "A", category = "popular"),
            createTestDbMovieEntity(id = 2, title = "B", category = "trending")
        )
        movieDao.insertMoviesList(movies)
        movieDao.clearMoviesByCategory("popular")
        val all = movieDao.getMoviesList().load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 10, placeholdersEnabled = false)
        )
        val data = (all as PagingSource.LoadResult.Page).data
        assertEquals(1, data.size)
        assertEquals("B", data[0].title)
    }
}
