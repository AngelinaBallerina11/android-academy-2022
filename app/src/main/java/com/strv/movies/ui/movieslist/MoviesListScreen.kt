package com.strv.movies.ui.movieslist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.strv.movies.R
import com.strv.movies.model.Movie
import com.strv.movies.ui.error.ErrorScreen
import com.strv.movies.ui.loading.LoadingScreen
import kotlin.math.roundToInt

@Composable
fun MoviesListScreen(
    navigateToMovieDetail: (movieId: Int) -> Unit,
    viewModel: MoviesListViewModel = viewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    println("AAA: $viewState")
    if (viewState.loading) {
        LoadingScreen()
    } else if (viewState.error != null) {
        ErrorScreen(errorMessage = viewState.error!!)
    } else {
        MoviesList(
            movies = viewState.movies,
            onMovieClick = navigateToMovieDetail
        )
    }
}

@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(all = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.BottomStart,

        ) {
            AsyncImage(
                contentScale = ContentScale.FillBounds,
                model = "https://image.tmdb.org/t/p/h632${movie.posterPath}",
                contentDescription = stringResource(id = R.string.movie_image),
                modifier = Modifier.fillMaxSize()
            )
            Card(
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Row(modifier = modifier.padding(4.dp)) {
                    Text(text = movie.averageVote.roundToSingleDecimal())
                    Image(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = "Average vote"
                    )
                }
            }
        }
    }
}

private fun Float.roundToSingleDecimal(): String {
    return ((this * 10f).roundToInt() / 10f).toString()
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MoviesList(
    movies: List<Movie>,
    onMovieClick: (movieId: Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        cells = GridCells.Fixed(2)
    ) {
        items(movies) { movie ->
            val state = remember {
                MutableTransitionState(false).apply {
                    // Start the animation immediately.
                    targetState = true
                }
            }
            AnimatedVisibility(
                visibleState = state,
                enter = fadeIn(animationSpec = tween(300)) + scaleIn(animationSpec = tween(300))
            ) {
                MovieItem(
                    movie = movie,
                    modifier = Modifier
                        .animateItemPlacement()
                        .clickable { onMovieClick(movie.id) }
                )
            }
        }
    }
}
