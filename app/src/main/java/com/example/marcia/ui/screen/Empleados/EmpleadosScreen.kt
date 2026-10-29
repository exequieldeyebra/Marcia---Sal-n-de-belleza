package com.example.marcia.ui.screen.Empleados

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marcia.data.Empleado
import com.example.marcia.ui.viewmodel.EmpleadosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun EmpleadosScreen(
    empleadosViewModel: EmpleadosViewModel = viewModel(),
) {
    val empleadosLista = empleadosViewModel.listadoEmpleados
    var mostrarModalRegistro by remember { mutableStateOf(false) }
    var empleadoParaEditar by remember { mutableStateOf<Empleado?>(null) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MetricCard("TOTAL", empleadosLista.size.toString(), "equipo", Modifier.weight(1f))
                    MetricCard("EN TURNO", "6", "activos", Modifier.weight(1f))
                    MetricCard("LICENCIA", "2", "ausentes", Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(empleadosLista) { empleado ->
                EmpleadoCardItem(
                    empleado = empleado,
                    onClick = {
                        empleadosViewModel.seleccionarEmpleadoParaEdicion(empleado)
                        empleadoParaEditar = empleado
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        BotonFlotanteAgregarEmpleado(
            onClick = {
                empleadosViewModel.prepararNuevoRegistro()
                mostrarModalRegistro = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )

        // Modal de Registro
        if (mostrarModalRegistro) {
            ModalBottomSheet(
                onDismissRequest = { mostrarModalRegistro = false },
                containerColor = MaterialTheme.colorScheme.surface,
                scrimColor = Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                val state by empleadosViewModel.state.collectAsStateWithLifecycle()
                val stateUsuario by empleadosViewModel.stateUsuario.collectAsStateWithLifecycle()

                RegistrarEmpleadoContent(
                    usuarioState = stateUsuario,
                    empleadoState = state,
                    onNombreChange = empleadosViewModel::onNombreChange,
                    onApellidoChange = empleadosViewModel::onApellidoChange,
                    onDniChange = empleadosViewModel::onDniChange,
                    onRolChange = empleadosViewModel::onRolChange,
                    onConfirmar = {
                        empleadosViewModel.agregarEmpleado()
                        mostrarModalRegistro = false
                    },
                    onDismiss = { mostrarModalRegistro = false }
                )
            }
        }

        // Modal de Detalle / Edición
        if (empleadoParaEditar != null) {
            ModalBottomSheet(
                onDismissRequest = { empleadoParaEditar = null },
                containerColor = MaterialTheme.colorScheme.surface,
                scrimColor = Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                val state by empleadosViewModel.state.collectAsStateWithLifecycle()
                val stateUsuario by empleadosViewModel.stateUsuario.collectAsStateWithLifecycle()

                DetalleEmpleadoContent(
                    usuarioState = stateUsuario,
                    empleadoState = state,
                    onNombreChange = empleadosViewModel::onNombreChange,
                    onApellidoChange = empleadosViewModel::onApellidoChange,
                    onDniChange = empleadosViewModel::onDniChange,
                    onRolChange = empleadosViewModel::onRolChange,
                    onConfirmar = {
                        empleadosViewModel.guardarEdicionEmpleado()
                        empleadoParaEditar = null
                    },
                    onDismiss = { empleadoParaEditar = null }
                )
            }
        }
    }
}

@Composable
fun BotonFlotanteAgregarEmpleado(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        text = { Text("Agregar empleado") },
        icon = { Icon(Icons.Filled.Add, contentDescription = "Agregar empleado") },
        shape = RoundedCornerShape(16.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = modifier.testTag("btn_agregar_empleado")
    )
}

@Composable
fun MetricCard(title: String, value: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
            Text(subtitle, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun EmpleadoCardItem(
    empleado: Empleado,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "${empleado.UsuarioId.nome} ${empleado.UsuarioId.apellido}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                empleado.role,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(empleado.shifts, style = MaterialTheme.typography.bodyMedium)
                Text("⭐ ${empleado.rating}", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
