package parinexus.tmdb.movies.presentation.screens.favorite

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import parinexus.tmdb.movies.R
import parinexus.tmdb.movies.models.PresentationMovieEntity
import parinexus.tmdb.movies.presentation.screens.favorite.components.FavoritesListSection
import parinexus.tmdb.movies.utils.UiState

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoriteMoviesViewModel,
    onMovieSelected: (PresentationMovieEntity) -> Unit,
) {
    val uiState by favoritesViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        favoritesViewModel.onEvent(FavoriteMoviesEvent.LoadFavoriteMovies)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        when (val movieUiState = uiState.favoriteMovies) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is UiState.Success -> {
                val favorites = movieUiState.data.orEmpty()
                if (favorites.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.movie_logo),
                            contentDescription = "No favorites",
                            modifier = Modifier
                                .size(140.dp)
                                .alpha(0.4f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No favorites yet! Start adding movies you love.",
                            fontSize = 18.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    FavoritesListSection(
                        favoriteMovies = favorites,
                        onMovieClick = onMovieSelected
                    )
                }
            }

            is UiState.Error -> {
                val context = LocalContext.current
                Toast.makeText(context, movieUiState.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
