package com.strv.movies.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.strv.movies.R
import com.strv.movies.ui.theme.buttonGradientColor

@Composable
fun LoginScreen() {
    val username = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(36.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Log In to Movies",
            style = MaterialTheme.typography.h5
        )
        OutlinedTextField(
            value = username.value,
            label = { Text("Username") },
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
        OutlinedTextField(
            value = password.value,
            label = { Text("Password") },
            onValueChange = { password.value = it },
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = "Password"
                )
            },
            shape = MaterialTheme.shapes.medium,
            visualTransformation = PasswordVisualTransformation(),
        )
        GradientButton(
            text = "Sign in",
            textColor = MaterialTheme.colors.onPrimary,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    MaterialTheme.colors.primary,
                    buttonGradientColor
                )
            ),
            onClick = {}
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
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        shape = MaterialTheme.shapes.medium,
        onClick = { onClick() },
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text.uppercase(), color = textColor)
        }
    }
}