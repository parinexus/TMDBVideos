package parinexus.tmdb.movies.presentation.screens.home

import app.cash.turbine.test
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import parinexus.tmdb.movies.domain.models.DomainMovieEntity
import parinexus.tmdb.movies.domain.usecase.GetMoviesByCategoryUseCase
import parinexus.tmdb.movies.domain.usecase.GetTrendingMoviesUseCase
import parinexus.tmdb.movies.domain.usecase.RefreshTrendingMoviesUseCase
import parinexus.tmdb.movies.mappers.toPresentation
import parinexus.tmdb.movies.presentation.screens.home.arch.HomeIntent
import parinexus.tmdb.movies.utils.UiState

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var getTrendingMoviesUseCase: GetTrendingMoviesUseCase
    private lateinit var refreshTrendingMoviesUseCase: RefreshTrendingMoviesUseCase
    private lateinit var getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        getTrendingMoviesUseCase = mockk()
        refreshTrendingMoviesUseCase = mockk()
        getMoviesByCategoryUseCase = mockk()
        viewModel = HomeViewModel(
            getTrendingMoviesUseCase,
            refreshTrendingMoviesUseCase,
            getMoviesByCategoryUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `initial state is Loading for both trending and popular movies`() = runTest {
        val state = viewModel.uiState.value
        assert(state.trendingMoviesState is UiState.Loading)
        assert(state.popularMoviesState is UiState.Loading)
    }

    @Test
    fun `handleIntent FetchTrendingMovies triggers trending loading and success`() = runTest {

        val domainMovieList = listOf(
            DomainMovieEntity(
                id = 1,
                category = "Action",
                adult = false,
                backdropPath = "/example_backdrop.jpg",
                genreIds = "28,12,16",
                originalLanguage = "en",
                originalTitle = "Original Movie Title",
                overview = "This is the overview of the movie.",
                popularity = 87.5,
                posterPath = "/example_poster.jpg",
                releaseDate = "2023-12-10",
                title = "Awesome Movie",
                video = false,
                voteAverage = 8.2,
                voteCount = 1934
            )
        )
        val presentationMovieList = domainMovieList.map { it.toPresentation() }

        coEvery { refreshTrendingMoviesUseCase.invoke() } just Runs
        coEvery { getTrendingMoviesUseCase.invoke() } returns flowOf(domainMovieList)

        viewModel.uiState.test {
            viewModel.handleIntent(HomeIntent.FetchTrendingMovies)
            advanceUntilIdle()

            val state = expectMostRecentItem()
            assert(state.trendingMoviesState is UiState.Success)
            val movies = (state.trendingMoviesState as UiState.Success).data
            assert(movies == presentationMovieList)
        }
    }

    @Test
    fun `handleIntent FetchTrendingMovies triggers error state if exception thrown`() = runTest {
        val exceptionMessage = "Network error"
        coEvery { refreshTrendingMoviesUseCase.invoke() } just Runs
        coEvery { getTrendingMoviesUseCase.invoke() } throws RuntimeException(exceptionMessage)

        viewModel.uiState.test {
            viewModel.handleIntent(HomeIntent.FetchTrendingMovies)
            advanceUntilIdle()

            val state = expectMostRecentItem()
            assert(state.trendingMoviesState is UiState.Error)
            assert((state.trendingMoviesState as UiState.Error).message.contains(exceptionMessage))
        }
    }

    @Test
    fun `handleIntent FetchPopularMovies triggers popular loading and success`() = runTest {
        val fakePagingDataFlow =
            flowOf(mockk<androidx.paging.PagingData<DomainMovieEntity>>())
        coEvery { getMoviesByCategoryUseCase(any()) } returns fakePagingDataFlow

        viewModel.uiState.test {
            viewModel.handleIntent(HomeIntent.FetchPopularMovies)
            advanceUntilIdle()

            val state = expectMostRecentItem()
            assert(state.popularMoviesState is UiState.Success)
        }
    }

    @Test
    fun `handleIntent FetchPopularMovies triggers error state if exception thrown`() = runTest {
        val exceptionMessage = "Service error"
        coEvery { getMoviesByCategoryUseCase(any()) } throws RuntimeException(exceptionMessage)

        viewModel.uiState.test {
            viewModel.handleIntent(HomeIntent.FetchPopularMovies)
            advanceUntilIdle()

            val state = expectMostRecentItem()
            assert(state.popularMoviesState is UiState.Error)
            assert((state.popularMoviesState as UiState.Error).message.contains(exceptionMessage))
        }
    }
}