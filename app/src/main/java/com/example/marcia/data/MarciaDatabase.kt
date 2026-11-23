package com.example.marcia.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Base de datos local de Room para la aplicación Marcia.
 */
@Database(
    entities = [Producto::class],
    version = 1,
    exportSchema = false
)
abstract class MarciaDatabase : RoomDatabase() {

    // Expone el DAO de productos
    abstract fun productoDao(): ProductoDao

    companion object {
        // Instancia única compartida de forma segura entre hilos
        @Volatile
        private var INSTANCE: MarciaDatabase? = null

        fun obtenerInstancia(context: Context): MarciaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    MarciaDatabase::class.java,
                    "marcia_salon_db"
                )
                    // Recrea tablas en desarrollo si cambiás la versión sin migración manual
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()

                INSTANCE = instancia
                instancia
            }
        }
    }
}