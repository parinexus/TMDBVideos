package parinexus.tmdb.movies.common

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import parinexus.tmdb.movies.R
import parinexus.tmdb.movies.data.BuildConfig.BASE_IMAGE_URL
import parinexus.tmdb.movies.models.PresentationMovieEntity

@Composable
fun InfiniteImageScroller(
    images: List<PresentationMovieEntity>,
    modifier: Modifier = Modifier,
    slideDuration: Long = 3000L,
) {
    if (images.isEmpty()) return

    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(images.size) {
        if (images.size > 1) {
            while (true) {
                delay(slideDuration)
                currentIndex = (currentIndex + 1) % images.size
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = modifier) {
            val imageUrl = BASE_IMAGE_URL + (images.getOrNull(currentIndex)?.posterPath.orEmpty())
            val imagePainter = rememberAsyncImagePainter(
                model = imageUrl,
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder)
            )

            Image(
                painter = imagePainter,
                contentDescription = images.getOrNull(currentIndex)?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 300f
                        )
                    )
            )

            Text(
                text = images.getOrNull(currentIndex)?.title.orEmpty(),
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
        if (images.size > 1) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                images.forEachIndexed { index, _ ->
                    val selected = currentIndex == index
                    val size by animateDpAsState(
                        targetValue = if (selected) 12.dp else 8.dp, label = "IndicatorSize"
                    )
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(size)
                            .clip(CircleShape)
                            .background(if (selected) Color.White else Color.Gray)
                    )
                }
            }
        }
    }
}