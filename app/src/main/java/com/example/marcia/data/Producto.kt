package com.example.marcia.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa la tabla "productos" dentro de la base de datos local SQLite de Room.
 * Cada instancia equivale a una fila en la tabla.
 */
@Entity(tableName = "productos")
data class Producto(
    // Clave primaria autoincremental: Room genera el ID automáticamente al insertar con id = 0
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    // Nombre comercial del producto (ej: "Shampoo Especial")
    val nombre: String,
    // Precio unitario en pesos
    val precio: Double,
    // Cantidad de unidades disponibles en el inventario
    val stock: Int,
    // Referencia opcional a un recurso drawable (0 si no tiene imagen asignada)
    val imagenResId: Int = 0
)