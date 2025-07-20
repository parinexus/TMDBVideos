package parinexus.tmdb.movies.data.repository

import androidx.paging.ExperimentalPagingApi
import app.cash.turbine.test
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDao
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDatabase
import parinexus.tmdb.movies.data.dataSources.remote.MoviesApi
import parinexus.tmdb.movies.data.mapper.favToDomainMovieEntity
import parinexus.tmdb.movies.data.model.TestData.sampleDomainMovie
import parinexus.tmdb.movies.data.model.TestData.sampleFavoriteEntity

@ExperimentalCoroutinesApi
@OptIn(ExperimentalPagingApi::class)
class MoviesRepositoryFavoritesTest {

    private lateinit var repository: MoviesRepositoryImpl
    private val moviesApi: MoviesApi = mockk(relaxed = true)
    private val movieDatabase: MovieDatabase = mockk()
    private val movieDao: MovieDao = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { movieDatabase.movieDao } returns movieDao
        repository = MoviesRepositoryImpl(moviesApi, movieDatabase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addFavoriteMovie should insert favorite movie`() = runTest {
        coEvery { movieDao.insertFavoriteMovie(any()) } just Runs

        repository.addFavoriteMovie(sampleDomainMovie)

        coVerify(exactly = 1) { movieDao.insertFavoriteMovie(sampleFavoriteEntity) }
    }

    @Test
    fun `fetchFavoriteMovieById should return domain movie if exists`() = runTest {
        coEvery { movieDao.getFavoriteMovieById(sampleDomainMovie.id) } returns sampleFavoriteEntity

        val result = repository.fetchFavoriteMovieById(sampleDomainMovie.id)

        assertEquals(sampleDomainMovie, result)
    }

    @Test
    fun `fetchFavoriteMovieById should return null if not found`() = runTest {
        coEvery { movieDao.getFavoriteMovieById(any()) } returns null

        val result = repository.fetchFavoriteMovieById(9999)

        assertNull(result)
    }

    @Test
    fun `deleteFavoriteMovie should call dao delete with correct id`() = runTest {
        coEvery { movieDao.deleteFavoriteMovie(any()) } just Runs

        repository.deleteFavoriteMovie(sampleDomainMovie.id)

        coVerify(exactly = 1) { movieDao.deleteFavoriteMovie(sampleDomainMovie.id) }
    }

    @Test
    fun `fetchFavoriteMovies should emit list of domain movies`() = runTest {
        coEvery { movieDao.getAllFavoriteMovies() } returns listOf(sampleFavoriteEntity)

        repository.fetchFavoriteMovies().test {
            val emitted = awaitItem()
            assertEquals(listOf(sampleDomainMovie), emitted)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `check mapper consistency`() = runTest {
        val mappedBack = sampleFavoriteEntity.favToDomainMovieEntity()
        assertEquals(sampleDomainMovie, mappedBack)
    }
}