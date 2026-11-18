package com.example.marcia.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movimientos")
data class Movimiento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tipo: String = "GASTO", // "GASTO" o "INGRESO"
    val monto: Double = 0.0,
    val categoria: String = "Insumos de estética y aparatología",
    val descripcion: String = "", // Proveedor / Comercio o concepto
    val fechaMillis: Long = System.currentTimeMillis(),
    val horaTexto: String = "",
    val imagenUri: String? = null
)