package com.example.marcia.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.marcia.data.Servicio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ServiciosViewModel : ViewModel() {

    var servicios = mutableStateListOf<Servicio>()
        private set

    // Estado reactivo para el formulario (Crear/Editar)
    private val _state = MutableStateFlow(Servicio(id = 0, nombre = "", precio = "", duracion = "60 minutos", descripcion = "", categoria = "Faciales", personal = ""))
    val state: StateFlow<Servicio> = _state.asStateFlow()

    init {
        cargarServicios()
    }

    private fun cargarServicios() {
        if (servicios.isEmpty()) {
            servicios.addAll(
                listOf(
                    Servicio(1, "Tratamiento Facial Glow", "28500", "60 minutos", "Protocolo biorevitalizante...", "Faciales", "Dra. Elena S., Mariana R."),
                    Servicio(2, "Drenaje Linfático Manual", "22000", "90 minutos", "Técnica especializada...", "Corporales", "Lic. Sofía V."),
                    Servicio(3, "Peeling Químico Renovador", "25000", "50 minutos", "Exfoliación profunda...", "Faciales", "Dra. Elena S.")
                )
            )
        }
    }

    // Actualizadores de estado desde la UI
    fun onNombreChange(v: String) { _state.update { it.copy(nombre = v) } }
    fun onPrecioChange(v: String) { _state.update { it.copy(precio = v) } }
    fun onDuracionChange(v: String) { _state.update { it.copy(duracion = v) } }
    fun onCategoriaChange(v: String) { _state.update { it.copy(categoria = v) } }
    fun onDescripcionChange(v: String) { _state.update { it.copy(descripcion = v) } }

    fun prepararNuevoRegistro() {
        _state.value = Servicio(id = 0, nombre = "", precio = "", duracion = "60 minutos", descripcion = "", categoria = "Faciales", personal = "")
    }

    fun seleccionarServicioParaEdicion(servicio: Servicio) {
        _state.value = servicio.copy()
    }

    fun guardarNuevoServicio() {
        val nuevoId = (servicios.maxOfOrNull { it.id } ?: 0) + 1
        val nuevoServicio = _state.value.copy(id = nuevoId)
        servicios.add(nuevoServicio)
        prepararNuevoRegistro()
    }

    fun guardarEdicionServicio() {
        val idEditando = _state.value.id
        val index = servicios.indexOfFirst { it.id == idEditando }
        if (index != -1) {
            servicios[index] = _state.value.copy()
        }
        prepararNuevoRegistro()
    }

    fun eliminarServicio(servicio: Servicio) {
        servicios.remove(servicio)
    }
}