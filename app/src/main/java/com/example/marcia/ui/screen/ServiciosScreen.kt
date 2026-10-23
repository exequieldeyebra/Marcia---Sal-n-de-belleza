package com.example.marcia.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ServiciosScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("CATÁLOGO ACTIVO", style = MaterialTheme.typography.labelMedium)
            Text("24 tratamientos activos", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))

            // Fila de categorías[cite: 5]
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = true, onClick = { }, label = { Text("Todos") })
                FilterChip(selected = false, onClick = { }, label = { Text("Faciales") })
                FilterChip(selected = false, onClick = { }, label = { Text("Corporales") })
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ServicioCardItem("Tratamiento Facial Glow", "$28.500", "60m", "Protocolo biorevitalizante con ácido hialurónico...", "Dra. Elena S., Mariana R.")
            Spacer(modifier = Modifier.height(12.dp))
            ServicioCardItem("Drenaje Linfático Manual", "$22.000", "90m", "Técnica especializada Método Vodder...", "Lic. Sofía V., Lucas G.")
        }
    }
}

@Composable
fun ServicioCardItem(name: String, price: String, duration: String, desc: String, staff: String) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(name, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Text(duration, style = MaterialTheme.typography.labelSmall)
            }
            Text(price, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(4.dp))
            Text(desc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Personal: $staff", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
        }
    }
}