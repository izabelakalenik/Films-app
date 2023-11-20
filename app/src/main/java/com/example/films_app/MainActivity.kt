package com.example.films_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.runtime.mutableStateOf

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
                navController.navigate("movieDetail/${movie.title}") {
                    launchSingleTop = true
                }
            }
    ) {
        Image(
            painter = painterResource(id = movie.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp, 180.dp)
                .clip(shape = MaterialTheme.shapes.medium)
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






data class Movie(val title: String, val description: String, val imageRes: Int)

val movies = listOf(
    Movie(
        "Captain America: The Winter Soldier",
        "Description for Captain America: The Winter Soldier goes here...",
        R.drawable.winter_soldier
    ),
    Movie(
        "Captain America: Civil War",
        "Description for Captain America: Civil War goes here...",
        R.drawable.civil_war
    ),
    Movie(
        "Dr Strange in the Multiverse of Madness",
        "Description for Dr Strange in the Multiverse of Madness goes here...",
        R.drawable.dr_strange
    )
)

@Composable
fun MovieDetail(movie: Movie) {
    val selectedTabIndex = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = movie.imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.background)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Display personalized description for each movie
        Text(
            text = movie.description, // Add a 'description' property to your Movie data class
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // TabRow with two tabs: Scenes and Cast
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .height(56.dp) // Adjust the height as needed
        ) {
            Tab(
                selected = selectedTabIndex.value == 0,
                onClick = { selectedTabIndex.value = 0 }
            ) {
                Text(text = "Scenes")
            }
            Tab(
                selected = selectedTabIndex.value == 1,
                onClick = { selectedTabIndex.value = 1 }
            ) {
                Text(text = "Cast")
            }
        }

        // Display tab content based on the selected tab index
        when (selectedTabIndex.value) {
            0 -> FilmScenesTab()
            1 -> CastTab()
        }
    }
}



@Composable
fun FilmScenesTab() {
    // Content for the "Scenes" tab
    LazyRow {
        items(scenes) { scene ->
            // Display scene information
            Text(text = "Scene: ${scene.sceneName}")
        }
    }
}

@Composable
fun CastTab() {
    // Content for the "Cast" tab
    LazyColumn {
        items(cast) { castMember ->
            // Display cast member information
            Text(text = "Cast: ${castMember.actorName}")
        }
    }
}

data class Scene(val sceneName: String)
data class CastMember(val actorName: String)

val scenes = listOf(
    Scene("Scene 1"),
    Scene("Scene 2"),
    // Add more scenes as needed
)

val cast = listOf(
    CastMember("Actor 1"),
    CastMember("Actor 2"),
    // Add more cast members as needed
)




//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MyApp {
//        MovieList(navController)
//    }
//}