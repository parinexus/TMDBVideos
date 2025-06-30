package parinexus.tmdb.movies.presentation.screens.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import parinexus.tmdb.movies.presentation.screens.details.MovieDetailsUiState
import parinexus.tmdb.movies.utils.UiState
import parinexus.tmdb.movies.data.BuildConfig.BASE_IMAGE_URL
import parinexus.tmdb.movies.models.PresentationMovieCast

@Composable
fun CastSection(uiState: MovieDetailsUiState) {
    Text(
        text = "Cast",
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    )

    Spacer(modifier = Modifier.height(8.dp))

    when (val state = uiState.cast) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                items(state.data.orEmpty()) { cast ->
                    CastCard(cast)
                }
            }
        }

        is UiState.Error -> {
            Text(
                text = "Failed to load cast.",
                color = Color.Gray,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun CastCard(cast: PresentationMovieCast) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .padding(end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = BASE_IMAGE_URL + cast.profilePath,
            contentDescription = cast.name,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = cast.name,
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}
