package com.example.marcia.ui.screen.Empleados

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marcia.data.Empleado
import com.example.marcia.ui.viewmodel.EmpleadosViewModel


@Composable
fun EmpleadosScreen(
    empleadosViewModel: EmpleadosViewModel = viewModel()
) {
    val empleadosLista = empleadosViewModel.listadoEmpleados

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
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
            EmpleadoCardItem(empleado)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
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
fun EmpleadoCardItem(empleado: Empleado) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = {}
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                empleado.name,
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
