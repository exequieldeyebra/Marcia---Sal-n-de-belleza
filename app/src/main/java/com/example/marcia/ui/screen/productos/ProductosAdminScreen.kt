package com.example.marcia.ui.screen.productos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marcia.data.Producto
import com.example.marcia.ui.viewmodel.ProductosViewModel

/**
 * Pantalla principal de administración (ABML) de productos del salón.
 * Lee el inventario desde Room mediante StateFlow y gestiona Alta, Baja y Modificación
 * mediante modales flotantes (ModalBottomSheet) y diálogo de confirmación.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosAdminScreen(
    viewModel: ProductosViewModel = viewModel()
) {
    // Observamos la lista reactiva proveniente de Room
    val productos by viewModel.listaProductos.collectAsStateWithLifecycle()

    // Estados locales para controlar la apertura de los modales y el diálogo de baja
    var mostrarModalRegistro by remember { mutableStateOf(false) }
    var productoParaEditar by remember { mutableStateOf<Producto?>(null) }
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Encabezado con contador total de productos cargados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "INVENTARIO ACTIVO", style = MaterialTheme.typography.labelMedium)
                    Text(
                        text = "${productos.size} productos",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Evaluamos si el inventario está vacío
            if (productos.isEmpty()) {
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
                            text = "Tocá el botón inferior para agregar el primero",
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
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(
                        items = productos,
                        key = { it.id }
                    ) { producto ->
                        AdminProductoItemCard(
                            producto = producto,
                            onEditar = {
                                viewModel.seleccionarProductoParaEdicion(producto)
                                productoParaEditar = producto
                            },
                            onEliminar = { productoAEliminar = producto }
                        )
                    }
                }
            }
        }

        // Botón flotante extendido para dar de Alta un nuevo producto
        ExtendedFloatingActionButton(
            onClick = {
                viewModel.prepararNuevoProducto()
                mostrarModalRegistro = true
            },
            text = { Text("Nuevo Producto") },
            icon = { Icon(Icons.Filled.Add, contentDescription = "Agregar producto") },
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )

        // Modal BottomSheet para CREAR (Alta)
        if (mostrarModalRegistro) {
            ModalBottomSheet(
                onDismissRequest = { mostrarModalRegistro = false },
                containerColor = MaterialTheme.colorScheme.surface,
                scrimColor = Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                val productoState by viewModel.productoEnEdicion.collectAsStateWithLifecycle()
                FormularioProductoContent(
                    productoState = productoState,
                    precioTexto = viewModel.precioTexto,
                    stockTexto = viewModel.stockTexto,
                    tituloPanel = "Añadir Nuevo Producto",
                    onNombreChange = viewModel::onNombreChange,
                    onDescripcionChange = viewModel::onDescripcionChange,
                    onPrecioChange = viewModel::onPrecioChange,
                    onStockChange = viewModel::onStockChange,
                    onConfirmar = {
                        viewModel.guardarNuevoProducto()
                        mostrarModalRegistro = false
                    },
                    onDismiss = { mostrarModalRegistro = false }
                )
            }
        }

        // Modal BottomSheet para EDITAR (Modificación)
        if (productoParaEditar != null) {
            ModalBottomSheet(
                onDismissRequest = { productoParaEditar = null },
                containerColor = MaterialTheme.colorScheme.surface,
                scrimColor = Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                val productoState by viewModel.productoEnEdicion.collectAsStateWithLifecycle()
                FormularioProductoContent(
                    productoState = productoState,
                    precioTexto = viewModel.precioTexto,
                    stockTexto = viewModel.stockTexto,
                    tituloPanel = "Modificar Producto",
                    iconoPanel = Icons.Default.Inventory2,
                    onNombreChange = viewModel::onNombreChange,
                    onDescripcionChange = viewModel::onDescripcionChange,
                    onPrecioChange = viewModel::onPrecioChange,
                    onStockChange = viewModel::onStockChange,
                    onConfirmar = {
                        viewModel.guardarEdicionProducto()
                        productoParaEditar = null
                    },
                    onDismiss = { productoParaEditar = null }
                )
            }
        }

        // Diálogo de confirmación para evitar eliminaciones accidentales (Baja)
        if (productoAEliminar != null) {
            AlertDialog(
                onDismissRequest = { productoAEliminar = null },
                containerColor = MaterialTheme.colorScheme.surface,
                title = {
                    Text(
                        "Eliminar Producto",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                text = {
                    Text(
                        text = "¿Estás seguro de que deseas eliminar \"${productoAEliminar?.nombre}\"? Esta acción no se puede deshacer.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            productoAEliminar?.let { viewModel.eliminarProducto(it) }
                            productoAEliminar = null
                        }
                    ) {
                        Text("Eliminar", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { productoAEliminar = null }) {
                        Text("Cancelar", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            )
        }
    }
}

/**
 * Tarjeta individual moderna para cada producto en el panel de administración.
 * Muestra Nombre, Descripción, Precio, Stock y botones rápidos de Edición y Baja.
 */
@Composable
fun AdminProductoItemCard(
    producto: Producto,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = producto.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = producto.descripcion.ifBlank { "Sin descripción detallada" },
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$ ${producto.precio}",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (producto.stock > 3) {
                            MaterialTheme.colorScheme.surfaceVariant
                        } else {
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

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End
            ) {
                IconButton(onClick = onEditar, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(onClick = onEliminar, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}