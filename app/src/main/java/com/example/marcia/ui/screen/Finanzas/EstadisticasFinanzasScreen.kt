package com.example.marcia.ui.screen.Finanzas

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.marcia.ui.theme.Success
import com.example.marcia.ui.viewmodel.FinanzasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstadisticasFinanzasScreen(
    viewModel: FinanzasViewModel,
    onNavigateBack: () -> Unit
) {
    val resumen by viewModel.resumen.collectAsStateWithLifecycle()
    val filtroActual by viewModel.filtro.collectAsStateWithLifecycle()

    val totalVolumen = resumen.totalIngresos + resumen.totalGastos
    val proporcionIngresos = if (totalVolumen > 0) (resumen.totalIngresos / totalVolumen).toFloat() else 0f
    val proporcionGastos = if (totalVolumen > 0) (resumen.totalGastos / totalVolumen).toFloat() else 0f

    val animacionProgreso by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 700),
        label = "animacion_torta"
    )

    val colorIngresos = Success
    val colorGastos = MaterialTheme.colorScheme.secondary
    val colorVacio = MaterialTheme.colorScheme.surfaceVariant

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Balance Financiero", style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = viewModel.obtenerEtiquetaFiltro(filtroActual),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tarjeta Principal con Gráfico de Torta Nativo (Canvas)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(220.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val grosorAnillo = 36.dp.toPx()
                                val diametro = size.minDimension - grosorAnillo
                                val topLeft = Offset(
                                    (size.width - diametro) / 2f,
                                    (size.height - diametro) / 2f
                                )
                                val arcSize = Size(diametro, diametro)

                                if (totalVolumen <= 0.0) {
                                    drawArc(
                                        color = colorVacio,
                                        startAngle = 0f,
                                        sweepAngle = 360f,
                                        useCenter = false,
                                        topLeft = topLeft,
                                        size = arcSize,
                                        style = Stroke(width = grosorAnillo)
                                    )
                                } else {
                                    val barridoIngresos = 360f * proporcionIngresos * animacionProgreso
                                    val barridoGastos = 360f * proporcionGastos * animacionProgreso

                                    // Arco de Ingresos
                                    drawArc(
                                        color = colorIngresos,
                                        startAngle = -90f,
                                        sweepAngle = barridoIngresos,
                                        useCenter = false,
                                        topLeft = topLeft,
                                        size = arcSize,
                                        style = Stroke(width = grosorAnillo, cap = StrokeCap.Butt)
                                    )

                                    // Arco de Gastos (Egresos)
                                    drawArc(
                                        color = colorGastos,
                                        startAngle = -90f + barridoIngresos,
                                        sweepAngle = barridoGastos,
                                        useCenter = false,
                                        topLeft = topLeft,
                                        size = arcSize,
                                        style = Stroke(width = grosorAnillo, cap = StrokeCap.Butt)
                                    )
                                }
                            }

                            // Centro del gráfico: Ganancia Neta
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "GANANCIA NETA",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = FinanzasViewModel.formatearPesos(resumen.gananciaNeta),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = if (resumen.gananciaNeta >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Leyenda detallada
                        LeyendaFinancieraRow(
                            color = colorIngresos,
                            titulo = "Ingresos",
                            porcentaje = "${(proporcionIngresos * 100).toInt()}%",
                            monto = FinanzasViewModel.formatearPesos(resumen.totalIngresos)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        LeyendaFinancieraRow(
                            color = colorGastos,
                            titulo = "Egresos (Gastos)",
                            porcentaje = "${(proporcionGastos * 100).toInt()}%",
                            monto = FinanzasViewModel.formatearPesos(resumen.totalGastos)
                        )
                    }
                }
            }

            // Desglose de Gastos por Categoría
            if (resumen.gastosPorCategoria.isNotEmpty()) {
                item {
                    Text(
                        text = "Distribución de Gastos por Categoría",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                items(resumen.gastosPorCategoria.toList()) { (categoria, montoCat) ->
                    val porcentajeCat = if (resumen.totalGastos > 0) (montoCat / resumen.totalGastos).toFloat() else 0f
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(categoria, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
                                Text(
                                    FinanzasViewModel.formatearPesos(montoCat),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            LinearProgressIndicator(
                                progress = { porcentajeCat },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                strokeCap = StrokeCap.Round
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun LeyendaFinancieraRow(
    color: Color,
    titulo: String,
    porcentaje: String,
    monto: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color, CircleShape)
            )
            Text(titulo, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Text(
                text = "($porcentaje)",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(monto, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    }
}