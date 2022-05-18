package com.strv.movies.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.data.MovieRepository
import com.strv.movies.extension.Either
import com.strv.movies.ui.navigation.MoviesNavArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: MovieRepository
) : ViewModel() {

    private val movieId =
        requireNotNull(savedStateHandle.get<Int>(MoviesNavArguments.MOVIE_ID_KEY)) {
            "Movie id is missing..."
        }

    private val _viewState = MutableStateFlow(MovieDetailViewState(loading = true))
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val detailDeferred = async {
                repository.getMovieDetail(movieId)
            }
            val trailerDeferred = async {
                repository.getTrailer(movieId)
            }
            val detail = detailDeferred.await()
            val trailer = trailerDeferred.await()
            when {
                detail is Either.Error -> {
                    _viewState.update { state ->
                        state.copy(
                            error = detail.error,
                            loading = false
                        )
                    }
                }
                trailer is Either.Error -> {
                    _viewState.update { state ->
                        state.copy(
                            error = trailer.error,
                            loading = false
                        )
                    }
                }
                trailer is Either.Value && detail is Either.Value -> {
                    _viewState.update { state ->
                        state.copy(
                            error = null,
                            loading = false,
                            movie = detail.value,
                            trailerKey = trailer.value.key
                        )
                    }
                }
            }
        }
    }

    fun updateVideoProgress(progress: Float) {
        _viewState.update { it.copy(videoProgress = progress) }
    }
}