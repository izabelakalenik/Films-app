package com.example.films_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.films_app.R
import com.example.films_app.dataClasses.Movie
import com.example.films_app.dataClasses.movies
import com.example.films_app.ui.theme.DarkGrey
import com.example.films_app.ui.theme.LightGrey


@Composable
fun MovieList(navController: NavHostController) {
    Column {
        AppTopBar(title = "MCU Films")
        LazyColumn {
            items(movies) { movie ->
                MovieItem(movie = movie, navController)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String) {
    Surface(
        color = DarkGrey
    ){
        TopAppBar(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.big_padding))
                .height(dimensionResource(id = R.dimen.top_bar_height)),
            title = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = LightGrey,
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = DarkGrey)
        )
    }
}

@Composable
fun MovieItem(movie: Movie, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.big_padding))
            .clickable {
                navController.navigate("movieDetail/${movie.title}") {
                    launchSingleTop = true
                }
            }
    ) {
        Image(
            painter = painterResource(id = movie.imageRes),
            contentDescription = "Film poster",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.small_image_size), dimensionResource(id = R.dimen.big_image_size))
                .clip(shape = MaterialTheme.shapes.medium)
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width)))
        Text(
            text = movie.title,
            fontWeight = FontWeight.Bold,
            fontSize =  with(LocalDensity.current) { dimensionResource(id = R.dimen.big_font_size).toSp() },
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}

