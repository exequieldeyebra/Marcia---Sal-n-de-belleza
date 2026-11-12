package com.example.marcia.ui.screen.productos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marcia.data.Producto
import com.example.marcia.ui.viewmodel.ProductosViewModel

/**
 * Pantalla principal de administración (ABML) de productos del salón.
 * Muestra el inventario con nombre, descripción, precio y stock, permitiendo
 * dar de alta nuevos artículos, editarlos o eliminarlos.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosAdminScreen(
    viewModel: ProductosViewModel,
    onNuevoProductoClick: () -> Unit,
    onEditarProductoClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Estado para controlar el diálogo de confirmación antes de eliminar un producto
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Gestión de Productos 📦",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                )
            )
        },
        // Botón flotante para dar de Alta un nuevo producto
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNuevoProductoClick,
                containerColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar nuevo producto"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Encabezado con contador total de productos cargados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Inventario del Salón",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Badge(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Text(
                        text = "${viewModel.listaProductos.size} productos",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Evaluamos si el inventario está vacío
            if (viewModel.listaProductos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "📦", fontSize = 48.sp)
                        Text(
                            text = "No hay productos cargados",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Tocá el botón + para agregar el primero",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                // Listado de productos con espaciado prolijo
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    // Dejamos espacio abajo para que el FAB no tape las acciones del último ítem
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(
                        items = viewModel.listaProductos,
                        key = { it.id }
                    ) { producto ->
                        AdminProductoItemCard(
                            producto = producto,
                            onEditar = { onEditarProductoClick(producto.id) },
                            onEliminar = { productoAEliminar = producto }
                        )
                    }
                }
            }
        }

        // Diálogo de confirmación para evitar eliminaciones accidentales
        productoAEliminar?.let { producto ->
            AlertDialog(
                onDismissRequest = { productoAEliminar = null },
                title = { Text(text = "Eliminar producto") },
                text = {
                    Text(text = "¿Estás seguro de que querés eliminar \"${producto.nombre}\" del inventario?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.eliminarProducto(producto.id)
                            productoAEliminar = null
                        }
                    ) {
                        Text(
                            text = "Eliminar",
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { productoAEliminar = null }) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }
    }
}

/**
 * Tarjeta individual moderna para cada producto en el panel de administración.
 * Muestra Nombre, Descripción, Precio, Stock y botones de Edición y Baja.
 */
@Composable
fun AdminProductoItemCard(
    producto: Producto,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Columna izquierda con la información completa del producto
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = producto.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = producto.descripcion.ifBlank { "Sin descripción detallada" },
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Fila con Precio y etiqueta de Stock
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$ ${producto.precio}",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (producto.stock > 3) {
                            MaterialTheme.colorScheme.surfaceVariant
                        } else {
                            // Destacamos en rojo suave si queda poco stock
                            MaterialTheme.colorScheme.errorContainer
                        }
                    ) {
                        Text(
                            text = "Stock: ${producto.stock} un.",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            color = if (producto.stock > 3) {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            } else {
                                MaterialTheme.colorScheme.onErrorContainer
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Columna derecha con los botones de acción (Editar y Eliminar)
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End
            ) {
                IconButton(
                    onClick = onEditar,
                    modifier = Modifier.size(38.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar producto",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

                IconButton(
                    onClick = onEliminar,
                    modifier = Modifier.size(38.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar producto",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun ProductosAdminScreenPreview() {
    val viewModelPrueba = ProductosViewModel()
    ProductosAdminScreen(
        viewModel = viewModelPrueba,
        onNuevoProductoClick = {},
        onEditarProductoClick = {}
    )
}