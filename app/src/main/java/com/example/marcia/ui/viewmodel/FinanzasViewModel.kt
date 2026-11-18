package com.example.marcia.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marcia.data.Movimiento
import com.example.marcia.data.local.AppDatabase
import com.example.marcia.data.local.MovimientoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

sealed interface FiltroFinanzas {
    data class PorDia(val fecha: LocalDate = LocalDate.now()) : FiltroFinanzas
    data class PorRango(val inicio: LocalDate, val fin: LocalDate) : FiltroFinanzas
    data class PorMesAnio(val mes: Int, val anio: Int) : FiltroFinanzas
}

data class MovimientoFormState(
    val id: Int = 0,
    val tipo: String = "GASTO",
    val montoTexto: String = "",
    val categoria: String = "Insumos de estética y aparatología",
    val descripcion: String = "",
    val fechaMillis: Long = System.currentTimeMillis(),
    val horaTexto: String = "",
    val imagenUri: String? = null
)

data class ResumenFinanciero(
    val movimientos: List<Movimiento> = emptyList(),
    val totalIngresos: Double = 0.0,
    val totalGastos: Double = 0.0,
    val gananciaNeta: Double = 0.0,
    val gastosPorCategoria: Map<String, Double> = emptyMap()
)

@OptIn(ExperimentalCoroutinesApi::class)
class FinanzasViewModel(private val movimientoDao: MovimientoDao) : ViewModel() {

    private val _filtro = MutableStateFlow<FiltroFinanzas>(FiltroFinanzas.PorDia(LocalDate.now()))
    val filtro: StateFlow<FiltroFinanzas> = _filtro.asStateFlow()

