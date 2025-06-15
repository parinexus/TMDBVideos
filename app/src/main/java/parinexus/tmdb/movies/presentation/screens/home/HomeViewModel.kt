package parinexus.tmdb.movies.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import parinexus.tmdb.movies.domain.usecase.GetTrendingMoviesUseCase
import parinexus.tmdb.movies.domain.usecase.RefreshTrendingMoviesUseCase
import parinexus.tmdb.movies.mappers.toPresentation
import parinexus.tmdb.movies.presentation.screens.home.arch.HomeIntent
import parinexus.tmdb.movies.presentation.screens.home.arch.HomeState
import parinexus.tmdb.movies.utils.UiState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val refreshTrendingMoviesUseCase: RefreshTrendingMoviesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadTrendingMovies -> getTrendingMovies()
        }
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(trendingMovies = UiState.Loading) }

            try {
                refreshTrendingMoviesUseCase()

                getTrendingMoviesUseCase().collect { trendingMoviesList ->
                    _uiState.update {
                        it.copy(trendingMovies = UiState.Success(trendingMoviesList.map { movieItem ->
                            movieItem.toPresentation()
                        }))
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(trendingMovies = UiState.Error(e.message ?: "Unknown error"))
                }
            }
        }
    }
}
