package parinexus.tmdb.movies.presentation.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import parinexus.tmdb.movies.R
import parinexus.tmdb.movies.models.PresentationMovieEntity
import parinexus.tmdb.movies.presentation.screens.details.components.CastSection
import parinexus.tmdb.movies.presentation.screens.details.components.PosterAndInfoCard
import parinexus.tmdb.movies.presentation.screens.details.components.TextSection

@Composable
fun MovieDetailsScreen(
    movie: PresentationMovieEntity,
    viewModel: MovieDetailsViewModel,
    onBackClick: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.handleIntent(MovieDetailsIntent.LoadFavoriteStatus(movie))
        viewModel.handleIntent(MovieDetailsIntent.FetchCast(movie.id))
    }

    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.blackBackground)),
        contentPadding = PaddingValues(16.dp),
    ) {
        item {
            PosterAndInfoCard(movie, uiState, onBackClick) {
                viewModel.handleIntent(
                    MovieDetailsIntent.SetFavoriteStatus(movie, !uiState.isFavorite)
                )
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item {
            TextSection("Overview", movie.overview)
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item {
            CastSection(uiState)
        }
    }

    if (uiState.fetchFavoriteError.isNotBlank()) {
        Text(
            text = uiState.fetchFavoriteError,
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }

    if (uiState.toggleFavoriteError.isNotBlank()) {
        Text(
            text = uiState.toggleFavoriteError,
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}
