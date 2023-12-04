
package com.example.films_app.start

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.films_app.dataClasses.films
import com.example.films_app.ui.screens.PhotoScreen
import com.example.films_app.ui.screens.FilmDetails
import com.example.films_app.ui.screens.FilmList
import com.example.films_app.ui.theme.FilmsAppTheme

const val MAIN_SCREEN_ROUTE = "filmList"
const val DETAIL_SCREEN_ROUTE = "filmDetail/{filmId}"
const val PHOTO_SCREEN_ROUTE = "photo/{imageId}"


@Composable
fun AppNavigation() {
    FilmsAppTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = MAIN_SCREEN_ROUTE
        ) {
            composable(MAIN_SCREEN_ROUTE) {
                FilmList(navController)
            }
            composable(
                DETAIL_SCREEN_ROUTE,
                arguments = listOf(navArgument("filmId") { type = NavType.StringType })
            ) { backStackEntry ->
                val filmId = backStackEntry.arguments?.getString("filmId")
                val film = films.find { it.title == filmId }
                if (film != null) {
                    FilmDetails(film = film, navController)
                }
            }
            composable(
                PHOTO_SCREEN_ROUTE,
                arguments = listOf(navArgument("imageId") { type = NavType.IntType })
            ) { backStackEntry ->
                val imageId = backStackEntry.arguments?.getInt("imageId")
                if (imageId != null) {
                    PhotoScreen(imageId)
                }
            }
        }
    }
}