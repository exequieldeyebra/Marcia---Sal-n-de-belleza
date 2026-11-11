package com.example.marcia.ui.screen.productos

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompraConfirmadaScreen(
    totalCobrado: Double,
    medioPago: String,
    onVolverAlCatalogo: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Scaffold provee la estructura visual de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Venta Exitosa ✅") }
            )
        }
    ) { innerPadding ->

        // Contenedor principal centrado vertical y horizontalmente
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Ícono o emoji representativo de éxito grande
            Text(
                text = "🎉",
                fontSize = 64.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título principal de confirmación
            Text(
                text = "¡Cobro realizado con éxito!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtítulo aclaratorio
            Text(
                text = "La transacción se registró correctamente en el sistema del salón.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tarjeta con el resumen de la operación
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Medio de pago:")
                        Text(text = medioPago, fontWeight = FontWeight.Bold)
                    } // Fin de Row medio de pago

                    Divider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Total cobrado:", fontWeight = FontWeight.Bold)
                        Text(
                            text = "$ $totalCobrado",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp
                        )
                    } // Fin de Row total
                } // Fin de Column interna de la tarjeta
            } // Fin de Card de resumen

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para volver al catálogo y arrancar de nuevo
            Button(
                onClick = onVolverAlCatalogo,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Nueva Venta / Volver al Catálogo")
            } // Fin de Button volver

        } // Fin de Column principal
    } // Fin de Scaffold
} // Fin de CompraConfirmadaScreen

/**
 * Vista previa para verificar el diseño de éxito en el IDE sin compilar en el emulador.
 */
@Preview(showBackground = true)
@Composable
fun CompraConfirmadaScreenPreview() {
    CompraConfirmadaScreen(
        totalCobrado = 23500.0,
        medioPago = "Efectivo",
        onVolverAlCatalogo = {}
    )
} // Fin de CompraConfirmadaScreenPreview