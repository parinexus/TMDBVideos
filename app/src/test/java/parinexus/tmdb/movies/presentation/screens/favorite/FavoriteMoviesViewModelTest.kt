package parinexus.tmdb.movies.presentation.screens.favorite

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import parinexus.tmdb.movies.domain.models.DomainMovieEntity
import parinexus.tmdb.movies.domain.usecase.FetchFavoriteMoviesUseCase
import parinexus.tmdb.movies.mappers.toPresentation
import parinexus.tmdb.movies.utils.UiState

@ExperimentalCoroutinesApi
class FavoriteMoviesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var favoriteMovieUseCase: FetchFavoriteMoviesUseCase
    private lateinit var viewModel: FavoriteMoviesViewModel

    private val sampleDomainMovies = listOf(
        DomainMovieEntity(
            id = 1,
            category = "",
            adult = false,
            backdropPath = "/backdrop.jpg",
            genreIds = "[1,2]",
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "Overview",
            popularity = 10.0,
            posterPath = "/poster.jpg",
            releaseDate = "2023-01-01",
            title = "Title",
            video = false,
            voteAverage = 7.5,
            voteCount = 100
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        favoriteMovieUseCase = mockk()
        viewModel = FavoriteMoviesViewModel(favoriteMovieUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadFavoriteMovies emits success state with data`() = runTest {
        coEvery { favoriteMovieUseCase() } returns flow { emit(sampleDomainMovies) }

        viewModel.onEvent(FavoriteMoviesEvent.LoadFavoriteMovies)

        viewModel.state.test {
            // initial state is empty, skip it
            awaitItem()

            val state = awaitItem()
            assert(state.favoriteMovies is UiState.Success)
            val movies = (state.favoriteMovies as UiState.Success).data
            assertEquals(sampleDomainMovies.map { it.toPresentation() }, movies)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadFavoriteMovies emits error state when use case throws`() = runTest {
        val errorMessage = "Network error"

        coEvery { favoriteMovieUseCase() } returns flow {
            throw Exception(errorMessage)
        }

        viewModel.onEvent(FavoriteMoviesEvent.LoadFavoriteMovies)

        viewModel.state.test {
            // initial state
            awaitItem()

            val state = awaitItem()
            assert(state.favoriteMovies is UiState.Error)
            val error = (state.favoriteMovies as UiState.Error).message
            assertEquals(errorMessage, error)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
