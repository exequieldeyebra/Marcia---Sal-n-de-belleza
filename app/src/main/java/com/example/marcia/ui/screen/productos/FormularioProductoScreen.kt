package com.example.marcia.ui.screen.productos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marcia.data.Producto

/**
 * Pantalla de formulario para el ABML de productos.
 * Permite dar de alta un nuevo artículo o modificar uno existente gestionando
 * cuatro campos obligatorios: nombre, descripción, precio y stock.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioProductoScreen(
    productoExistente: Producto? = null, // Si es null funciona como Alta; si trae datos, funciona como Edición
    onGuardarClick: (nombre: String, descripcion: String, precio: Double, stock: Int) -> Unit,
    onVolverClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Estados locales para capturar el valor de los campos de texto
    var nombre by remember { mutableStateOf(productoExistente?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(productoExistente?.descripcion ?: "") }
    var precioStr by remember { mutableStateOf(productoExistente?.precio?.toString() ?: "") }
    var stockStr by remember { mutableStateOf(productoExistente?.stock?.toString() ?: "") }

    // Estado para controlar y mostrar mensajes de error ante datos inválidos
    var errorMensaje by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (productoExistente == null) "Nuevo Producto 📝" else "Editar Producto ✏️",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    TextButton(onClick = onVolverClick) {
                        Text(text = "← Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()) // Permite scrollear si la pantalla es chica
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Complete los datos del inventario",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Campo de texto para el Nombre del producto
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del producto") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Campo de texto para la Descripción detallada
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 3,
                maxLines = 5
            )

            // Campo de texto para el Precio (teclado numérico)
            OutlinedTextField(
                value = precioStr,
                onValueChange = { precioStr = it },
                label = { Text("Precio ($)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            // Campo de texto para el Stock disponible (teclado numérico)
            OutlinedTextField(
                value = stockStr,
                onValueChange = { stockStr = it },
                label = { Text("Stock disponible (unidades)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            // Muestra una alerta visual si algún campo obligatorio no está bien completado
            if (errorMensaje.isNotEmpty()) {
                Text(
                    text = errorMensaje,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón principal de acción para validar y guardar los datos
            Button(
                onClick = {
                    val precio = precioStr.toDoubleOrNull()
                    val stock = stockStr.toIntOrNull()

                    // Validamos que los campos no estén vacíos y que los números sean correctos
                    if (nombre.isBlank() || precio == null || stock == null) {
                        errorMensaje = "Por favor, complete correctamente todos los campos obligatorios."
                    } else {
                        errorMensaje = ""
                        onGuardarClick(nombre.trim(), descripcion.trim(), precio, stock)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Guardar Producto",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}