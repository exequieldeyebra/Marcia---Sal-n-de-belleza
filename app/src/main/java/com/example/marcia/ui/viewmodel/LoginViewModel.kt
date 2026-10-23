package com.example.loginbasico.ui.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.marcia.data.Usuario

class LoginViewModel : ViewModel() {

    // Estado reactivo de los campos de login (sobreviven a la rotación)
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf("")

    var usuario by mutableStateOf(
        Usuario(
            id = 0,
            nome = "usuario",
            email = "email@gmail.com",
            password = "1234"
        )
    )

    fun validateLogin(): Boolean {
        return when {
            email.isBlank() && password.isBlank() -> {
                errorMessage = "Por favor, ingresa el correo y la contraseña."
                false
            }
            email.isBlank() -> {
                errorMessage = "Por favor, ingresa el correo electrónico."
                false
            }
            password.isBlank() -> {
                errorMessage = "Por favor, ingresa la contraseña."
                false
            }
            email != usuario.email || password != usuario.password -> {
                errorMessage = "El correo o la contraseña son incorrectos."
                false
            }
            else -> {
                errorMessage = ""
                true
            }
        }
    }

    fun clearError() {
        errorMessage = ""
    }

}