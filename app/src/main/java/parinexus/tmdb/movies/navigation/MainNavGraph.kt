package parinexus.tmdb.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import parinexus.tmdb.movies.common.SmartNavBarVisibility
import parinexus.tmdb.movies.presentation.screens.intro.IntroScreen
import parinexus.tmdb.movies.presentation.screens.splash.SplashScreen


@Composable
fun MainNavGraph() {
    val navController = rememberNavController()

    // Hide navigation bar
    SmartNavBarVisibility()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {

        composable(route = Screen.SplashScreen.route) { SplashScreen(navController) }
        composable(route = Screen.IntroScreen.route) { IntroScreen(navController) }
        composable(route = Screen.ContentNavGraph.route) { ContentNavGraph() }

    }
}