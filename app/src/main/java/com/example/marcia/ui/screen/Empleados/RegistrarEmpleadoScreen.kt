package com.example.marcia.ui.screen.Empleados

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marcia.data.Empleado
import com.example.marcia.data.Usuario

@Composable
fun EncabezadoModal(icono: ImageVector, titulo: String, onDismiss: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = titulo,
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
}

/**
 * Componente Stateless siguiendo las buenas prácticas y estándares de Android.
 * No depende de ningún ViewModel específico, facilitando pruebas unitarias y Previews.
 */
@Composable
fun RegistrarEmpleadoContent(
    usuarioState: Usuario,
    empleadoState: Empleado,
    onNombreChange: (String) -> Unit,
    onApellidoChange: (String) -> Unit,
    onDniChange: (String) -> Unit,
    onRolChange: (String) -> Unit,
    onConfirmar: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        EncabezadoModal(
            icono = Icons.Default.PersonAdd,
            titulo = "Registrar empleado",
            onDismiss = onDismiss
        )

        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 1.dp)

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomFormField(
                label = "NOMBRE",
                placeholder = "Ej: Camila",
                value = usuarioState.nome,
                onInputChange = onNombreChange,
                modifier = Modifier.weight(1f)
            )
            CustomFormField(
                label = "APELLIDO",
                value = usuarioState.apellido,
                placeholder = "Ej: Ponce",
                onInputChange = onApellidoChange,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomFormField(
                label = "DNI",
                value = usuarioState.dni,
                placeholder = "Sin puntos",
                onInputChange = onDniChange,
                modifier = Modifier.weight(1f)
            )
            CustomFormField(
                label = "SERVICIO / ROL",
                value = empleadoState.role,
                onInputChange = onRolChange,
                placeholder = "Ej: Lashista",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onConfirmar,
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

@Composable
fun CustomFormField(
    label: String,
    placeholder: String,
    value: String = "",
    onInputChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
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
            onValueChange = onInputChange,
            placeholder = { Text(placeholder, color = Color.Gray) },
            singleLine = true,
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

@Preview(showBackground = true)
@Composable
fun RegistrarEmpleadoContentPreview() {
    RegistrarEmpleadoContent(
        usuarioState = Usuario(nome = "Camila", apellido = "Ponce", dni = "12345678"),
        empleadoState = Empleado(role = "Lashista"),
        onNombreChange = {},
        onApellidoChange = {},
        onDniChange = {},
        onRolChange = {},
        onConfirmar = {},
        onDismiss = {}
    )
}
