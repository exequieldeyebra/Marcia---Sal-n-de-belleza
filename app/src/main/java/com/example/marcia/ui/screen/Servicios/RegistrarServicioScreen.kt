package com.example.marcia.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RegistrarServicioScreen(onDismiss: () -> Unit = {}) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var beneficios by remember { mutableStateOf("") }

    // Mock states for dropdowns and checkboxes
    var expandedDuracion by remember { mutableStateOf(false) }
    var duracionSeleccionada by remember { mutableStateOf("60 minutos") }

    var expandedCategoria by remember { mutableStateOf(false) }
    var categoriaSeleccionada by remember { mutableStateOf("Faciales") }

    var checkElena by remember { mutableStateOf(true) }
    var checkMariana by remember { mutableStateOf(false) }
    var checkCamila by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Encabezado del Modal[cite: 23]
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Icono circular[cite: 23]
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🌸", fontSize = 18.sp) // Puedes reemplazarlo por el icono SVG correspondiente
                }
                Text(
                    text = "Añadir Nuevo Servicio",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 1.dp)

        // Nombre del tratamiento[cite: 23]
        CustomFormField(
            label = "NOMBRE DEL TRATAMIENTO",
            placeholder = "Ej: Ritual Corporal Relajante Botánico",
            value = nombre,
            onValueChange = { nombre = it },
            modifier = Modifier.fillMaxWidth()
        )

        // Fila: Precio y Duración[cite: 23]
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomFormField(
                label = "PRECIO (ARS)",
                placeholder = "25000",
                value = precio,
                onValueChange = { precio = it },
                leadingIcon = { Text("$", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                modifier = Modifier.weight(1f)
            )

            // Dropdown Duración (Simplificado para vista visual)[cite: 23]
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "DURACIÓN (MIN)",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
                ExposedDropdownMenuBox(expanded = expandedDuracion, onExpandedChange = { expandedDuracion = !expandedDuracion }) {
                    TextField(
                        value = duracionSeleccionada,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDuracion) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.menuAnchor()
                    )
                }
            }
        }

        // Categoría[cite: 23]
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "CATEGORÍA",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
            ExposedDropdownMenuBox(expanded = expandedCategoria, onExpandedChange = { expandedCategoria = !expandedCategoria }) {
                TextField(
                    value = categoriaSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategoria) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
            }
        }

        // Beneficios / Descripción[cite: 23]
        CustomFormField(
            label = "BENEFICIOS / DESCRIPCIÓN",
            placeholder = "Resumen de técnicas, activos e impacto del tratamiento...",
            value = beneficios,
            onValueChange = { beneficios = it },
            singleLine = false,
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        // Especialistas Certificados[cite: 23]
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "ESPECIALISTAS CERTIFICADOS",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CustomCheckbox(checked = checkElena, onCheckedChange = { checkElena = it }, label = "Dra. Elena Silva")
                    CustomCheckbox(checked = checkMariana, onCheckedChange = { checkMariana = it }, label = "Mariana Ruiz")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón Guardar
        Button(
            onClick = { onDismiss() },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Confirmar y Guardar", style = MaterialTheme.typography.titleMedium)
        }
    }
}

// Componente reutilizable ajustado para el campo de texto[cite: 23]
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomFormField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray) },
            singleLine = singleLine,
            minLines = minLines,
            leadingIcon = leadingIcon,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

// Componente para Checkbox con texto al lado[cite: 23]
@Composable
private fun CustomCheckbox(checked: Boolean, onCheckedChange: (Boolean) -> Unit, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
    }
}