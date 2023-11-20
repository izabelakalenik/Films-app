package com.example.films_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.films_app.ui.theme.Films_appTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Films_appTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "movieList"
                ) {
                    composable("movieList") {
                        MyApp {
                            MovieList(navController)
                        }
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


@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = Typography()
    ) {
        content()
    }
}
//@Composable
//fun Typography() {
//    MaterialTheme.typography.copy(
//        // Dostosuj styl tekstu według własnych preferencji
//        TextStyle(
//            fontSize = 30.sp,
//            fontWeight = FontWeight.Bold,
//            lineHeight = 40.sp
//        ),
//        // Dodaj więcej dostosowań
//    )
//}

@Composable
fun MovieList(navController: NavHostController) {
    LazyColumn {
        items(movies) { movie ->
            MovieItem(navController, movie = movie)
        }
    }
}

@Composable
fun MovieItem(navController: NavController, movie: Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                navController.navigate("movieDetail/${movie.title}")
            }
    ) {
        Image(
            painter = painterResource(id = movie.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp, 180.dp)
                .clip(MaterialTheme.shapes.medium)
                .aspectRatio(4 / 6f)
                .background(MaterialTheme.colorScheme.background)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = movie.title,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}


data class Movie(val title: String, val imageRes: Int)

val movies = listOf(
    Movie("Captain America: The Winter Soldier", R.drawable.winter_soldier),
    Movie("Captain America: Civil War", R.drawable.civil_war),
    Movie("Dr Strange in the Multiverse of Madness", R.drawable.dr_strange)
)

@Composable
fun MovieDetail(movie: Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        Text(
            text = "Opis...",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
    }
}
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MyApp {
//        MovieList(navController)
//    }
//}