@file:OptIn(ExperimentalComposeUiApi::class)

package com.strv.movies.ui.login

import android.util.Patterns
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strv.movies.R


@Composable
fun LoginScreen() {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val username = rememberSaveable { mutableStateOf("") }
    val usernameError = rememberSaveable { mutableStateOf(false) }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordError = rememberSaveable { mutableStateOf(false) }
    val isButtonEnabled = username.value.isNotBlank() &&
            password.value.isNotBlank() &&
            !usernameError.value &&
            !passwordError.value

    Column(
        modifier = Modifier
            .padding(36.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Log In to Movies",
            style = TextStyle(
                color = MaterialTheme.colors.secondary,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                letterSpacing = 0.sp
            )
        )
        Column {
            OutlinedTextField(
                value = username.value,
                singleLine = true,
                label = { Text("Username") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequester.requestFocus()
                        usernameError.value =
                            !Patterns.EMAIL_ADDRESS.matcher(username.value).matches()
                    }
                ),
                isError = usernameError.value,
                onValueChange = { username.value = it },
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "User email"
                    )
                },
                shape = MaterialTheme.shapes.medium
            )
            if (usernameError.value) {
                Text(
                    text = "This is not a valid email",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        Column {
            OutlinedTextField(
                value = password.value,
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        val isError = password.value.length < 8
                        passwordError.value = isError
                        if (!isError) keyboardController?.hide()
                    }
                ),
                singleLine = true,
                isError = passwordError.value,
                onValueChange = { password.value = it },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .focusRequester(focusRequester = focusRequester),
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
                Text(
                    text = "Password should be at least 8 characters long",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        GradientButton(
            text = "Sign in",
            onClick = {},
            isEnabled = isButtonEnabled
        )
        Text(
            text = "Forgot your password?",
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean
) {
    var isClicked by remember { mutableStateOf(false) }
    val backgroundColor = animateColorAsState(
        targetValue = if (isClicked) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
        animationSpec = tween(200)
    )
    Button(
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            isClicked = !isClicked
            onClick()
        },
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .background(SolidColor(backgroundColor.value))
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            contentAlignment = Center
        ) {
            Box(
                modifier = Modifier.height(36.dp),
                contentAlignment = Center
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = !isClicked,
                    enter = fadeIn(tween(100)) + expandVertically(
                        tween(100)
                    ),
                    exit = fadeOut(tween(100)) + shrinkVertically(
                        animationSpec = tween(100)
                    )
                ) {
                    Text(
                        text = text.uppercase(),
                        color = MaterialTheme.colors.onPrimary,
                        style = MaterialTheme.typography.subtitle1
                    )
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = isClicked,
                    enter = fadeIn(tween(400)) + expandVertically(
                        tween(400)
                    ),
                    exit = fadeOut(tween(100)) + shrinkVertically(
                        animationSpec = tween(100)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_check),
                        tint = MaterialTheme.colors.onPrimary,
                        contentDescription = "Check success"
                    )
                }
            }
        }
    }
}