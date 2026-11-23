package com.example.marcia.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.marcia.data.MarciaDatabase
import com.example.marcia.data.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel del módulo de productos conectado a Room mediante AndroidViewModel.
 * Mantiene los estados y métodos del ABML sin tocar otros módulos del proyecto.
 */
class ProductosViewModel(application: Application) : AndroidViewModel(application) {

    // Obtenemos el DAO desde la base singleton de Room
    private val productoDao = MarciaDatabase.obtenerInstancia(application).productoDao()

    // Lista de productos observada en tiempo real desde SQLite mediante Flow
    val listaProductos: StateFlow<List<Producto>> = productoDao.observarProductos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Estado reactivo del formulario modal para Alta o Modificación
    private val _productoEnEdicion = MutableStateFlow(
        Producto(id = 0, nombre = "", descripcion = "", precio = 0.0, stock = 0, imagenResId = 0)
    )
    val productoEnEdicion: StateFlow<Producto> = _productoEnEdicion.asStateFlow()

    // Texto de los campos numéricos para no pisar lo que escribe el usuario en el modal
    var precioTexto by mutableStateOf("")
        private set

    var stockTexto by mutableStateOf("")
        private set

    init {
        cargarProductosIniciales()
    }

    /**
     * Precarga los productos iniciales solo si la tabla está vacía.
     */
    private fun cargarProductosIniciales() {
        viewModelScope.launch {
            if (productoDao.obtenerTodos().isEmpty()) {
                productoDao.insertarVarios(
                    listOf(
                        Producto(nombre = "Shampoo Especial", descripcion = "Tratamiento capilar nutritivo de uso profesional", precio = 12500.0, stock = 10, imagenResId = 0),
                        Producto(nombre = "Acondicionador Brillo", descripcion = "Fórmula avanzada para suavidad extrema", precio = 11000.0, stock = 5, imagenResId = 0),
                        Producto(nombre = "Crema para peinar", descripcion = "Definición y control anti-frizz", precio = 9500.0, stock = 8, imagenResId = 0),
                        Producto(nombre = "Aceite de Argan", descripcion = "Reparación profunda de puntas", precio = 15000.0, stock = 3, imagenResId = 0)
                    )
                )
            }
        }
    }

    // Actualiza el nombre en el formulario
    fun onNombreChange(v: String) {
        _productoEnEdicion.update { it.copy(nombre = v) }
    }

    // Actualiza la descripción en el formulario
    fun onDescripcionChange(v: String) {
        _productoEnEdicion.update { it.copy(descripcion = v) }
    }

    // Actualiza el precio y su texto crudo desde el modal
    fun onPrecioChange(v: String) {
        precioTexto = v
        val precioDouble = v.replace(",", ".").toDoubleOrNull() ?: 0.0
        _productoEnEdicion.update { it.copy(precio = precioDouble) }
    }

    // Actualiza el stock y su texto crudo desde el modal
    fun onStockChange(v: String) {
        stockTexto = v
        val stockInt = v.toIntOrNull() ?: 0
        _productoEnEdicion.update { it.copy(stock = stockInt) }
    }

    // Limpia el estado para dar de alta un producto nuevo
    fun prepararNuevoProducto() {
        precioTexto = ""
        stockTexto = ""
        _productoEnEdicion.value = Producto(
            id = 0,
            nombre = "",
            descripcion = "",
            precio = 0.0,
            stock = 0,
            imagenResId = 0
        )
    }

    // Precarga los datos de un producto existente para modificarlo
    fun seleccionarProductoParaEdicion(producto: Producto) {
        precioTexto = if (producto.precio == 0.0) "" else producto.precio.toString()
        stockTexto = if (producto.stock == 0) "" else producto.stock.toString()
        _productoEnEdicion.value = producto.copy()
    }

    // Inserta el producto nuevo en Room (con id = 0 para que Room lo autogenere)
    fun guardarNuevoProducto() {
        val nuevoProducto = _productoEnEdicion.value.copy(id = 0)
        viewModelScope.launch {
            productoDao.insertar(nuevoProducto)
        }
        prepararNuevoProducto()
    }

    // Actualiza el producto existente en Room
    fun guardarEdicionProducto() {
        val editado = _productoEnEdicion.value
        if (editado.id != 0) {
            viewModelScope.launch {
                productoDao.actualizar(editado)
            }
        }
        prepararNuevoProducto()
    }

    // Elimina el producto seleccionado de Room
    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            productoDao.eliminar(producto)
        }
    }
}