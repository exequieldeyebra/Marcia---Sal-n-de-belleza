package com.example.marcia.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marcia.data.Servicio
import com.example.marcia.ui.viewmodel.ServiciosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiciosScreen(
    viewModel: ServiciosViewModel = viewModel()
) {
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    var mostrarModalRegistro by remember { mutableStateOf(false) }
    var servicioParaEditar by remember { mutableStateOf<Servicio?>(null) }

    // Nuevo estado para controlar el diálogo de confirmación
    var servicioParaEliminar by remember { mutableStateOf<Servicio?>(null) }

    val serviciosFiltrados = remember(categoriaSeleccionada, viewModel.servicios.toList()) {
        if (categoriaSeleccionada == "Todos") viewModel.servicios
        else viewModel.servicios.filter { it.categoria == categoriaSeleccionada }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            item {
                Text("CATÁLOGO ACTIVO", style = MaterialTheme.typography.labelMedium)
                Text("${viewModel.servicios.size} tratamientos", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Todos", "Faciales", "Corporales").forEach { cat ->
                        FilterChip(
                            selected = categoriaSeleccionada == cat,
                            onClick = { categoriaSeleccionada = cat },
                            label = { Text(cat) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(serviciosFiltrados, key = { it.id }) { servicio ->
                ServicioCardItem(
                    servicio = servicio,
                    onEdit = {
                        viewModel.seleccionarServicioParaEdicion(servicio)
                        servicioParaEditar = servicio
                    },
                    onDelete = {
                        // En lugar de borrar directamente, abrimos el diálogo de advertencia
                        servicioParaEliminar = servicio
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }

        ExtendedFloatingActionButton(
            onClick = {
                viewModel.prepararNuevoRegistro()
                mostrarModalRegistro = true
            },
            text = { Text("Nuevo Servicio") },
            icon = { Icon(Icons.Filled.Add, "Nuevo Servicio") },
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        )

        // Modal para CREAR
        if (mostrarModalRegistro) {
            ModalBottomSheet(
                onDismissRequest = { mostrarModalRegistro = false },
                containerColor = MaterialTheme.colorScheme.surface,
                scrimColor = Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                val state by viewModel.state.collectAsStateWithLifecycle()
                FormularioServicioContent(
                    servicioState = state,
                    tituloPanel = "Añadir Nuevo Servicio",
                    onNombreChange = viewModel::onNombreChange,
                    onPrecioChange = viewModel::onPrecioChange,
                    onDuracionChange = viewModel::onDuracionChange,
                    onCategoriaChange = viewModel::onCategoriaChange,
                    onDescripcionChange = viewModel::onDescripcionChange,
                    onConfirmar = {
                        viewModel.guardarNuevoServicio()
                        mostrarModalRegistro = false
                    },
                    onDismiss = { mostrarModalRegistro = false }
                )
            }
        }

        // Modal para EDITAR
        if (servicioParaEditar != null) {
            ModalBottomSheet(
                onDismissRequest = { servicioParaEditar = null },
                containerColor = MaterialTheme.colorScheme.surface,
                scrimColor = Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                val state by viewModel.state.collectAsStateWithLifecycle()
                FormularioServicioContent(
                    servicioState = state,
                    tituloPanel = "Modificar Servicio",
                    iconoPanel = Icons.Default.Spa,
                    onNombreChange = viewModel::onNombreChange,
                    onPrecioChange = viewModel::onPrecioChange,
                    onDuracionChange = viewModel::onDuracionChange,
                    onCategoriaChange = viewModel::onCategoriaChange,
                    onDescripcionChange = viewModel::onDescripcionChange,
                    onConfirmar = {
                        viewModel.guardarEdicionServicio()
                        servicioParaEditar = null
                    },
                    onDismiss = { servicioParaEditar = null }
                )
            }
        }

        // Diálogo de Confirmación para ELIMINAR
        if (servicioParaEliminar != null) {
            AlertDialog(
                onDismissRequest = { servicioParaEliminar = null },
                containerColor = MaterialTheme.colorScheme.surface,
                title = {
                    Text(
                        "Eliminar Tratamiento",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                text = {
                    Text(
                        "¿Estás seguro de que deseas eliminar '${servicioParaEliminar?.nombre}'? Esta acción no se puede deshacer.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            servicioParaEliminar?.let { viewModel.eliminarServicio(it) }
                            servicioParaEliminar = null
                        }
                    ) {
                        Text("Eliminar", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { servicioParaEliminar = null }) {
                        Text("Cancelar", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            )
        }
    }
}

@Composable
fun ServicioCardItem(
    servicio: Servicio,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Fila 1: Nombre a la izquierda, Botones de Acción a la derecha
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically, // Centra los botones con el texto
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = servicio.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                // Agrupamos los botones para que estén juntos pero no ocupen demasiado espacio
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Fila 2: Precio y Duración reubicada debajo del nombre
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "$ ${servicio.precio}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(text = "•", color = MaterialTheme.colorScheme.onSurfaceVariant)

                // Muestra la duración con un pequeño icono de reloj
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Duración",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = servicio.duracion,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Fila 3: Descripción
            Text(
                text = servicio.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}