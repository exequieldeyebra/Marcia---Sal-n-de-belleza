package com.example.marcia.ui.screen.Empleados

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marcia.data.Empleado
import com.example.marcia.data.Usuario

@Composable
fun DetalleEmpleadoContent(
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
            icono = Icons.Default.Person,
            titulo = "Detalle / Modificación",
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
                placeholder = "Ej: Ponce",
                value = usuarioState.apellido,
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
                placeholder = "Sin puntos",
                value = usuarioState.dni,
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
            Text("Guardar Cambios", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleEmpleadoContentPreview() {
    DetalleEmpleadoContent(
        usuarioState = Usuario(nome = "Baltasar", apellido = "Elli", dni = "12345678"),
        empleadoState = Empleado(role = "Peluquero"),
        onNombreChange = {},
        onApellidoChange = {},
        onDniChange = {},
        onRolChange = {},
        onConfirmar = {},
        onDismiss = {}
    )
}
