package com.example.marcia.domain

import com.example.marcia.data.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceUsuario {
    // GET: Obtiene recurso por ID
    @GET("api/v1/usuarios/{id}")
    suspend fun obtenerUsuario(@Path("id") userId: Int): Usuario

    // POST: Crea nuevo recurso enviando objeto JSON
    @POST("api/v1/usuarios")
    suspend fun crearUsuario(@Body usuario: Usuario): Response<Usuario>

    // PUT: Actualiza recurso existente
    @PUT("api/v1/usuarios/{id}")
    suspend fun actualizarUsuario(
        @Path("id") userId: Int,
        @Body usuario: Usuario
    ): Response<Usuario>
}