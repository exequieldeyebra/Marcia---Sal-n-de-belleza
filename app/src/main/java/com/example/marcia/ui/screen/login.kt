package com.example.loginbasico.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Label
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun LoginInput(){
    var email by remember { mutableStateOf("") }
    var pass = remember { mutableStateOf("") }
    val modifier = Modifier.padding(5.dp)
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            label = { Text("Email") },
            placeholder = {Text("ejemplo@gmail.com")},
            value = email,
            onValueChange = { email = it},
            modifier = modifier

        )
        TextField(
            label = { Text("Password") },
            value = pass.value,
            onValueChange = { pass.value = it},
            modifier = modifier,
        )

    }
}

@Composable
fun LoginForm(){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 5.dp)
            .fillMaxSize()
    )

    {
        Text("Bienvenido")

        LoginInput()

        TextButton(onClick = {})  { Text("Iniciar sesion")}
    }
}


