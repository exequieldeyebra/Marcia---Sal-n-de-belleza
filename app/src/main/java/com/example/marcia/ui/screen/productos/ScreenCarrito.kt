package com.example.marcia.ui.screen.productos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marcia.ui.viewModel.ItemCarrito
import com.example.marcia.ui.viewModel.ProductosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    viewModel: ProductosViewModel,
    onVolverClick: () -> Unit = {},
    onVentaExitosa: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Venta y Cobro 💳") }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            // Si el carrito está vacío, mostramos un mensaje descriptivo
            if (viewModel.carrito.isEmpty()) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "El carrito está vacío 🛒",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                } // Fin de Box vacío

            } else {

                // Título del listado
                Text(
                    text = "Productos a cobrar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Lista de productos agregados con sus controles de cantidad
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.carrito) { item ->
                        ItemCarritoCard(
                            item = item,
                            onSumar = { viewModel.incrementarCantidad(item) },
                            onRestar = { viewModel.decrementarCantidad(item) },
                            onEliminar = { viewModel.eliminarDelCarrito(item) }
                        )
                    } // Fin de items
                } // Fin de LazyColumn

                Spacer(modifier = Modifier.height(12.dp))

                // Selección de Medio de Pago
                Text(
                    text = "Medio de Pago",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Opciones de medio de pago
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    viewModel.mediosPagoDisponibles.forEach { medio ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.seleccionarMedioPago(medio) }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = (medio == viewModel.medioPagoSeleccionado),
                                onClick = { viewModel.seleccionarMedioPago(medio) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = medio)
                        } // Fin de Row medio de pago
                    } // Fin de forEach
                } // Fin de Column de medios de pago

                Spacer(modifier = Modifier.height(12.dp))

                // Resumen final de cobro
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Total Final:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = "$ ${viewModel.totalVenta}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        } // Fin de Row de Total

                        Spacer(modifier = Modifier.height(12.dp))

                        // Botón para Confirmar Venta
                        Button(
                            onClick = {
                                if (viewModel.finalizarVenta()) {
                                    onVentaExitosa()
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Confirmar y Cobrar")
                        } // Fin de Button Confirmar

                    } // Fin de Column interna del Card
                } // Fin de Card Resumen

            } // Fin del else (carrito con items)

        } // Fin de Column principal
    } // Fin de Scaffold
} // Fin de CarritoScreen

/**
 * Tarjeta individual para ajustar cantidades de un producto en el carrito.
 */
@Composable
fun ItemCarritoCard(
    item: ItemCarrito,
    onSumar: () -> Unit,
    onRestar: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Información del producto y subtotal
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.producto.nombre,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Unitario: $ ${item.producto.precio}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Subtotal: $ ${item.producto.precio * item.cantidad}",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            } // Fin de Column info

            // Controles de incremento / decremento
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                OutlinedButton(
                    onClick = onRestar,
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text("-")
                }

                Text(
                    text = "${item.cantidad}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )

                OutlinedButton(
                    onClick = onSumar,
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text("+")
                }
            } // Fin de Row controles

        } // Fin de Row principal
    } // Fin de Card
} // Fin de ItemCarritoCard

/**
 * Vista previa para verificar el diseño en la solapa Split/Design sin compilar en emulador.
 */
@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun CarritoScreenPreview() {
    val viewModelPrueba = ProductosViewModel()
    // Agregamos un producto de prueba para ver el renderizado
    viewModelPrueba.agregarAlCarrito(
        com.example.marcia.data.Producto("1", "Shampoo Especial", 12500.0, 10, 0)
    )

    CarritoScreen(viewModel = viewModelPrueba)
} // Fin de CarritoScreenPreview