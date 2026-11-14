package com.example.marcia.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.marcia.data.Servicio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Servicio::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun servicioDao(): ServicioDao

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
                    // Opcional: Precarga datos iniciales solo la primera vez que se crea la BD
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
                    val dao = database.servicioDao()
                    dao.insertar(Servicio(nombre = "Tratamiento Facial Glow", precio = "28500", duracion = "60 minutos", descripcion = "Protocolo biorevitalizante con ácido hialurónico...", categoria = "Faciales", personal = "Dra. Elena S., Mariana R."))
                    dao.insertar(Servicio(nombre = "Drenaje Linfático Manual", precio = "22000", duracion = "90 minutos", descripcion = "Técnica especializada Método Vodder...", categoria = "Corporales", personal = "Lic. Sofía V."))
                    dao.insertar(Servicio(nombre = "Peeling Químico Renovador", precio = "25000", duracion = "50 minutos", descripcion = "Exfoliación profunda para atenuar manchas.", categoria = "Faciales", personal = "Dra. Elena S."))
                }
            }
        }
    }
}