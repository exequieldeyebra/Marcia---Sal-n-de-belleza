package com.example.marcia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marcia.data.Servicio
import com.example.marcia.data.local.AppDatabase
import com.example.marcia.data.local.ServicioDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServiciosViewModel(private val servicioDao: ServicioDao) : ViewModel() {

    // Convierte el Flow de Room en un StateFlow observable por Compose
    val servicios: StateFlow<List<Servicio>> = servicioDao.obtenerTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _state = MutableStateFlow(
        Servicio(nombre = "", precio = "", duracion = "60 minutos", descripcion = "", categoria = "Faciales")
    )
    val state: StateFlow<Servicio> = _state.asStateFlow()

    fun onNombreChange(v: String) { _state.update { it.copy(nombre = v) } }
    fun onPrecioChange(v: String) { _state.update { it.copy(precio = v) } }
    fun onDuracionChange(v: String) { _state.update { it.copy(duracion = v) } }
    fun onCategoriaChange(v: String) { _state.update { it.copy(categoria = v) } }
    fun onDescripcionChange(v: String) { _state.update { it.copy(descripcion = v) } }

    fun prepararNuevoRegistro() {
        _state.value = Servicio(id = 0, nombre = "", precio = "", duracion = "60 minutos", descripcion = "", categoria = "Faciales")
    }

    fun seleccionarServicioParaEdicion(servicio: Servicio) {
        _state.value = servicio.copy()
    }

    fun guardarNuevoServicio() {
        val nuevoServicio = _state.value.copy(id = 0) // id = 0 fuerza el autogenerado en Room
        viewModelScope.launch {
            servicioDao.insertar(nuevoServicio)
            prepararNuevoRegistro()
        }
    }

    fun guardarEdicionServicio() {
        val servicioActualizado = _state.value.copy()
        viewModelScope.launch {
            servicioDao.actualizar(servicioActualizado)
            prepararNuevoRegistro()
        }
    }

    fun eliminarServicio(servicio: Servicio) {
        viewModelScope.launch {
            servicioDao.eliminar(servicio)
        }
    }

    // Factory para inyectar el DAO automáticamente desde Compose
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                val database = AppDatabase.getDatabase(application)
                ServiciosViewModel(database.servicioDao())
            }
        }
    }
}