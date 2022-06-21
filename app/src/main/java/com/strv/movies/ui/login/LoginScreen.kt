@file:OptIn(ExperimentalComposeUiApi::class)

package com.strv.movies.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

internal const val DEFAULT_ANIMATION_DURATION = 200

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onSuccessfulLogin: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val username = rememberSaveable { mutableStateOf("") }
    val httpError = rememberSaveable { mutableStateOf("") }
    val usernameError = rememberSaveable { mutableStateOf(false) }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordError = rememberSaveable { mutableStateOf(false) }
    val isSignInButtonEnabled = username.value.isNotBlank() &&
            password.value.isNotBlank() &&
            !usernameError.value &&
            !passwordError.value

    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            LargeVerticalSpacer()
            Title()
            LargeVerticalSpacer()
            UsernameTextField(username, focusRequester, usernameError)
            DefaultVerticalSpacer()
            PasswordTextField(password, passwordError, keyboardController, focusRequester)
            DefaultVerticalSpacer()
        }
        Column(
            modifier = Modifier.weight(1f, false),
            horizontalAlignment = CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = httpError.value,
                style = TextStyle(
                    color = MaterialTheme.colors.error,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    letterSpacing = 0.sp
                )
            )
            GradientButton(
                username = username.value,
                password = password.value,
                isEnabled = isSignInButtonEnabled,
                viewModel = viewModel,
                onSuccessfulLogin = onSuccessfulLogin
            ) { errorMessage ->
                httpError.value = errorMessage
            }
            DefaultVerticalSpacer()
            GoogleSignInButton()
            DefaultVerticalSpacer()
            FaceBookSignInButton()
            DefaultVerticalSpacer()
            ForgotYourPasswordButton()
            DefaultVerticalSpacer()
        }
    }
}

@Composable
fun Title() {
    Text(
        text = "Log In to Movies",
        style = TextStyle(
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            letterSpacing = 0.sp
        )
    )
}