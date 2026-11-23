package com.example.marcia.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de Room que representa la tabla "productos" en la base local SQLite.
 */
@Entity(tableName = "productos")
data class Producto(
    // Clave primaria autoincremental: en 0 Room genera el siguiente ID solo al insertar
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    // Nombre comercial del producto
    val nombre: String,
    // Descripción opcional del artículo en el inventario
    val descripcion: String = "",
    // Precio unitario en pesos
    val precio: Double,
    // Cantidad de unidades disponibles en stock
    val stock: Int,
    // Recurso drawable opcional
    val imagenResId: Int = 0
)