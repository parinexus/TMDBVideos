package parinexus.tmdb.movies.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import parinexus.tmdb.movies.domain.usecase.GetMoviesByCategoryUseCase
import parinexus.tmdb.movies.domain.usecase.GetTrendingMoviesUseCase
import parinexus.tmdb.movies.domain.usecase.RefreshTrendingMoviesUseCase
import parinexus.tmdb.movies.mappers.asPresentationFlow
import parinexus.tmdb.movies.mappers.toPresentation
import parinexus.tmdb.movies.models.MovieCategory
import parinexus.tmdb.movies.models.PresentationMovieEntity
import parinexus.tmdb.movies.presentation.screens.home.arch.HomeIntent
import parinexus.tmdb.movies.presentation.screens.home.arch.HomeState
import parinexus.tmdb.movies.utils.UiState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val refreshTrendingMoviesUseCase: RefreshTrendingMoviesUseCase,
    private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.FetchTrendingMovies -> {
                if (_uiState.value.trendingMoviesState !is UiState.Success) {
                    getTrendingMovies()
                }
            }
            is HomeIntent.FetchMoviesByCategory -> {
                val categoryState = when (intent.category) {
                    MovieCategory.Popular -> _uiState.value.popularMoviesState
                    MovieCategory.TopRated -> _uiState.value.topPicksMovies
                    MovieCategory.Upcoming -> _uiState.value.comingSoonMoviesState
                }
                if (categoryState !is UiState.Success) {
                    getMoviesByCategory(intent.category)
                }
            }
        }
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(trendingMoviesState = UiState.Loading) }

            try {
                refreshTrendingMoviesUseCase()

                getTrendingMoviesUseCase().collect { trendingMoviesList ->
                    _uiState.update {
                        it.copy(trendingMoviesState = UiState.Success(trendingMoviesList.map { movieItem ->
                            movieItem.toPresentation()
                        }))
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(trendingMoviesState = UiState.Error(e.message ?: "Unknown error"))
                }
            }
        }
    }

    private fun getMoviesByCategory(category: MovieCategory) {
        viewModelScope.launch {
            updateCategoryState(category = category, state = UiState.Loading)
            try {
                val popularMovies =
                    getMoviesByCategoryUseCase(category.apiConst).cachedIn(viewModelScope)
                updateCategoryState(
                    category = category,
                    state = UiState.Success(popularMovies.asPresentationFlow())
                )
            } catch (e: Exception) {
                updateCategoryState(
                    category = category,
                    state = UiState.Error(e.message.toString())
                )
            }
        }
    }

    private fun updateCategoryState(
        category: MovieCategory,
        state: UiState<Flow<PagingData<PresentationMovieEntity>>>
    ) = setState {
        when (category) {
            MovieCategory.Popular -> copy(popularMoviesState = state)

            MovieCategory.TopRated -> copy(topPicksMovies = state)

            MovieCategory.Upcoming -> copy(comingSoonMoviesState = state)
        }
    }

    private inline fun setState(reducer: HomeState.() -> HomeState) {
        _uiState.update(reducer)
    }
}
