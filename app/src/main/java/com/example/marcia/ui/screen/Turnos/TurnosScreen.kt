package com.example.marcia.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TurnosScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(16.dp)
    ) {
        item {
            TurnosHeroCard(date = "Jueves, 24 Octubre", occupancy = "85% ocupación en cabinas", totalTurnos = 18, enCurso = 3)
            Spacer(modifier = Modifier.height(16.dp))
            TurnosSearchBarAndFilters()
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            TurnoCardItem(
                time = "09:30 - 10:45 AM",
                duration = "75 min",
                clientName = "Valentina Rossi",
                serviceName = "Limpieza Facial Profunda + Hidratación",
                therapist = "Dra. Elena Silva",
                status = "En curso",
                room = "Cabina 02"
            )
            Spacer(modifier = Modifier.height(12.dp))
            TurnoCardItem(
                time = "12:15 - 01:30 PM",
                duration = "75 min",
                clientName = "Sofía Méndez",
                serviceName = "Masaje Descontracturante & Piedras...",
                therapist = "Lic. Mariana Ruiz",
                status = "Confirmado",
                room = "VIP Oro"
            )
        }
    }
}

@Composable
fun TurnosHeroCard(date: String, occupancy: String, totalTurnos: Int, enCurso: Int) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("JORNADA ACTIVA", color = MaterialTheme.colorScheme.onPrimaryContainer, style = MaterialTheme.typography.labelSmall)
                Text("$totalTurnos Turnos Hoy", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(date, color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.headlineMedium)
            Text(occupancy, color = MaterialTheme.colorScheme.onPrimaryContainer, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun TurnosSearchBarAndFilters() {
    Column {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Buscar cliente, servicio o profesional...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        // Aquí iría un LazyRow con los chips de filtrado (Todos, En curso, Confirmados)[cite: 7]
    }
}

@Composable
fun TurnoCardItem(time: String, duration: String, clientName: String, serviceName: String, therapist: String, status: String, room: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("$time • $duration", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                Text(status, style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(clientName, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
            Text(serviceName, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(therapist, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}