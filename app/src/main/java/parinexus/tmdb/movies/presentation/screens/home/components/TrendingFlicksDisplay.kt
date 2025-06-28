package parinexus.tmdb.movies.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import parinexus.tmdb.movies.R
import parinexus.tmdb.movies.common.InfiniteImageScroller
import parinexus.tmdb.movies.models.PresentationMovieEntity
import parinexus.tmdb.movies.utils.UiState

@Composable
fun TrendingFlicksDisplay(uiState: UiState<List<PresentationMovieEntity>>) {
    when (uiState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            uiState.data.orEmpty().takeIf { it.isNotEmpty() }?.let { movies->
                DisplayHomeSlider(movies)
            }
        }

        is UiState.Error -> {
            ErrorTrendingMovies()
        }
    }
}

@Composable
private fun ErrorTrendingMovies() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF2F2F39)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.placeholder),
            contentDescription = "Placeholder",
            modifier = Modifier
                .size(100.dp)
                .alpha(0.4f)
        )
    }
}


@Composable
fun DisplayHomeSlider(listMovies: List<PresentationMovieEntity>) {
    Column {
        InfiniteImageScroller(
            images = listMovies.take(6), modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(8.dp)
        )
    }
}


