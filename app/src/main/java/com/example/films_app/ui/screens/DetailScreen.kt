package com.example.films_app.ui.screens
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.films_app.R
import com.example.films_app.dataClasses.CastMember
import com.example.films_app.dataClasses.Movie
import com.example.films_app.dataClasses.Scene
import com.example.films_app.dataClasses.Trailer
import androidx.compose.runtime.remember as remember


@Composable
fun MovieDetail(movie: Movie, navController: NavController) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.medium_padding))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.big_spacer))
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            MainImage(movie, navController)
            Details(movie)
        }
        Text(
            text = movie.description,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        )
        TabRow(selectedTabIndex, movie, navController) { newSelectedTabIndex ->
            selectedTabIndex = newSelectedTabIndex
        }
    }
}

@Composable
fun MainImage(movie: Movie, navController: NavController){
    Image(
        painter = painterResource(id = movie.imageRes),
        contentDescription = "Film poster",
        modifier = Modifier
            .size(
                dimensionResource(id = R.dimen.huge_image_size),
                dimensionResource(id = R.dimen.enormous_image_size)
            )
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = { navController.navigate("photo/${movie.imageRes}") })
    )
}

@Composable
fun Details(movie: Movie) {
    Column(
        modifier = Modifier
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
fun TabRow(selectedTabIndex: Int, movie: Movie, navController: NavController, onTabSelected: (Int) -> Unit) {
    val tabTitles = listOf("Scenes", "Trailers", "Cast")
    val selectedTabIndices = remember { mutableStateListOf<Int>().apply { repeat(tabTitles.size) { add(if (it == selectedTabIndex) 1 else 0) } } }

    TabRow(
        selectedTabIndex = selectedTabIndex,
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndices[index] == 1,
                onClick = {
                    selectedTabIndices.fill(0)
                    selectedTabIndices[index] = 1
                    onTabSelected(index)
                },
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.height))
            ) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.medium_font_size).toSp() })
            }
        }
    }

    when (selectedTabIndex) {
        0 -> FilmScenesTab(movie.scenesList, navController)
        1 -> TrailersTab(movie.trailersList)
        2 -> CastTab(movie.castList, navController)
    }
}

@Composable
fun FilmScenesTab(scenes: List<Scene>, navController: NavController) {
    LazyVerticalGrid(columns = GridCells.Fixed(3),
        modifier = Modifier.height(dimensionResource(id = R.dimen.big_height))) {
        items(scenes) { scene ->
            Image(
                painter = painterResource(id = scene.sceneImage),
                contentDescription = "Scenes from film",
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.small_image_size))
                    .padding(dimensionResource(id = R.dimen.small_padding))
                    .clip(MaterialTheme.shapes.medium)
                    .clickable(onClick = { navController.navigate("photo/${scene.sceneImage}") }),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun TrailersTab(trailers: List<Trailer>) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    var player by remember { mutableStateOf<ExoPlayer?>(null) }

    DisposableEffect(context, lifecycle) {
        player = ExoPlayer.Builder(context).build()

        trailers.forEach { trailer ->
            val uri = "android.resource://${context.packageName}/${trailer.trailerID}"
            player?.addMediaItem(MediaItem.fromUri(uri))
        }

        player?.prepare()

        onDispose {
            player?.release()
        }
    }

    VideoView(lifecycle, player)
}

@Composable
fun VideoView(lifecycle: Lifecycle, player: ExoPlayer?) {
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            }
        },
        update = { playerView ->
            playerView.player = player
            when (lifecycle.currentState) {
                Lifecycle.State.STARTED -> player?.play()
                else -> player?.pause()
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(16 / 9f)
    )
}


@Composable
fun CastTab(cast: List<CastMember>, navController: NavController) {
    LazyColumn (
        modifier = Modifier.height(dimensionResource(id = R.dimen.big_height))
    ){
        items(cast) { castMember ->
            Row {
                Image(
                    painter = painterResource(id = castMember.actorImage),
                    contentDescription = "Actor photo",
                    modifier = Modifier
                        .size(
                            dimensionResource(id = R.dimen.small_image_size),
                            dimensionResource(id = R.dimen.small_image_size)
                        )
                        .padding(dimensionResource(id = R.dimen.small_padding))
                        .clip(MaterialTheme.shapes.medium)
                        .clickable(onClick = { navController.navigate("photo/${castMember.actorImage}") }),
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