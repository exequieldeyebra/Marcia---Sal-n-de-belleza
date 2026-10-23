package com.example.marcia.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.marcia.ui.theme.Success

@Composable
fun FinanzasScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            BalanceHeroCard("$184.500", "+$242.000", "-$57.500")
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Abrir modal de gasto */ },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text("Registrar Comprobante de Gasto")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("Movimientos de Hoy", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            TransaccionItem("Cobro", "Limpieza Facial Profunda", "+$28.500", "17:15 hs")
            Spacer(modifier = Modifier.height(8.dp))
            TransaccionItem("Gasto", "Reposición Ampollas Hial...", "-$32.000", "15:40 hs")
        }
    }
}

@Composable
fun BalanceHeroCard(neto: String, ingresos: String, gastos: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("GANANCIA NETA DE HOY", style = MaterialTheme.typography.labelSmall)
            Text(neto, style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column {
                    Text("Ingresos", style = MaterialTheme.typography.labelSmall)
                    Text(ingresos, style = MaterialTheme.typography.titleMedium, color = Success) //[cite: 10]
                }
                Column {
                    Text("Gastos", style = MaterialTheme.typography.labelSmall)
                    Text(gastos, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}

@Composable
fun TransaccionItem(type: String, desc: String, amount: String, time: String) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(type, style = MaterialTheme.typography.labelSmall)
                Text(desc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
            }
            Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                Text(amount, style = MaterialTheme.typography.titleMedium)
                Text(time, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}