package parinexus.tmdb.movies.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow
import parinexus.tmdb.movies.common.FeaturedPosterCard
import parinexus.tmdb.movies.common.PosterCard
import parinexus.tmdb.movies.data.utils.Constant.DISCOVER
import parinexus.tmdb.movies.models.PresentationMovieEntity
import parinexus.tmdb.movies.utils.UiState

@Composable
fun HorizontalPosterSection(
    categoryName: String,
    viewState: UiState<Flow<PagingData<PresentationMovieEntity>>>,
    onItemClick: (PresentationMovieEntity) -> Unit,
) {
    when (viewState) {
        is UiState.Loading -> ProgressIndicator()
        is UiState.Success -> viewState.data?.let { pagingDataFlow ->
            val lazyItems = pagingDataFlow.collectAsLazyPagingItems()
            PosterList(
                categoryName = categoryName,
                posterItems = lazyItems,
                onPosterClick = onItemClick
            )
        }
        is UiState.Error -> ErrorBanner(message = viewState.message)
    }
}

@Composable
fun PosterList(
    categoryName: String,
    posterItems: LazyPagingItems<PresentationMovieEntity>,
    onPosterClick: (PresentationMovieEntity) -> Unit,
) {
    if (posterItems.loadState.refresh is LoadState.Loading && posterItems.itemCount == 0) {
        ProgressIndicator()
        return
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(posterItems.itemCount) { index ->
            posterItems[index]?.let { movie ->
                if (categoryName == DISCOVER) {
                    FeaturedPosterCard(
                        movie = movie,
                        onDetailsClick = { onPosterClick(movie) }
                    )
                } else {
                    PosterCard(
                        movie = movie,
                        onCardClick = { onPosterClick(movie) }
                    )
                }
            }
        }

        if (posterItems.loadState.append is LoadState.Loading) {
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun ProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorBanner(message: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Something went wrong: $message", color = Color(0xFFD32F2F))
    }
}