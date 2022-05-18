package com.strv.movies.ui.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.data.MovieRepository
import com.strv.movies.extension.fold
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(MoviesListViewState(loading = true))
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMovieList().fold(
                e = { error ->
                    _viewState.update { MoviesListViewState(error = error, loading = false) }
                },
                v = { list ->
                    _viewState.update {
                        MoviesListViewState(
                            movies = list,
                            loading = false,
                            error = null
                        )
                    }
                }
            )
        }
    }
}