    val resumen: StateFlow<ResumenFinanciero> = _filtro
        .flatMapLatest { filtroActual ->
            val (inicioMillis, finMillis) = calcularLimitesMillis(filtroActual)
            movimientoDao.obtenerPorRango(inicioMillis, finMillis)
        }
        .map { lista ->
            val ingresos = lista.filter { it.tipo == "INGRESO" }.sumOf { it.monto }
            val gastos = lista.filter { it.tipo == "GASTO" }.sumOf { it.monto }
            val porCategoria = lista
                .filter { it.tipo == "GASTO" }
                .groupBy { it.categoria }
                .mapValues { entry -> entry.value.sumOf { it.monto } }

            ResumenFinanciero(
                movimientos = lista,
                totalIngresos = ingresos,
                totalGastos = gastos,
                gananciaNeta = ingresos - gastos,
                gastosPorCategoria = porCategoria
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ResumenFinanciero()
        )

    private val _formState = MutableStateFlow(MovimientoFormState())
    val formState: StateFlow<MovimientoFormState> = _formState.asStateFlow()

    // --- Filtros de fecha ---
    fun seleccionarDia(utcMillis: Long) {
        val fechaLocal = Instant.ofEpochMilli(utcMillis).atZone(ZoneOffset.UTC).toLocalDate()
        _filtro.value = FiltroFinanzas.PorDia(fechaLocal)
    }

    fun seleccionarHoy() {
        _filtro.value = FiltroFinanzas.PorDia(LocalDate.now())
    }

    fun seleccionarRango(inicioUtcMillis: Long, finUtcMillis: Long) {
        val inicio = Instant.ofEpochMilli(inicioUtcMillis).atZone(ZoneOffset.UTC).toLocalDate()
        val fin = Instant.ofEpochMilli(finUtcMillis).atZone(ZoneOffset.UTC).toLocalDate()
        _filtro.value = FiltroFinanzas.PorRango(inicio, fin)
    }

    fun seleccionarMesAnio(mes: Int, anio: Int) {
        _filtro.value = FiltroFinanzas.PorMesAnio(mes, anio)
    }

    fun obtenerEtiquetaFiltro(filtroActual: FiltroFinanzas): String {
        val fmtCorto = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val meses = listOf(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        )
        return when (filtroActual) {
            is FiltroFinanzas.PorDia -> {
                if (filtroActual.fecha == LocalDate.now()) "Hoy (${filtroActual.fecha.format(fmtCorto)})"
                else "Día: ${filtroActual.fecha.format(fmtCorto)}"
            }
            is FiltroFinanzas.PorRango -> "${filtroActual.inicio.format(fmtCorto)} al ${filtroActual.fin.format(fmtCorto)}"
            is FiltroFinanzas.PorMesAnio -> "${meses[filtroActual.mes - 1]} ${filtroActual.anio}"
        }
    }

    private fun calcularLimitesMillis(filtroActual: FiltroFinanzas): Pair<Long, Long> {
        val zona = ZoneId.systemDefault()
        return when (filtroActual) {
            is FiltroFinanzas.PorDia -> {
                val inicio = filtroActual.fecha.atStartOfDay(zona).toInstant().toEpochMilli()
                val fin = filtroActual.fecha.plusDays(1).atStartOfDay(zona).toInstant().toEpochMilli() - 1
                inicio to fin
            }
            is FiltroFinanzas.PorRango -> {
                val inicio = filtroActual.inicio.atStartOfDay(zona).toInstant().toEpochMilli()
                val fin = filtroActual.fin.plusDays(1).atStartOfDay(zona).toInstant().toEpochMilli() - 1
                inicio to fin
            }
            is FiltroFinanzas.PorMesAnio -> {
                val ym = YearMonth.of(filtroActual.anio, filtroActual.mes)
                val inicio = ym.atDay(1).atStartOfDay(zona).toInstant().toEpochMilli()
                val fin = ym.atEndOfMonth().plusDays(1).atStartOfDay(zona).toInstant().toEpochMilli() - 1
                inicio to fin
            }
        }
    }

    // --- Campos del Formulario ---
    fun onMontoChange(v: String) {
        // Permite solo dígitos y un punto decimal
        val limpio = v.filter { it.isDigit() || it == '.' }
        _formState.update { it.copy(montoTexto = limpio) }
    }

    fun onCategoriaChange(v: String) { _formState.update { it.copy(categoria = v) } }
    fun onDescripcionChange(v: String) { _formState.update { it.copy(descripcion = v) } }
    fun onImagenUriChange(uri: String?) { _formState.update { it.copy(imagenUri = uri) } }

    fun prepararNuevoGasto() {
        val fechaBaseMillis = when (val f = _filtro.value) {
            is FiltroFinanzas.PorDia -> {
                if (f.fecha == LocalDate.now()) System.currentTimeMillis()
                else f.fecha.atTime(12, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            }
            else -> System.currentTimeMillis()
        }
        _formState.value = MovimientoFormState(
            id = 0,
            tipo = "GASTO",
            montoTexto = "",
            categoria = "Insumos de estética y aparatología",
            descripcion = "",
            fechaMillis = fechaBaseMillis,
            horaTexto = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + " hs",
            imagenUri = null
        )
    }

    fun seleccionarMovimientoParaEdicion(movimiento: Movimiento) {
        val montoFormateado = if (movimiento.monto % 1.0 == 0.0) {
            movimiento.monto.toLong().toString()
        } else {
            movimiento.monto.toString()
        }
        _formState.value = MovimientoFormState(
            id = movimiento.id,
            tipo = movimiento.tipo,
            montoTexto = montoFormateado,
            categoria = movimiento.categoria,
            descripcion = movimiento.descripcion,
            fechaMillis = movimiento.fechaMillis,
            horaTexto = movimiento.horaTexto,
            imagenUri = movimiento.imagenUri
        )
    }

    fun guardarMovimiento() {
        val actual = _formState.value
        val montoDouble = actual.montoTexto.toDoubleOrNull() ?: 0.0
        if (montoDouble <= 0.0) return

        val entidad = Movimiento(
            id = actual.id,
            tipo = actual.tipo,
            monto = montoDouble,
            categoria = actual.categoria,
            descripcion = actual.descripcion.ifBlank { if (actual.tipo == "GASTO") "Gasto general" else "Cobro de servicio" },
            fechaMillis = actual.fechaMillis,
            horaTexto = actual.horaTexto.ifBlank { LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + " hs" },
            imagenUri = actual.imagenUri
        )

        viewModelScope.launch {
            if (entidad.id == 0) {
                movimientoDao.insertar(entidad)
            } else {
                movimientoDao.actualizar(entidad)
            }
            prepararNuevoGasto()
        }
    }

    fun eliminarMovimiento(movimiento: Movimiento) {
        viewModelScope.launch {
            movimientoDao.eliminar(movimiento)
        }
    }

    // --- Utilidades de Imagen ---
    fun crearUriTemporalCamara(context: Context): Uri {
        val carpeta = File(context.filesDir, "comprobantes").apply { mkdirs() }
        val archivo = File(carpeta, "foto_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", archivo)
    }

    fun copiarImagenGaleriaAInterno(context: Context, sourceUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val carpeta = File(context.filesDir, "comprobantes").apply { mkdirs() }
                val destino = File(carpeta, "gal_${System.currentTimeMillis()}.jpg")
                context.contentResolver.openInputStream(sourceUri)?.use { input ->
                    destino.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                val uriGuardada = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", destino)
                withContext(Dispatchers.Main) {
                    onImagenUriChange(uriGuardada.toString())
                }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) {
                    onImagenUriChange(sourceUri.toString())
                }
            }
        }
    }

    companion object {
        fun formatearPesos(valor: Double): String {
            val formato = NumberFormat.getNumberInstance(Locale("es", "AR")).apply {
                maximumFractionDigits = 0
                minimumFractionDigits = 0
            }
            return "$ ${formato.format(valor)}"
        }

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                val database = AppDatabase.getDatabase(application)
                FinanzasViewModel(database.movimientoDao())
            }
        }
    }
}