package parinexus.tmdb.movies.presentation.screens.home.arch

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import parinexus.tmdb.movies.models.PresentationMovieEntity
import parinexus.tmdb.movies.utils.UiState

data class HomeState(
    val trendingMoviesState: UiState<List<PresentationMovieEntity>> = UiState.Loading,
    val popularMoviesState: UiState<Flow<PagingData<PresentationMovieEntity>>> = UiState.Loading,
)
