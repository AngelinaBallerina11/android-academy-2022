package com.strv.movies.ui.login

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.strv.movies.R
import com.strv.movies.ui.loading.LoadingScreen
import com.strv.movies.ui.theme.buttonGradientColor
import com.strv.movies.ui.theme.facebookButtonColor

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

enum class ButtonState {
    Default, Loading, Success
}

@Composable
fun GradientButton(
    username: String,
    password: String,
    isEnabled: Boolean,
    viewModel: LoginViewModel,
    onSuccessfulLogin: () -> Unit,
    onFailure: (String) -> Unit
) {
    var buttonState by remember { mutableStateOf(ButtonState.Default) }
    val backgroundColor = animateColorAsState(
        targetValue = if (buttonState == ButtonState.Default) MaterialTheme.colors.secondary else Color.Transparent,
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
            buttonState = ButtonState.Loading
            viewModel.login(username, password, onSuccessfulLogin) { errorMessage ->
                buttonState = ButtonState.Default
                onFailure(errorMessage)
            }
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
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .background(SolidColor(backgroundColor.value))
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.height(36.dp),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = buttonState == ButtonState.Default,
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
                        visible = buttonState == ButtonState.Success,
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
                    androidx.compose.animation.AnimatedVisibility(
                        visible = buttonState == ButtonState.Loading,
                        enter = fadeIn(tween(400)) + expandVertically(
                            tween(400)
                        ),
                        exit = fadeOut(tween(DEFAULT_ANIMATION_DURATION)) + shrinkVertically(
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION)
                        )
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun ForgotYourPasswordButton() {
    TextButton(onClick = {}) {
        Text(text = "Forgot your password?")
    }
}