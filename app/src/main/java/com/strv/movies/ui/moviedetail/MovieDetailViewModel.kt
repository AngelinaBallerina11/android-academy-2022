package com.strv.movies.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.data.OfflineMoviesProvider
import com.strv.movies.ui.navigation.MoviesNavArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId =
        requireNotNull(savedStateHandle.get<Int>(MoviesNavArguments.MOVIE_ID_KEY)) {
            "Movie ID cannot be null"
        }

    private val _viewState =
        MutableStateFlow(MovieDetailViewState(isLoading = true))
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(500L)
            val random = Random.nextBoolean()
            if (random) {
                _viewState.update {
                    MovieDetailViewState(
                        movie = OfflineMoviesProvider.getMovieDetail(movieId),
                        error = "Something went wrong"
                    )
                }
            } else {
                _viewState.update {
                    MovieDetailViewState(
                        movie = OfflineMoviesProvider.getMovieDetail(movieId)
                    )
                }
            }
        }
    }

    fun updateVideoProgress(newProgress: Float) {
        _viewState.update {
            it.copy(videoProgress = newProgress)
        }
    }
}