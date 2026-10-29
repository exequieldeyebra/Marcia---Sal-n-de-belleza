package com.example.marcia.data

data class Usuario (
    val id: Int = 0,
    var nome: String = "",
    var apellido: String = "",
    var email: String = "",
    var tel : String = "",
    var dni : String = "",
    var password: String = "",


){

    override fun toString(): String {
        return "Usuario(id=$id, nome='$nome', email='$email', password='$password')"
    }

}

