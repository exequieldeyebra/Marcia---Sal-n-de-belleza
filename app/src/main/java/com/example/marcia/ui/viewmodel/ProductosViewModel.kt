package com.example.marcia.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.marcia.data.Producto

/**
 * Modelo interno para representar un elemento dentro del carrito con su cantidad.
 */
data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int
)

class ProductosViewModel : ViewModel() {

    // Lista de productos en catálogo
    var listaProductos by mutableStateOf(
        listOf(
            Producto("1", "Shampoo Especial", 12500.0, 10, 0),
            Producto("2", "Acondicionador Brillo", 11000.0, 5, 0),
            Producto("3", "Crema para peinar", 9500.0, 8, 0),
            Producto("4", "Aceite de Argan", 15000.0, 3, 0)
        )
    )
        private set

    // Lista de ítems en el carrito de compras
    var carrito by mutableStateOf(listOf<ItemCarrito>())
        private set

    // Medio de pago seleccionado (Efectivo, Transferencia, Tarjeta)
    var medioPagoSeleccionado by mutableStateOf("Efectivo")
        private set

    // Opciones disponibles de medio de pago
    val mediosPagoDisponibles = listOf("Efectivo", "Transferencia", "Tarjeta de Débito/Crédito")

    /**
     * Calcula el monto total acumulado del carrito dinámicamente.
     */
    val totalVenta: Double
        get() = carrito.sumOf { it.producto.precio * it.cantidad }

    /**
     * Agrega un producto al carrito o incrementa su cantidad si ya existía.
     */
    fun agregarAlCarrito(producto: Producto) {
        val itemExistente = carrito.find { it.producto.id == producto.id }
        if (itemExistente != null) {
            // Si ya existe, creamos una nueva lista incrementando la cantidad del ítem
            carrito = carrito.map {
                if (it.producto.id == producto.id) {
                    it.copy(cantidad = it.cantidad + 1)
                } else {
                    it
                }
            }
        } else {
            // Si no existe, lo sumamos a la lista con cantidad 1
            carrito = carrito + ItemCarrito(producto = producto, cantidad = 1)
        }
    } // Fin de agregarAlCarrito

    /**
     * Incrementa la cantidad de un producto en el carrito.
     */
    fun incrementarCantidad(item: ItemCarrito) {
        carrito = carrito.map {
            if (it.producto.id == item.producto.id) {
                it.copy(cantidad = it.cantidad + 1)
            } else {
                it
            }
        }
    } // Fin de incrementarCantidad

    /**
     * Decrementa la cantidad de un producto o lo elimina si llega a cero.
     */
    fun decrementarCantidad(item: ItemCarrito) {
        if (item.cantidad > 1) {
            carrito = carrito.map {
                if (it.producto.id == item.producto.id) {
                    it.copy(cantidad = it.cantidad - 1)
                } else {
                    it
                }
            }
        } else {
            // Si la cantidad es 1 y decrementa, se remueve del carrito
            carrito = carrito.filter { it.producto.id != item.producto.id }
        }
    } // Fin de decrementarCantidad

    /**
     * Elimina por completo un ítem del carrito.
     */
    fun eliminarDelCarrito(item: ItemCarrito) {
        carrito = carrito.filter { it.producto.id != item.producto.id }
    } // Fin de eliminarDelCarrito

    /**
     * Actualiza el medio de pago seleccionado.
     */
    fun seleccionarMedioPago(medio: String) {
        medioPagoSeleccionado = medio
    } // Fin de seleccionarMedioPago

    /**
     * Simula el cobro y cierre de la venta.
     */
    fun finalizarVenta(): Boolean {
        if (carrito.isEmpty()) {
            return false
        } else {
            // Lógica futura para guardar en BD/API
            carrito = emptyList()
            return true
        }
    } // Fin de finalizarVenta

} // Fin de ProductosViewModel