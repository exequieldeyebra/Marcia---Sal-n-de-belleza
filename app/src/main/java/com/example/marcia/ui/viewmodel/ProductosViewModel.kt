package com.example.marcia.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.marcia.data.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductosViewModel : ViewModel() {

    val listaProductos = mutableStateListOf<Producto>()

    // Estado reactivo para el formulario del modal (Alta/Edición)
    private val _productoEnEdicion = MutableStateFlow(Producto(id = "", nombre = "", descripcion = "", precio = 0.0, stock = 0, imagenResId = 0))
    val productoEnEdicion: StateFlow<Producto> = _productoEnEdicion.asStateFlow()

    init {
        cargarProductosIniciales()
    }

    private fun cargarProductosIniciales() {
        if (listaProductos.isEmpty()) {
            listaProductos.addAll(
                listOf(
                    Producto("1", "Shampoo Especial", "Tratamiento capilar nutritivo de uso profesional", 12500.0, 10, 0),
                    Producto("2", "Acondicionador Brillo", "Fórmula avanzada para suavidad extrema", 11000.0, 5, 0),
                    Producto("3", "Crema para peinar", "Definición y control anti-frizz", 9500.0, 8, 0),
                    Producto("4", "Aceite de Argan", "Reparación profunda de puntas", 15000.0, 3, 0)
                )
            )
        }
    }

    // Métodos para actualizar los campos desde el formulario modal
    fun onNombreChange(v: String) { _productoEnEdicion.update { it.copy(nombre = v) } }
    fun onDescripcionChange(v: String) { _productoEnEdicion.update { it.copy(descripcion = v) } }
    fun onPrecioChange(v: String) {
        val precioDouble = v.toDoubleOrNull() ?: 0.0
        _productoEnEdicion.update { it.copy(precio = precioDouble) }
    }
    fun onStockChange(v: String) {
        val stockInt = v.toIntOrNull() ?: 0
        _productoEnEdicion.update { it.copy(stock = stockInt) }
    }

    fun prepararNuevoProducto() {
        _productoEnEdicion.value = Producto(id = "", nombre = "", descripcion = "", precio = 0.0, stock = 0, imagenResId = 0)
    }

    fun seleccionarProductoParaEdicion(producto: Producto) {
        _productoEnEdicion.value = producto.copy()
    }

    fun guardarNuevoProducto() {
        val nuevoId = ((listaProductos.maxOfOrNull { it.id.toIntOrNull() ?: 0 } ?: 0) + 1).toString()
        val nuevoProducto = _productoEnEdicion.value.copy(id = nuevoId)
        listaProductos.add(nuevoProducto)
        prepararNuevoProducto()
    }

    fun guardarEdicionProducto() {
        val idEditando = _productoEnEdicion.value.id
        val index = listaProductos.indexOfFirst { it.id == idEditando }
        if (index != -1) {
            listaProductos[index] = _productoEnEdicion.value.copy()
        }
        prepararNuevoProducto()
    }

    fun eliminarProducto(producto: Producto) {
        listaProductos.remove(producto)
    }
}