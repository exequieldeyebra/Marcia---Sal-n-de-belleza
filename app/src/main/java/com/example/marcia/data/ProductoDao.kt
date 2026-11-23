package com.example.marcia.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * DAO con las operaciones sobre la tabla "productos".
 * KSP genera la implementación real al compilar.
 */
@Dao
interface ProductoDao {

    // Flow reactivo: emite la lista actualizada automáticamente ante cualquier cambio en la tabla
    @Query("SELECT * FROM productos ORDER BY id ASC")
    fun observarProductos(): Flow<List<Producto>>

    // Trae todos los registros una vez (para chequear si la tabla está vacía)
    @Query("SELECT * FROM productos")
    suspend fun obtenerTodos(): List<Producto>

    // Inserta un producto nuevo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(producto: Producto): Long

    // Inserta varios productos de una (para los datos iniciales)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVarios(productos: List<Producto>)

    // Actualiza un producto existente según su @PrimaryKey (id)
    @Update
    suspend fun actualizar(producto: Producto)

    // Elimina el producto indicado de la base
    @Delete
    suspend fun eliminar(producto: Producto)
}