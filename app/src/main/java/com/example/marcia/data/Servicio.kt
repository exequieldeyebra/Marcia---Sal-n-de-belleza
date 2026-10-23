package com.example.marcia.data

data class Servicio(
    val id: Int,
    var nombre: String,
    var precio: String,
    var duracion: String,
    var descripcion: String,
    var categoria: String = "General",
    var personal: String = ""
)
