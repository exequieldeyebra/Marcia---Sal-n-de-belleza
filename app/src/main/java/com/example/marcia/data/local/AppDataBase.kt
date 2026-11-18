package com.example.marcia.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.marcia.data.Movimiento
import com.example.marcia.data.Servicio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Database(
    entities = [Servicio::class, Movimiento::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun servicioDao(): ServicioDao
    abstract fun movimientoDao(): MovimientoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "marcia_salon_db"
                )
                    .fallbackToDestructiveMigration(true)
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    val servicioDao = database.servicioDao()
                    servicioDao.insertar(Servicio(nombre = "Tratamiento Facial Glow", precio = "28500", duracion = "60 minutos", descripcion = "Protocolo biorevitalizante con ácido hialurónico...", categoria = "Faciales", personal = "Dra. Elena S., Mariana R."))
                    servicioDao.insertar(Servicio(nombre = "Drenaje Linfático Manual", precio = "22000", duracion = "90 minutos", descripcion = "Técnica especializada Método Vodder...", categoria = "Corporales", personal = "Lic. Sofía V."))
                    servicioDao.insertar(Servicio(nombre = "Peeling Químico Renovador", precio = "25000", duracion = "50 minutos", descripcion = "Exfoliación profunda para atenuar manchas.", categoria = "Faciales", personal = "Dra. Elena S."))

                    val movimientoDao = database.movimientoDao()
                    val ahora = System.currentTimeMillis()
                    val horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + " hs"

                    movimientoDao.insertar(
                        Movimiento(
                            tipo = "INGRESO",
                            monto = 28500.0,
                            categoria = "Servicios en Salón",
                            descripcion = "Limpieza Facial Profunda",
                            fechaMillis = ahora,
                            horaTexto = horaActual
                        )
                    )
                    movimientoDao.insertar(
                        Movimiento(
                            tipo = "INGRESO",
                            monto = 44000.0,
                            categoria = "Servicios en Salón",
                            descripcion = "Drenaje Linfático (x2)",
                            fechaMillis = ahora - 3600_000,
                            horaTexto = "16:10 hs"
                        )
                    )
                    movimientoDao.insertar(
                        Movimiento(
                            tipo = "GASTO",
                            monto = 32000.0,
                            categoria = "Insumos de estética y aparatología",
                            descripcion = "Reposición Ampollas Hialurónico",
                            fechaMillis = ahora - 7200_000,
                            horaTexto = "15:40 hs"
                        )
                    )
                }
            }
        }
    }
}