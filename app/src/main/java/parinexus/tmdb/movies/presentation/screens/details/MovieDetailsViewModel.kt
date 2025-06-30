package parinexus.tmdb.movies.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import parinexus.tmdb.movies.domain.repository.MoviesRepository
import parinexus.tmdb.movies.domain.usecase.FetchMovieCastUseCase
import parinexus.tmdb.movies.mappers.toDomainMovieEntity
import parinexus.tmdb.movies.mappers.toPresentationMovieCast
import parinexus.tmdb.movies.models.PresentationMovieEntity
import parinexus.tmdb.movies.utils.UiState
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieUseCase: FetchMovieCastUseCase,
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState: StateFlow<MovieDetailsUiState> = _uiState

    fun handleIntent(intent: MovieDetailsIntent) {
        when (intent) {
            is MovieDetailsIntent.LoadFavoriteStatus -> checkFavoriteMovie(intent.movie)
            is MovieDetailsIntent.SetFavoriteStatus -> toggleFavorite(
                intent.movie,
                intent.isFavorite
            )

            is MovieDetailsIntent.FetchCast -> fetchMovieCast(intent.movieId)
        }
    }

    private fun fetchMovieCast(movieId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(cast = UiState.Loading) }

            runCatching {
                movieUseCase(movieId)
            }.onSuccess { flow ->
                flow.collect { castList ->
                    _uiState.update {
                        it.copy(cast = UiState.Success(castList.map {
                            it.toPresentationMovieCast()
                        }))
                    }
                }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(cast = UiState.Error(e.message ?: "Error loading movie cast"))
                }
            }
        }
    }

    private fun checkFavoriteMovie(movie: PresentationMovieEntity) {
        viewModelScope.launch {
            runCatching {
                moviesRepository.fetchFavoriteMovieById(movie.id)
            }.onSuccess { result ->
                _uiState.update { it.copy(isFavorite = result != null, fetchFavoriteError = "") }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        fetchFavoriteError = e.message ?: "Error checking favorite movie"
                    )
                }
            }
        }
    }

    private fun toggleFavorite(movie: PresentationMovieEntity, favorite: Boolean) {
        viewModelScope.launch {
            runCatching {
                if (favorite) {
                    moviesRepository.addFavoriteMovie(movie.toDomainMovieEntity())
                } else {
                    moviesRepository.deleteFavoriteMovie(movie.id)
                }
            }.onSuccess {
                _uiState.update { it.copy(isFavorite = favorite, toggleFavoriteError = "") }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        toggleFavoriteError = e.message ?: "Error checking favorite movie"
                    )
                }
            }
        }
    }
}