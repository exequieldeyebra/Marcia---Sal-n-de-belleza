package com.example.marcia.data

class Usuario (
    val id: Int,
    val nome: String,
    val email: String,
    val password: String
){

    override fun toString(): String {
        return "Usuario(id=$id, nome='$nome', email='$email', password='$password')"
    }

}

