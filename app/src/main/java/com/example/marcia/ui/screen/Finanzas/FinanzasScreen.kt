package com.example.marcia.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marcia.data.Movimiento
import com.example.marcia.ui.screen.Finanzas.FormularioComprobanteContent
import com.example.marcia.ui.theme.Success
import com.example.marcia.ui.viewmodel.FinanzasViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanzasScreen(
    viewModel: FinanzasViewModel = viewModel(factory = FinanzasViewModel.Factory),
    onNavigateToEstadisticas: () -> Unit = {}
) {
    val context = LocalContext.current
    val resumen by viewModel.resumen.collectAsStateWithLifecycle()
    val filtroActual by viewModel.filtro.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()

    // Estados persistentes ante rotación de pantalla
    var mostrarModalNuevoGasto by rememberSaveable { mutableStateOf(false) }
    var mostrarModalEdicion by rememberSaveable { mutableStateOf(false) }
    var idMovimientoAEliminar by rememberSaveable { mutableStateOf<Int?>(null) }

    // Estados de los selectores de fecha
    var mostrarPickerDia by rememberSaveable { mutableStateOf(false) }
    var mostrarPickerRango by rememberSaveable { mutableStateOf(false) }
    var mostrarPickerMesAnio by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                // Tarjeta de Balance + Acceso al Gráfico de Torta
                BalanceHeroCard(
                    etiquetaPeriodo = viewModel.obtenerEtiquetaFiltro(filtroActual),
                    neto = FinanzasViewModel.formatearPesos(resumen.gananciaNeta),
                    ingresos = "+ ${FinanzasViewModel.formatearPesos(resumen.totalIngresos)}",
                    gastos = "- ${FinanzasViewModel.formatearPesos(resumen.totalGastos)}",
                    onVerGraficoClick = onNavigateToEstadisticas
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Barra de Filtros de Fecha (Día / Rango / Mes-Año)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = false,
                        onClick = { mostrarPickerDia = true },
                        label = { Text("Día") },
                        leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(16.dp)) },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = false,
                        onClick = { mostrarPickerRango = true },
                        label = { Text("Rango") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.size(16.dp)) },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = false,
                        onClick = { mostrarPickerMesAnio = true },
                        label = { Text("Mes/Año") },
                        leadingIcon = { Icon(Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(16.dp)) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Botón Registrar Comprobante de Gasto
                Button(
                    onClick = {
                        viewModel.prepararNuevoGasto()
                        mostrarModalNuevoGasto = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Registrar Comprobante de Gasto")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Movimientos (${resumen.movimientos.size})", style = MaterialTheme.typography.titleMedium)
                    TextButton(onClick = { viewModel.seleccionarHoy() }) {
                        Text("Ver Hoy", style = MaterialTheme.typography.labelMedium)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (resumen.movimientos.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                    ) {
                        Text(
                            text = "No hay movimientos registrados para el período seleccionado.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            } else {
                items(resumen.movimientos, key = { it.id }) { movimiento ->
                    TransaccionItem(
                        movimiento = movimiento,
                        onClick = {
                            viewModel.seleccionarMovimientoParaEdicion(movimiento)
                            mostrarModalEdicion = true
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }

        // --- MODAL: NUEVO GASTO ---
        if (mostrarModalNuevoGasto) {
            ModalBottomSheet(
                onDismissRequest = { mostrarModalNuevoGasto = false },
                containerColor = MaterialTheme.colorScheme.surface,
                scrimColor = Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                FormularioComprobanteContent(
                    formState = formState,
                    esEdicion = false,
                    onMontoChange = viewModel::onMontoChange,
                    onCategoriaChange = viewModel::onCategoriaChange,
                    onDescripcionChange = viewModel::onDescripcionChange,
                    onCrearUriCamara = { viewModel.crearUriTemporalCamara(context) },
                    onFotoTomada = { viewModel.onImagenUriChange(it) },
                    onImagenGaleriaSeleccionada = { viewModel.copiarImagenGaleriaAInterno(context, it) },
                    onEliminarImagen = { viewModel.onImagenUriChange(null) },
                    onConfirmar = {
                        viewModel.guardarMovimiento()
                        mostrarModalNuevoGasto = false
                    },
                    onDismiss = { mostrarModalNuevoGasto = false }
                )
            }
        }

        // --- MODAL: VER / EDITAR / ELIMINAR MOVIMIENTO ---
        if (mostrarModalEdicion) {
            ModalBottomSheet(
                onDismissRequest = { mostrarModalEdicion = false },
                containerColor = MaterialTheme.colorScheme.surface,
                scrimColor = Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                FormularioComprobanteContent(
                    formState = formState,
                    esEdicion = true,
                    onMontoChange = viewModel::onMontoChange,
                    onCategoriaChange = viewModel::onCategoriaChange,
                    onDescripcionChange = viewModel::onDescripcionChange,
                    onCrearUriCamara = { viewModel.crearUriTemporalCamara(context) },
                    onFotoTomada = { viewModel.onImagenUriChange(it) },
                    onImagenGaleriaSeleccionada = { viewModel.copiarImagenGaleriaAInterno(context, it) },
                    onEliminarImagen = { viewModel.onImagenUriChange(null) },
                    onConfirmar = {
                        viewModel.guardarMovimiento()
                        mostrarModalEdicion = false
                    },
                    onEliminarRegistro = {
                        idMovimientoAEliminar = formState.id
                        mostrarModalEdicion = false
                    },
                    onDismiss = { mostrarModalEdicion = false }
                )
            }
        }

        // --- DIÁLOGO CONFIRMACIÓN ELIMINAR ---
        val movimientoAEliminar = remember(idMovimientoAEliminar, resumen.movimientos) {
            resumen.movimientos.find { it.id == idMovimientoAEliminar }
        }
        if (movimientoAEliminar != null) {
            AlertDialog(
                onDismissRequest = { idMovimientoAEliminar = null },
                containerColor = MaterialTheme.colorScheme.surface,
                title = { Text("Eliminar Movimiento", color = MaterialTheme.colorScheme.primary) },
                text = {
                    Text(
                        "¿Deseas eliminar '${movimientoAEliminar.descripcion}' por ${FinanzasViewModel.formatearPesos(movimientoAEliminar.monto)}?",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.eliminarMovimiento(movimientoAEliminar)
                        idMovimientoAEliminar = null
                    }) {
                        Text("Eliminar", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { idMovimientoAEliminar = null }) {
                        Text("Cancelar", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            )
        }

        // --- SELECTOR DE DÍA ÚNICO ---
        if (mostrarPickerDia) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { mostrarPickerDia = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { viewModel.seleccionarDia(it) }
                        mostrarPickerDia = false
                    }) { Text("Aplicar") }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarPickerDia = false }) { Text("Cancelar") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        // --- SELECTOR DE RANGO DE FECHAS ---
        if (mostrarPickerRango) {
            val rangeState = rememberDateRangePickerState()
            DatePickerDialog(
                onDismissRequest = { mostrarPickerRango = false },
                confirmButton = {
                    TextButton(
                        enabled = rangeState.selectedStartDateMillis != null && rangeState.selectedEndDateMillis != null,
                        onClick = {
                            val inicio = rangeState.selectedStartDateMillis
                            val fin = rangeState.selectedEndDateMillis
                            if (inicio != null && fin != null) {
                                viewModel.seleccionarRango(inicio, fin)
                            }
                            mostrarPickerRango = false
                        }
                    ) { Text("Filtrar Rango") }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarPickerRango = false }) { Text("Cancelar") }
                }
            ) {
                DateRangePicker(
                    state = rangeState,
                    modifier = Modifier.height(460.dp)
                )
            }
        }

        // --- SELECTOR DE MES Y AÑO ---
        if (mostrarPickerMesAnio) {
            DialogoSeleccionMesAnio(
                onDismiss = { mostrarPickerMesAnio = false },
                onConfirmar = { mes, anio ->
                    viewModel.seleccionarMesAnio(mes, anio)
                    mostrarPickerMesAnio = false
                }
            )
        }
    }
}

@Composable
fun BalanceHeroCard(
    etiquetaPeriodo: String,
    neto: String,
    ingresos: String,
    gastos: String,
    onVerGraficoClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("GANANCIA NETA • ${etiquetaPeriodo.uppercase()}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(neto, style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
                FilledTonalIconButton(onClick = onVerGraficoClick) {
                    Icon(Icons.Default.PieChart, contentDescription = "Ver gráfico financiero")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                Column {
                    Text("Ingresos", style = MaterialTheme.typography.labelSmall)
                    Text(ingresos, style = MaterialTheme.typography.titleMedium, color = Success, fontWeight = FontWeight.SemiBold)
                }
                Column {
                    Text("Gastos", style = MaterialTheme.typography.labelSmall)
                    Text(gastos, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun TransaccionItem(
    movimiento: Movimiento,
    onClick: () -> Unit
) {
    val esIngreso = movimiento.tipo == "INGRESO"
    val signo = if (esIngreso) "+" else "-"
    val colorMonto = if (esIngreso) Success else MaterialTheme.colorScheme.secondary

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = if (esIngreso) "Cobro" else "Gasto • ${movimiento.categoria}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (movimiento.imagenUri != null) {
                        Icon(
                            imageVector = Icons.Default.Receipt,
                            contentDescription = "Tiene comprobante",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
                Text(
                    text = movimiento.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$signo ${FinanzasViewModel.formatearPesos(movimiento.monto)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = colorMonto,
                    fontWeight = FontWeight.SemiBold
                )
                Text(movimiento.horaTexto, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun DialogoSeleccionMesAnio(
    onDismiss: () -> Unit,
    onConfirmar: (Int, Int) -> Unit
) {
    val hoy = LocalDate.now()
    var mesSeleccionado by remember { mutableIntStateOf(hoy.monthValue) }
    var anioSeleccionado by remember { mutableIntStateOf(hoy.year) }

    val meses = listOf("Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic")
    val anios = (hoy.year - 2..hoy.year + 1).toList()

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        title = { Text("Filtrar por Mes y Año", color = MaterialTheme.colorScheme.primary) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Año", style = MaterialTheme.typography.labelMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    anios.forEach { anio ->
                        FilterChip(
                            selected = anioSeleccionado == anio,
                            onClick = { anioSeleccionado = anio },
                            label = { Text(anio.toString()) }
                        )
                    }
                }
                Text("Mes", style = MaterialTheme.typography.labelMedium)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    meses.chunked(4).forEachIndexed { filaIndex, filaMeses ->
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                            filaMeses.forEachIndexed { colIndex, nombreMes ->
                                val numeroMes = filaIndex * 4 + colIndex + 1
                                FilterChip(
                                    selected = mesSeleccionado == numeroMes,
                                    onClick = { mesSeleccionado = numeroMes },
                                    label = { Text(nombreMes) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirmar(mesSeleccionado, anioSeleccionado) }) {
                Text("Aplicar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}