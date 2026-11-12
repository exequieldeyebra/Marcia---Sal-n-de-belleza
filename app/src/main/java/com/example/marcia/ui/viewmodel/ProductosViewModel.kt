package com.example.marcia.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.marcia.data.Producto

/**
 * ViewModel encargado de gestionar el ABML de productos para el panel de administración
 * del salón de belleza, preparado con una lista local en memoria lista para conectar con BD.
 */
class ProductosViewModel : ViewModel() {

    // Lista de productos en inventario para administración (ABML)
    var listaProductos by mutableStateOf(
        listOf(
            Producto(
                id = "1",
                nombre = "Shampoo Especial",
                descripcion = "Tratamiento capilar nutritivo de uso profesional",
                precio = 12500.0,
                stock = 10,
                imagenResId = 0
            ),
            Producto(
                id = "2",
                nombre = "Acondicionador Brillo",
                descripcion = "Fórmula avanzada para suavidad extrema",
                precio = 11000.0,
                stock = 5,
                imagenResId = 0
            ),
            Producto(
                id = "3",
                nombre = "Crema para peinar",
                descripcion = "Definición y control anti-frizz",
                precio = 9500.0,
                stock = 8,
                imagenResId = 0
            ),
            Producto(
                id = "4",
                nombre = "Aceite de Argan",
                descripcion = "Reparación profunda de puntas",
                precio = 15000.0,
                stock = 3,
                imagenResId = 0
            )
        )
    )
        private set

    /**
     * ALTA o MODIFICACIÓN de un producto.
     * Si el ID viene nulo o vacío, se crea uno nuevo (Alta).
     * Si el ID ya existe, se actualizan sus datos (Modificación).
     */
    fun guardarProducto(id: String?, nombre: String, descripcion: String, precio: Double, stock: Int) {
        if (id.isNullOrEmpty()) {
            // Alta de un nuevo producto generando un ID único provisorio
            val nuevoId = (listaProductos.size + 1).toString()
            val nuevoProducto = Producto(
                id = nuevoId,
                nombre = nombre,
                descripcion = descripcion,
                precio = precio,
                stock = stock,
                imagenResId = 0
            )
            listaProductos = listaProductos + nuevoProducto
        } else {
            // Modificación de un producto existente
            listaProductos = listaProductos.map { producto ->
                if (producto.id == id) {
                    producto.copy(
                        nombre = nombre,
                        descripcion = descripcion,
                        precio = precio,
                        stock = stock
                    )
                } else {
                    producto
                }
            }
        }
    } // Fin de guardarProducto

    /**
     * BAJA de un producto por su ID (Eliminar).
     */
    fun eliminarProducto(id: String) {
        listaProductos = listaProductos.filter { it.id != id }
    } // Fin de eliminarProducto

} // Fin de ProductosViewModel