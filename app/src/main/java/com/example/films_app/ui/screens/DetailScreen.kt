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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.films_app.dataClasses.Movie
import com.example.films_app.dataClasses.cast
import com.example.films_app.dataClasses.scenes

@Composable
fun MovieDetail(movie: Movie) {
    val selectedTabIndex = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Image on the left
            Image(
                painter = painterResource(id = movie.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp, 300.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.background)
            )

            // Specific information on the right
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Title in bold
                Text(
                    text = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start),

                    )
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Release date: ${movie.releaseDate}")
                Text(text = "Director: ${movie.director}")
                Text(text = "Duration: ${movie.duration}")
                Text(text = "Production Country: ${movie.productionCountry}")

            }

        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.description,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Additional movie information on the right


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