package parinexus.tmdb.movies.presentation.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import parinexus.tmdb.movies.domain.usecase.FetchFavoriteMoviesUseCase
import parinexus.tmdb.movies.mappers.toPresentation
import parinexus.tmdb.movies.utils.UiState
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val favoriteMovieUseCase: FetchFavoriteMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteMoviesState())
    val state: StateFlow<FavoriteMoviesState> = _state

    fun onEvent(event: FavoriteMoviesEvent) {
        when (event) {
            is FavoriteMoviesEvent.LoadFavoriteMovies -> loadFavoriteMovies()
        }
    }

    private fun loadFavoriteMovies() {
        viewModelScope.launch {
            try {
                favoriteMovieUseCase().collect { movies ->
                    val presentationMovies = movies.map { it.toPresentation() }
                    _state.update {
                        it.copy(favoriteMovies = UiState.Success(presentationMovies))
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(favoriteMovies = UiState.Error(e.message ?: "Unknown error"))
                }
            }
        }
    }
}
