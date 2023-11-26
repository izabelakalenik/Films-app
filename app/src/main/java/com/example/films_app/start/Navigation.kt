package com.example.films_app.start

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.films_app.dataClasses.movies
import com.example.films_app.ui.screens.MovieDetail
import com.example.films_app.ui.screens.MovieList
import com.example.films_app.ui.theme.FilmsAppTheme

const val MAIN_SCREEN_ROUTE = "movieList"
const val DETAIL_SCREEN_ROUTE = "movieDetail/{movieId}"

@Composable
fun AppNavigation() {
    FilmsAppTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = MAIN_SCREEN_ROUTE
        ) {
            composable(MAIN_SCREEN_ROUTE) {
                MovieList(navController)
            }
            composable(
                DETAIL_SCREEN_ROUTE,
                arguments = listOf(navArgument("movieId") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")
                val movie = movies.find { it.title == movieId }
                if (movie != null) {
                    MovieDetail(movie = movie)
                }
            }
        }
    }
}
