package com.example.marcia.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.marcia.data.Empleado

class EmpleadosViewModel : ViewModel() {

    // leer el libro
    val listadoEmpleados = mutableStateListOf<Empleado>()

    init {
        cargarEmpleados()
    }

    fun cargarEmpleados() {
        if (listadoEmpleados.isEmpty()) {
            listadoEmpleados.addAll(
                listOf(
                    Empleado("Joaquin", "Estilista", "4 turnos hoy", "4.67"),
                    Empleado("Balta", "Peluquero", "1 turnos hoy", "4.50"),
                    Empleado("Exequiel", "Barbero", "3 turnos hoy", "4.97")
                )
            )
        }
    }

    fun agregarEmpleado(empleado: Empleado) {
        listadoEmpleados.add(empleado)
    }

    fun eliminarEmpleado(empleado: Empleado) {
        listadoEmpleados.remove(empleado)
    }
}
