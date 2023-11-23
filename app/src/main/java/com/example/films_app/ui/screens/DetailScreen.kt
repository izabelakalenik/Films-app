package com.example.films_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.films_app.dataClasses.CastMember
import com.example.films_app.dataClasses.Movie
import com.example.films_app.dataClasses.Scene

@Composable
fun MovieDetail(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MainImage(movie)
            Details(movie)
        }
        Text(
            text = movie.description,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        )
        TabRow(movie)
    }
}

@Composable
fun MainImage(movie: Movie){
    Image(
        painter = painterResource(id = movie.imageRes),
        contentDescription = null,
        modifier = Modifier
            .size(200.dp, 300.dp)
            .clip(MaterialTheme.shapes.medium)
    )

}

@Composable
fun Details(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = movie.title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
        )

        Text("Release date: ${movie.releaseDate}", fontStyle = FontStyle.Italic)
        Text("Director: ${movie.director}", fontStyle = FontStyle.Italic)
        Text("Duration: ${movie.duration}", fontStyle = FontStyle.Italic)
        Text("Production Country: ${movie.productionCountry}", fontStyle = FontStyle.Italic)
    }
}
@Composable
fun TabRow(movie: Movie){
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
        0 -> FilmScenesTab(movie.scenesList)
        1 -> CastTab(movie.castList)
    }
}

@Composable
fun FilmScenesTab(scenes: List<Scene>) {
    LazyVerticalGrid(columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxHeight()) {
        items(scenes) { scene ->
            Image(
                painter = painterResource(id = scene.sceneImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(4.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun CastTab(cast: List<CastMember>) {
    LazyColumn {
        items(cast) { castMember ->
            Row{
                Image(
                    painter = painterResource(id = castMember.actorImage),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp, 120.dp)
                        .padding(4.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Text(text = castMember.actorName,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}