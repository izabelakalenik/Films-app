package com.example.films_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.films_app.dataClasses.Movie
import com.example.films_app.dataClasses.cast
import com.example.films_app.dataClasses.scenes

@Composable
fun MovieDetail(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(movie)
            Details(movie)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie.description,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(16.dp))

        TabRow()
    }
}

@Composable
fun Image(movie: Movie){
    Image(
        painter = painterResource(id = movie.imageRes),
        contentDescription = null,
        modifier = Modifier
            .size(200.dp, 300.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.background)
    )

}
@Composable
fun Details(movie: Movie){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = movie.title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            )
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Release date: ${movie.releaseDate}", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Director: ${movie.director}", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Duration: ${movie.duration}", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Production Country: ${movie.productionCountry}", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)

    }

}
@Composable
fun TabRow(){
    val selectedTabIndex = remember { mutableIntStateOf(0) }

    TabRow(
        selectedTabIndex = selectedTabIndex.intValue,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .height(80.dp)

    ) {
        Tab(
            selected = selectedTabIndex.intValue == 0,
            onClick = { selectedTabIndex.intValue = 0},
            modifier = Modifier
                .height(50.dp)
        ) {
            Text(text = "Scenes", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
        Tab(
            selected = selectedTabIndex.intValue == 1,
            onClick = { selectedTabIndex.intValue = 1},
            modifier = Modifier
                .height(50.dp)
        ) {
            Text(text = "Cast", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }

    when (selectedTabIndex.intValue) {
        0 -> FilmScenesTab()
        1 -> CastTab()
    }
}


@Composable
fun FilmScenesTab() {
    LazyRow {
        items(scenes) { scene ->
            Text(text = "Scene: ${scene.sceneName}")
        }
    }
}

@Composable
fun CastTab() {
    LazyColumn {
        items(cast) { castMember ->
            Text(text = "Cast: ${castMember.actorName}")
        }
    }
}