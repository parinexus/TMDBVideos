package parinexus.tmdb.movies.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import parinexus.tmdb.movies.common.FinderBar
import parinexus.tmdb.movies.data.utils.Constant.POPULAR
import parinexus.tmdb.movies.data.utils.Constant.TOP_RATED
import parinexus.tmdb.movies.data.utils.Constant.UPCOMING
import parinexus.tmdb.movies.models.MovieCategory
import parinexus.tmdb.movies.models.PresentationMovieEntity
import parinexus.tmdb.movies.presentation.screens.home.arch.HomeIntent
import parinexus.tmdb.movies.presentation.screens.home.components.HorizontalPosterSection
import parinexus.tmdb.movies.presentation.screens.home.components.Section
import parinexus.tmdb.movies.presentation.screens.home.components.TrendingFlicksDisplay

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onClickSearchBar: () -> Unit,
    navigateToDetails: (PresentationMovieEntity) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(HomeIntent.FetchTrendingMovies)
        viewModel.handleIntent(HomeIntent.FetchMoviesByCategory(MovieCategory.Popular))
        viewModel.handleIntent(HomeIntent.FetchMoviesByCategory(MovieCategory.Upcoming))
        viewModel.handleIntent(HomeIntent.FetchMoviesByCategory(MovieCategory.TopRated))
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 46.dp, bottom = 60.dp)
    ) {
        Text(
            text = "Let's find something awesome to watch!",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
        )

        FinderBar(
            isReadOnly = true,
            onClick = { onClickSearchBar() }
        )

        Spacer(modifier = Modifier.height(20.dp))


        TrendingFlicksDisplay(uiState.trendingMoviesState)


        Spacer(modifier = Modifier.height(8.dp))

        Section(title = "Most Watched") {
            HorizontalPosterSection(POPULAR, uiState.popularMoviesState) { movie ->
                navigateToDetails(movie)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Section(title = "Next in Line") {
            HorizontalPosterSection(UPCOMING, uiState.comingSoonMoviesState)  { movie ->
                navigateToDetails(movie)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Section(title = "Top Picks") {
            HorizontalPosterSection(TOP_RATED, uiState.topPicksMovies)  { movie ->
                navigateToDetails(movie)
            }
        }


    }

}