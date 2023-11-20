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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MovieList()
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
fun MovieList() {
    LazyColumn {
        items(movies) { movie ->
            MovieItem(movie = movie)
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { /* Handle click on movie item */ }
    ) {
        Image(
            painter = painterResource(id = movie.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp, 180.dp)  // Ustaw rozmiar obrazu
                .clip(MaterialTheme.shapes.medium)
                .aspectRatio(4 / 6f)  // Ustaw proporcje obrazu
                .background(MaterialTheme.colorScheme.background)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = movie.title,
            modifier = Modifier
                .align(Alignment.CenterVertically),

            )
    }
}

data class Movie(val title: String, val imageRes: Int)

val movies = listOf(
    Movie("Captain America: The Winter Soldier", R.drawable.winter_soldier),
    Movie("Captain America: Civil War", R.drawable.civil_war),
    Movie("Dr Strange in the Multiverse of Madness", R.drawable.dr_strange)
)


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MovieList()
    }
}