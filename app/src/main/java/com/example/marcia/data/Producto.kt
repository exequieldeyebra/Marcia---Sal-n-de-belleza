package com.example.marcia.data

/**
 * Modelo de datos que representa un Producto disponible para la venta en el salón.
 */
data class Producto(
    val id: String,          // Identificador único del producto
    val nombre: String,      // Nombre comercial (ej: "Shampoo reparador")
    val precio: Double,      // Precio unitario
    val stock: Int,          // Cantidad disponible en stock
    val imagenResId: Int     // Referencia al recurso drawable de la imagen
)