package com.strv.movies.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.data.AuthRepository
import com.strv.movies.data.ProfileRepository
import com.strv.movies.domain.fold
import com.strv.movies.domain.model.FavoriteMovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(ViewState(loading = true))
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            val account = profileRepository.getAccountDetails()
            val favorites = profileRepository.getFavoriteMovies(account.id)
            _viewState.update {
                it.copy(
                    name = account.name,
                    username = account.username,
                    favourites = favorites,
                    loading = false
                )
            }
        }
    }

    fun logout(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            authRepository.logOut().fold(
                {
                    Log.d("TAG", "Logout failed")
                },
                {
                    onSuccess()
                }
            )
        }
    }

    data class ViewState(
        val loading: Boolean = false,
        val username: String = "",
        val name: String = "",
        val favourites: List<FavoriteMovieDetail> = emptyList()
    )
}