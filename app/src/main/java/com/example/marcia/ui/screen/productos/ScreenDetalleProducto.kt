package com.example.marcia.ui.screen.productos

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marcia.data.Producto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    producto: Producto,
    onVolverClick: () -> Unit,
    onAgregarAlCarrito: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Scaffold provee la estructura visual estándar de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto 📦") },
                navigationIcon = {
                    // Botón para volver a la pantalla anterior
                    TextButton(onClick = onVolverClick) {
                        Text("← Volver")
                    } // Fin de TextButton volver
                }
            )
        }
    ) { innerPadding ->

        // Contenedor principal en columna con separación entre elementos
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // Bloque superior con toda la información del producto
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Nombre principal del producto
                Text(
                    text = producto.nombre,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                // Precio destacado
                Text(
                    text = "Precio: $ ${producto.precio}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                // Tarjeta informativa con el stock disponible
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Stock disponible:",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${producto.stock} unidades en el salón",
                            fontSize = 16.sp
                        )
                    } // Fin de Column interna
                } // Fin de Card de stock

                Spacer(modifier = Modifier.height(8.dp))

                // Descripción conceptual del producto
                Text(
                    text = "Descripción:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Text(
                    text = "Producto profesional seleccionado para el cuidado y tratamiento capilar en el salón de belleza Marcia. Ideal para aplicación en servicios o venta directa.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            } // Fin de Column de información

            // Botón inferior para sumar el producto al carrito desde el detalle
            Button(
                onClick = onAgregarAlCarrito,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Agregar al Carrito 🛒")
            } // Fin de Button agregar

        } // Fin de Column principal
    } // Fin de Scaffold
} // Fin de DetalleProductoScreen

/**
 * Vista previa para testear el diseño del detalle en el IDE sin compilar en el emulador.
 */
@Preview(showBackground = true)
@Composable
fun DetalleProductoScreenPreview() {
    // Objeto de prueba para visualizar la interfaz
    val productoPrueba = Producto(
        id = "1",
        nombre = "Shampoo Especial Profesional",
        precio = 12500.0,
        stock = 10,
        imagenResId = 0
    )

    DetalleProductoScreen(
        producto = productoPrueba,
        onVolverClick = {},
        onAgregarAlCarrito = {}
    )
} // Fin de DetalleProductoScreenPreview

