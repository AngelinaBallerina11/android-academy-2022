@file:OptIn(ExperimentalComposeUiApi::class)

package com.strv.movies.ui.login

import DefaultHorizontalSpacer
import DefaultVerticalSpacer
import LargeVerticalSpacer
import SmallHorizontalSpacer
import android.util.Patterns
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strv.movies.R
import com.strv.movies.ui.theme.buttonGradientColor
import com.strv.movies.ui.theme.facebookButtonColor

private const val DEFAULT_ANIMATION_DURATION = 200

@Composable
fun LoginScreen() {
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
            .padding(32.dp)
            .fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
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
            GradientButton(isEnabled = isSignInButtonEnabled)
            DefaultVerticalSpacer()
            GoogleSignInButton()
            DefaultVerticalSpacer()
            FaceBookSignInButton()
            ForgotYourPasswordButton()
        }
    }
}

@Composable
fun FaceBookSignInButton() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = facebookButtonColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_facebook),
                contentDescription = "Facebook login",
                tint = MaterialTheme.colors.onPrimary
            )
            SmallHorizontalSpacer()
            Text(
                text = "Continue with Facebook",
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
private fun ForgotYourPasswordButton() {
    TextButton(onClick = {}) {
        Text(text = "Forgot your password?")
    }
}

@Composable
private fun Title() {
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

@Composable
private fun UsernameTextField(
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
                    usernameError.value =
                        !Patterns.EMAIL_ADDRESS.matcher(username.value).matches()
                }
            ),
            isError = usernameError.value,
            onValueChange = { username.value = it },
            modifier = Modifier.fillMaxWidth(),
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
private fun PasswordTextField(
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
                    val isError = password.value.length < 8
                    passwordError.value = isError
                    if (!isError) keyboardController?.hide()
                }
            ),
            singleLine = true,
            isError = passwordError.value,
            onValueChange = { password.value = it },
            modifier = Modifier
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

@Composable
fun GoogleSignInButton() {
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val buttonText = "Sign in with Google"
    val loadingText = "Signing in..."

    Column {
        Surface(
            modifier = Modifier
                .clickable(
                    onClick = {
                        isLoading = !isLoading
                    }
                )
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(width = 1.dp, color = Color.LightGray),
            color = MaterialTheme.colors.surface,
            elevation = 2.dp
        ) {
            Row(
                modifier = Modifier.height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    Box {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = !isLoading,
                            enter = fadeIn(tween(DEFAULT_ANIMATION_DURATION)),
                            exit = fadeOut(tween(DEFAULT_ANIMATION_DURATION))
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_google),
                                contentDescription = "SignInButton",
                                tint = Color.Unspecified
                            )
                        }
                        androidx.compose.animation.AnimatedVisibility(
                            visible = isLoading,
                            enter = fadeIn(tween(DEFAULT_ANIMATION_DURATION)),
                            exit = fadeOut(tween(DEFAULT_ANIMATION_DURATION))
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .height(16.dp)
                                    .width(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }
                }
                Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.Start) {
                    Row {
                        SmallHorizontalSpacer()
                        Box {
                            androidx.compose.animation.AnimatedVisibility(
                                visible = isLoading,
                                enter = fadeIn(tween(DEFAULT_ANIMATION_DURATION)),
                                exit = fadeOut(tween(DEFAULT_ANIMATION_DURATION))
                            ) {
                                Text(
                                    text = loadingText,
                                    maxLines = 1,
                                    style = MaterialTheme.typography.button
                                )
                            }
                            androidx.compose.animation.AnimatedVisibility(
                                visible = !isLoading,
                                enter = fadeIn(tween(DEFAULT_ANIMATION_DURATION)),
                                exit = fadeOut(tween(DEFAULT_ANIMATION_DURATION))
                            ) {
                                Text(
                                    text = buttonText,
                                    maxLines = 1,
                                    style = MaterialTheme.typography.button
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GradientButton(isEnabled: Boolean) {
    var isClicked by remember { mutableStateOf(false) }
    val backgroundColor = animateColorAsState(
        targetValue = if (isClicked) Color.Transparent else MaterialTheme.colors.secondary,
        animationSpec = tween(200)
    )
    Button(
        enabled = true,//isEnabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            isClicked = !isClicked
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colors.primary,
                            buttonGradientColor
                        )
                    )
                )
                .fillMaxWidth(),
            contentAlignment = Center
        ) {
            Box(
                modifier = Modifier
                    .background(SolidColor(backgroundColor.value))
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                contentAlignment = Center
            ) {
                Box(
                    modifier = Modifier.height(36.dp),
                    contentAlignment = Center
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = !isClicked,
                        enter = fadeIn(tween(DEFAULT_ANIMATION_DURATION)) + expandVertically(
                            tween(DEFAULT_ANIMATION_DURATION)
                        ),
                        exit = fadeOut(tween(DEFAULT_ANIMATION_DURATION)) + shrinkVertically(
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION)
                        )
                    ) {
                        Text(
                            text = "Sign In".uppercase(),
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                    androidx.compose.animation.AnimatedVisibility(
                        visible = isClicked,
                        enter = fadeIn(tween(400)) + expandVertically(
                            tween(400)
                        ),
                        exit = fadeOut(tween(DEFAULT_ANIMATION_DURATION)) + shrinkVertically(
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION)
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
}