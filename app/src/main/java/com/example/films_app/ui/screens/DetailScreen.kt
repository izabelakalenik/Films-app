package com.example.films_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.films_app.R
import com.example.films_app.dataClasses.CastMember
import com.example.films_app.dataClasses.Movie
import com.example.films_app.dataClasses.Scene

@Composable
fun MovieDetail(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.medium_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.big_spacer))
    ) {
        Row {
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
        contentDescription = "Film poster",
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.huge_image_size), dimensionResource(id = R.dimen.enormous_image_size))
            .clip(MaterialTheme.shapes.medium)
    )

}

@Composable
fun Details(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.big_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_spacer))
    ) {
        Text(
            text = movie.title,
            fontWeight = FontWeight.Bold,
            fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.medium_font_size).toSp() },
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
        ) {
            Tab(
                selected = selectedTabIndex.intValue == 0,
                onClick = { selectedTabIndex.intValue = 0 },
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.height))
            ) {
                Text(text = "Scenes", fontWeight = FontWeight.Bold, fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.medium_font_size).toSp() })
            }
            Tab(
                selected = selectedTabIndex.intValue == 1,
                onClick = { selectedTabIndex.intValue = 1 },
            ) {
                Text(text = "Cast", fontWeight = FontWeight.Bold, fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.medium_font_size).toSp() })
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
                contentDescription = "Scenes from film",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.big_padding))
                    .padding(dimensionResource(id = R.dimen.small_padding))
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun CastTab(cast: List<CastMember>) {
    LazyColumn (
        modifier = Modifier.fillMaxHeight()
    ){
        items(cast) { castMember ->
            Row{
                Image(
                    painter = painterResource(id = castMember.actorImage),
                    contentDescription = "Actor photo",
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.big_padding), dimensionResource(id = R.dimen.big_padding))
                        .padding(dimensionResource(id = R.dimen.small_padding))
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Text(text = castMember.actorName,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.big_padding))
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}