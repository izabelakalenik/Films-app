package com.example.films_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.films_app.dataClasses.movies
import com.example.films_app.ui.screens.MovieDetail
import com.example.films_app.ui.screens.MovieList
import com.example.films_app.ui.theme.FilmsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FilmsAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "movieList"
                ) {
                    composable("movieList") {
                        MovieList(navController)

                    }
                    composable(
                        "movieDetail/{movieId}",
                        arguments = listOf(navArgument("movieId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val arguments = requireNotNull(backStackEntry.arguments)
                        val movieId = arguments.getString("movieId")
                        val movie = movies.find { it.title == movieId }
                        if (movie != null) {
                            MovieDetail(movie = movie)
                        }
                    }
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MyApp {
//        MovieList(navController)
//    }
//}