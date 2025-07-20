package parinexus.tmdb.movies.presentation.screens.favorite.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import parinexus.tmdb.movies.models.PresentationMovieEntity

@Composable
fun FavoritesListSection(
    favoriteMovies: List<PresentationMovieEntity>,
    onMovieClick: (PresentationMovieEntity) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(favoriteMovies) { movie ->
            MovieCard(
                movie = movie,
                onItemClick = onMovieClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
