package com.strv.movies.ui.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strv.movies.domain.model.toMovie
import com.strv.movies.ui.loading.LoadingScreen
import com.strv.movies.ui.movieslist.MovieItem

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    onLogout: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()
    if (viewState.loading) LoadingScreen() else ProfileUi(viewState) {
        viewModel.logout(onSuccess = onLogout)
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun ProfileUi(
    viewState: ProfileViewModel.ViewState,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Profile screen", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.size(24.dp))
        Text(text = "username: ${viewState.username}", style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "name: ${viewState.name}", style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.size(24.dp))
        Button(onClick = { onLogout() }) {
            Text(text = "Logout")
        }
        Spacer(modifier = Modifier.size(8.dp))
        Divider()
        Spacer(modifier = Modifier.size(8.dp))
        LazyVerticalGrid(
            contentPadding = PaddingValues(8.dp),
            cells = GridCells.Fixed(2)
        ) {
            items(viewState.favourites) { movie ->
                val state = remember {
                    MutableTransitionState(false).apply {
                        targetState = true
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visibleState = state,
                    enter = fadeIn(animationSpec = tween(300)) + scaleIn(animationSpec = tween(300))
                ) {
                    MovieItem(
                        movie = movie.toMovie(),
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        }
    }
}