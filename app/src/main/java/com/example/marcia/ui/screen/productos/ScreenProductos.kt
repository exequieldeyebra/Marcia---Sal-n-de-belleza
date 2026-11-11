package com.example.marcia.ui.screen.productos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marcia.data.Producto
import com.example.marcia.ui.viewModel.ProductosViewModel



/**
 * @Preview: Permite renderizar la pantalla directamente en la pestaña "Design" o "Split"
 * de tu entorno de desarrollo, sin necesidad de compilar la app en un celular ni en un emulador.
 */
@Preview(showBackground = true)
@Composable
fun ProductosScreenPreview() {
    // Instanciamos el ViewModel para alimentar la vista previa con datos de prueba
    @Suppress("ViewModelConstructorInComposable")
    val viewModelPrueba = ProductosViewModel()

    // Llamamos a nuestra pantalla principal pasándole el ViewModel
    ProductosScreen(viewModel = viewModelPrueba)
} // Fin de ProductosScreenPreview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(
    viewModel: ProductosViewModel,
    modifier: Modifier = Modifier
) {
    // Scaffold provee la estructura visual básica de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Venta de Productos 🛍️") }
            )
        }
    )
    { innerPadding ->

        // Contenedor principal en columna
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            // Título de la sección
            Text(
                text = "Catálogo disponible",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // LazyColumn para renderizar la lista de productos de forma eficiente
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.listaProductos) { producto ->
                    ProductoItemCard(
                        producto = producto,
                        onAgregarClick = {
                            viewModel.agregarAlCarrito(producto)
                        }
                    )
                } // Fin de items
            } // Fin de LazyColumn

            Spacer(modifier = Modifier.height(16.dp))

            // Tarjeta inferior que funciona como resumen del carrito y cobro
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Productos en carrito: ${viewModel.carrito.size}")

                    Text(
                        text = "Total a Pagar: $ ${viewModel.totalVenta}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Botón para concretar la venta
                    Button(
                        onClick = {
                            viewModel.finalizarVenta()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = viewModel.carrito.isNotEmpty()
                    ) {
                        Text(text = "Finalizar Venta")
                    } // Fin de Button

                } // Fin de Column interna del Card
            } // Fin de Card de resumen

        } // Fin de Column principal
    } // Fin de Scaffold
} // Fin de ProductosScreen

/**
 * Componente visual individual (Card) para cada producto del catálogo.
 */
@Composable
fun ProductoItemCard(
    producto: Producto,
    onAgregarClick: () -> Unit
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

            // Columna con detalles del producto (Nombre, precio, stock)
            Column {
                Text(
                    text = producto.nombre,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Precio: $ ${producto.precio}",
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Stock: ${producto.stock}",
                    fontSize = 12.sp
                )
            } // Fin de Column de detalles

            // Botón para agregar al carrito
            Button(
                onClick = onAgregarClick
            ) {
                Text("Agregar")
            } // Fin de Button agregar

        } // Fin de Row
    } // Fin de Card de producto
} // Fin de ProductoItemCard