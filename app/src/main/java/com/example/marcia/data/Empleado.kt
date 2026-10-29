package com.example.marcia.data

data class Empleado(
    var id : Int = 0,
    var UsuarioId : Usuario = Usuario(),
    var role: String = "",
    var shifts: String = "",
    var rating: String = ""
)
