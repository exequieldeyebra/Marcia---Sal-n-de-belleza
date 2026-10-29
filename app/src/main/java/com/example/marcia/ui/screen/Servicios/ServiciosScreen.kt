package com.example.marcia.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marcia.data.Servicio
import com.example.marcia.ui.viewmodel.ServiciosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiciosScreen(
    viewModel: ServiciosViewModel = viewModel()
) {
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    // Variable de estado para controlar la visibilidad del modal de Registro
    var mostrarModalRegistro by remember { mutableStateOf(false) }

    val serviciosFiltrados = remember(categoriaSeleccionada, viewModel.servicios.toList()) {
        if (categoriaSeleccionada == "Todos") {
            viewModel.servicios
        } else {
            viewModel.servicios.filter { it.categoria == categoriaSeleccionada }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Text("CATÁLOGO ACTIVO", style = MaterialTheme.typography.labelMedium)
                Text(
                    "${viewModel.servicios.size} tratamientos activos",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Fila de categorías
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = categoriaSeleccionada == "Todos",
                        onClick = { categoriaSeleccionada = "Todos" },
                        label = { Text("Todos") }
                    )
                    FilterChip(
                        selected = categoriaSeleccionada == "Faciales",
                        onClick = { categoriaSeleccionada = "Faciales" },
                        label = { Text("Faciales") }
                    )
                    FilterChip(
                        selected = categoriaSeleccionada == "Corporales",
                        onClick = { categoriaSeleccionada = "Corporales" },
                        label = { Text("Corporales") }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(serviciosFiltrados, key = { it.id }) { servicio ->
                ServicioCardItem(servicio)
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Espacio al final para que el botón no tape el último item
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }

        // Botón flotante agregado en la parte inferior derecha
        ExtendedFloatingActionButton(
            onClick = { mostrarModalRegistro = true },
            text = { Text("Nuevo Servicio") },
            icon = { Icon(Icons.Filled.Add, contentDescription = "Nuevo Servicio") },
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )

        // Modal tipo Bottom Sheet[cite: 21]
        if (mostrarModalRegistro) {
            ModalBottomSheet(
                onDismissRequest = { mostrarModalRegistro = false },
                containerColor = MaterialTheme.colorScheme.surface,
                scrimColor = Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                RegistrarServicioScreen(
                    onDismiss = { mostrarModalRegistro = false }
                )
            }
        }
    }
}

@Composable
fun ServicioCardItem(servicio: Servicio) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    servicio.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(servicio.duracion, style = MaterialTheme.typography.labelSmall)
            }
            Text(
                servicio.precio,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                servicio.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (servicio.personal.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Personal: ${servicio.personal}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}