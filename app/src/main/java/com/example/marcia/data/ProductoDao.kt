package com.example.marcia.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO): define cómo se lee y escribe en la tabla "productos".
 * Room arma la implementación real por detrás cuando compila el proyecto con KSP.
 */
@Dao
interface ProductoDao {

    // Devuelve un Flow reactivo: cada vez que cambia la tabla, emite la lista nueva ordenadita por nombre
    @Query("SELECT * FROM productos ORDER BY nombre ASC")
    fun observarProductos(): Flow<List<Producto>>

    // Trae todos los productos una sola vez (suspend para correr fuera del hilo principal)
    @Query("SELECT * FROM productos")
    suspend fun obtenerTodos(): List<Producto>

    // Inserta un producto nuevo; si hay conflicto de ID, lo reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(producto: Producto): Long

    // Inserta una lista de productos de una (útil para los datos iniciales si está vacía)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVarios(productos: List<Producto>)

    // Actualiza un producto existente buscándolo por su @PrimaryKey (id)
    @Update
    suspend fun actualizar(producto: Producto)

    // Elimina de la base el producto que le pases
    @Delete
    suspend fun eliminar(producto: Producto)
}