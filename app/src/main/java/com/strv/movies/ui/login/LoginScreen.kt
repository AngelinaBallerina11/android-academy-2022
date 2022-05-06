package com.strv.movies.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.strv.movies.R

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
        Button(
            onClick = { /*TODO*/ },
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text(text = "Sign In".uppercase())
        }
        Text(
            text = "Forgot your password?",
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}