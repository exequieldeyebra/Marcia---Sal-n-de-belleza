package com.example.marcia.data

data class Producto(
    val id: String,
    val nombre: String,
    val descripcion: String = "", // <- Agregamos la descripción con valor por defecto
    val precio: Double,
    val stock: Int,
    val imagenResId: Int = 0
)