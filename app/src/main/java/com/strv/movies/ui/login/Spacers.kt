package com.strv.movies.ui.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val SMALL_SPACE = 8.dp
private val DEFAULT_SPACE = 16.dp
private val LARGE_SPACE = 24.dp

@Composable
fun SmallVerticalSpacer() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(SMALL_SPACE)
    )
}

@Composable
fun DefaultVerticalSpacer() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(DEFAULT_SPACE)
    )
}

@Composable
fun LargeVerticalSpacer() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(LARGE_SPACE)
    )
}

@Composable
fun SmallHorizontalSpacer() {
    Spacer(
        modifier = Modifier
            .width(SMALL_SPACE)
    )
}

@Composable
fun DefaultHorizontalSpacer() {
    Spacer(
        modifier = Modifier
            .width(DEFAULT_SPACE)
    )
}

@Composable
fun LargeHorizontalSpacer() {
    Spacer(
        modifier = Modifier
            .width(LARGE_SPACE)
    )
}