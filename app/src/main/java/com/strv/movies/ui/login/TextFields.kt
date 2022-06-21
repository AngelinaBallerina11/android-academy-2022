@file:OptIn(ExperimentalComposeUiApi::class)
package com.strv.movies.ui.login

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.strv.movies.R

@Composable
fun UsernameTextField(
    username: MutableState<String>,
    focusRequester: FocusRequester,
    usernameError: MutableState<Boolean>
) {
    Column {
        OutlinedTextField(
            value = username.value,
            singleLine = true,
            label = { Text("Username") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester.requestFocus()
                }
            ),
            isError = usernameError.value,
            onValueChange = { username.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (!it.hasFocus && username.value.isNotBlank()) {
                        usernameError.value = username.value.trim().length < 3
                    } else {
                        usernameError.value = false
                    }
                },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = "User email"
                )
            },
            shape = MaterialTheme.shapes.medium
        )
        if (usernameError.value) {
            Row {
                DefaultHorizontalSpacer()
                Text(
                    text = "This is not a valid email",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

@Composable
fun PasswordTextField(
    password: MutableState<String>,
    passwordError: MutableState<Boolean>,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester
) {
    Column {
        OutlinedTextField(
            value = password.value,
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    val isError = password.value.trim().length < 8
                    passwordError.value = isError
                    if (!isError) keyboardController?.hide()
                }
            ),
            singleLine = true,
            isError = passwordError.value,
            onValueChange = { password.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester = focusRequester)
                .onFocusChanged {
                    if (!it.hasFocus && password.value.isNotBlank()) {
                        passwordError.value = password.value.length < 8
                    } else {
                        passwordError.value = false
                    }
                },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = "Password"
                )
            },
            shape = MaterialTheme.shapes.medium,
            visualTransformation = PasswordVisualTransformation()
        )
        if (passwordError.value) {
            Row {
                DefaultHorizontalSpacer()
                Text(
                    text = "Password should be at least 8 characters long",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
} 