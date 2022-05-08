package com.strv.movies.ui.login

import SmallHorizontalSpacer
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.strv.movies.R
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

@Composable
fun GradientButton(isEnabled: Boolean) {
    var isClicked by remember { mutableStateOf(false) }
    val backgroundColor = animateColorAsState(
        targetValue = if (isClicked) Color.Transparent else MaterialTheme.colors.secondary,
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

@Composable
fun ForgotYourPasswordButton() {
    TextButton(onClick = {}) {
        Text(text = "Forgot your password?")
    }
}