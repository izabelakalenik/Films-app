package com.example.films_app.ui.screens

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.films_app.dataClasses.Movie
import com.example.films_app.dataClasses.movies


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
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}

