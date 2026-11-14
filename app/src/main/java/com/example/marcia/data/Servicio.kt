package com.example.marcia.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicios")
data class Servicio(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var nombre: String,
    var precio: String,
    var duracion: String,
    var descripcion: String,
    var categoria: String = "General",
    var personal: String = ""
)