package com.example.marcia.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.marcia.data.Empleado
import com.example.marcia.data.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EmpleadosViewModel : ViewModel() {

    val listadoEmpleados = mutableStateListOf<Empleado>()

    private val _state = MutableStateFlow(Empleado())
    private val _stateUsuario = MutableStateFlow(Usuario())

    val state: StateFlow<Empleado> = _state.asStateFlow()
    val stateUsuario: StateFlow<Usuario> = _stateUsuario.asStateFlow()

    fun onNombreChange(v: String) { _stateUsuario.update { it.copy(nome = v) } }
    fun onApellidoChange(v: String) { _stateUsuario.update { it.copy(apellido = v) } }
    fun onDniChange(v: String) { _stateUsuario.update { it.copy(dni = v) } }
    fun onRolChange(v: String) { _state.update { it.copy(role = v) } }

    val empleadoSeleccionado = mutableSetOf<Empleado>()

    init {
        cargarEmpleados()
    }

    fun cargarEmpleados() {
        if (listadoEmpleados.isEmpty()) {
            listadoEmpleados.addAll(
                listOf(
                    Empleado(1, Usuario(id = 1, nome = "Joaquin", apellido = "Navarrete", dni = "12345678"), "Estilista", "4 turnos hoy", "4.67"),
                    Empleado(2, Usuario(id = 2, nome = "Baltasar", apellido = "Elli", dni = "87654321"), "Peluquero", "1 turnos hoy", "4.50"),
                    Empleado(3, Usuario(id = 3, nome = "Exequiel", apellido = "De Yebra", dni = "11223344"), "Barbero", "3 turnos hoy", "4.80")
                )
            )
        }
    }

    fun prepararNuevoRegistro() {
        _state.value = Empleado()
        _stateUsuario.value = Usuario()
    }

    fun seleccionarEmpleadoParaEdicion(empleado: Empleado) {
        _state.value = empleado.copy()
        _stateUsuario.value = empleado.UsuarioId.copy()
    }

    fun agregarEmpleado() {
        val nuevoId = (listadoEmpleados.maxOfOrNull { it.id } ?: 0) + 1
        val nuevoEmpleado = _state.value.copy(
            id = nuevoId,
            UsuarioId = _stateUsuario.value,
            shifts = "0 turnos hoy",
            rating = "5.00"
        )
        listadoEmpleados.add(nuevoEmpleado)

        prepararNuevoRegistro()
    }

    fun guardarEdicionEmpleado() {
        val idEditando = _state.value.id
        val index = listadoEmpleados.indexOfFirst { it.id == idEditando }
        if (index != -1) {
            val empleadoEditado = _state.value.copy(
                UsuarioId = _stateUsuario.value
            )
            listadoEmpleados[index] = empleadoEditado
        }
        prepararNuevoRegistro()
    }

    fun eliminarEmpleado(empleado: Empleado) {
        listadoEmpleados.remove(empleado)
    }
}
