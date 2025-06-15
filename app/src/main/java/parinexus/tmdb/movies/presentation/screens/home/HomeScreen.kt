package parinexus.tmdb.movies.presentation.screens.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import parinexus.tmdb.movies.models.PresentationMovieEntity
import parinexus.tmdb.movies.presentation.screens.home.arch.HomeIntent

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onClickSearchBar: () -> Unit,
    navigateToDetails: (PresentationMovieEntity) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(HomeIntent.LoadTrendingMovies)
    }

    Log.d("TAG", "HomeScreen: "+uiState.trendingMovies.toString())

}