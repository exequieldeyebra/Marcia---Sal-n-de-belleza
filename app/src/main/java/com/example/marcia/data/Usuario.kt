package com.example.marcia.data

class Usuario (
    val id: Int,
    var nome: String,
    var email: String,
    var password: String
){

    override fun toString(): String {
        return "Usuario(id=$id, nome='$nome', email='$email', password='$password')"
    }

}

