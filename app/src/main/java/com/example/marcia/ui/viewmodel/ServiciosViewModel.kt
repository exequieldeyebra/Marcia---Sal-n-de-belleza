package com.example.marcia.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.marcia.data.Servicio

class ServiciosViewModel : ViewModel() {

    // Lista mutable reactiva de servicios (sobrevive a la rotación)
    var servicios = mutableStateListOf<Servicio>()
        private set

    init {
        cargarServicios()
    }

    private fun cargarServicios() {
        if (servicios.isEmpty()) {
            servicios.addAll(
                listOf(
                    Servicio(
                        id = 1,
                        nombre = "Tratamiento Facial Glow",
                        precio = "$28.500",
                        duracion = "60m",
                        descripcion = "Protocolo biorevitalizante con ácido hialurónico...",
                        categoria = "Faciales",
                        personal = "Dra. Elena S., Mariana R."
                    ),
                    Servicio(
                        id = 2,
                        nombre = "Drenaje Linfático Manual",
                        precio = "$22.000",
                        duracion = "90m",
                        descripcion = "Técnica especializada Método Vodder...",
                        categoria = "Corporales",
                        personal = "Lic. Sofía V., Lucas G."
                    ),
                    Servicio(
                        id = 3,
                        nombre = "Masaje Descontracturante",
                        precio = "$18.000",
                        duracion = "45m",
                        descripcion = "Masaje profundo para liberar tensión muscular en espalda y cuello.",
                        categoria = "Corporales",
                        personal = "Lucas G."
                    ),
                    Servicio(
                        id = 4,
                        nombre = "Peeling Químico Renovador",
                        precio = "$25.000",
                        duracion = "50m",
                        descripcion = "Exfoliación profunda para atenuar manchas y renovar la piel.",
                        categoria = "Faciales",
                        personal = "Dra. Elena S."
                    )
                )
            )
        }
    }

    fun agregarServicio(servicio: Servicio) {
        servicios.add(servicio)
    }

    fun eliminarServicio(servicio: Servicio) {
        servicios.remove(servicio)
    }
}
