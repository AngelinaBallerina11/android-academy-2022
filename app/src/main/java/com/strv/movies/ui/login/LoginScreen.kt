@file:OptIn(ExperimentalComposeUiApi::class)

package com.strv.movies.ui.login

import DefaultVerticalSpacer
import LargeVerticalSpacer
import SmallVerticalSpacer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

internal const val DEFAULT_ANIMATION_DURATION = 200

@Composable
fun LoginScreen(onLoginClick: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val username = rememberSaveable { mutableStateOf("") }
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
            GradientButton(isEnabled = isSignInButtonEnabled, onLoginClick)
            DefaultVerticalSpacer()
            GoogleSignInButton()
            DefaultVerticalSpacer()
            FaceBookSignInButton()
            SmallVerticalSpacer()
            ForgotYourPasswordButton()
            DefaultVerticalSpacer()
        }
    }
}
