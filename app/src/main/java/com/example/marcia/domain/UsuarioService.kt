package com.example.marcia.domain

import com.example.marcia.data.Usuario
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsuarioService {

    private val retrofitUsuario = Retrofit.Builder()
        //Aqui se coloca la direccion ip o dominio del backend
        .baseUrl("http://localhost:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofitUsuario.create(ApiServiceUsuario::class.java)

    suspend fun obtenerUsuario(idUsuario: Int): Usuario{

        return apiService.obtenerUsuario(idUsuario)

    }
}