package com.strv.movies.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.extension.fold
import com.strv.movies.network.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun login(
        username: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            authRepository.logIn(
                username = username,
                password = password
            ).fold(
                { error ->
                    println("AAA: " + "Login failed - ${error.name}")
                    onFailure(error.name)
                },
                {
                    onSuccess()
                }
            )
        }

    }
}