package com.example.marcia.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Rutas tipadas y serializables para Jetpack Compose Navigation 3.
 * Centraliza todos los destinos de la aplicación del salón de belleza.
 */
sealed interface ScreenRoute : NavKey

@Serializable
data object LoginRoute : ScreenRoute

// ========================================================
// DESTINOS PRINCIPALES (BARRA DE NAVEGACIÓN INFERIOR)
// ========================================================

@Serializable
data object TurnosRoute : ScreenRoute

@Serializable
data object ServiciosRoute : ScreenRoute

@Serializable
data object EmpleadosRoute : ScreenRoute

@Serializable
data object RegistroEmpleadoRoute : ScreenRoute

@Serializable
data object FinanzasRoute : ScreenRoute

/**
 * Ruta principal para el panel de administración (ABML) de productos e inventario.
 */
@Serializable
data object ProductosRoute : ScreenRoute

// ========================================================
// DESTINOS SECUNDARIOS / FLUJOS INTERNOS CON ARGUMENTOS
// ========================================================

@Serializable
data class DetalleTurnoRoute(val appointmentId: String) : ScreenRoute

@Serializable
data object NuevoTurnoRoute : ScreenRoute

@Serializable
data class DetalleServicioRoute(val serviceId: String) : ScreenRoute

@Serializable
data class DetalleEmpleadoRoute(val employeeId: String) : ScreenRoute

// --- Rutas del módulo de Productos (ABML) ---

/**
 * Ruta para visualizar la ficha técnica completa de un producto.
 */
@Serializable
data class DetalleProductoRoute(val productoId: String) : ScreenRoute

/**
 * Ruta para el formulario de Alta y Modificación de productos.
 * - Si [productoId] es null: Abre el formulario vacío para crear un producto nuevo (Alta).
 * - Si [productoId] contiene un ID: Carga los datos existentes para editarlo (Modificación).
 */
@Serializable
data class FormularioProductoRoute(val productoId: String? = null) : ScreenRoute

@Serializable
data object EstadisticasFinanzasRoute : ScreenRoute

// ========================================================
// CONFIGURACIÓN DE LA BARRA DE NAVEGACIÓN INFERIOR
// ========================================================

/**
 * Representación de cada pestaña principal mostrada en la barra de navegación inferior.
 */
data class TopLevelDestination(
    val route: NavKey,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String,
    val testTag: String
)

val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = TurnosRoute,
        selectedIcon = Icons.Filled.CalendarMonth,
        unselectedIcon = Icons.Outlined.CalendarMonth,
        label = "Turnos",
        testTag = "nav_tab_turnos"
    ),
    TopLevelDestination(
        route = ServiciosRoute,
        selectedIcon = Icons.Filled.Spa,
        unselectedIcon = Icons.Outlined.Spa,
        label = "Servicios",
        testTag = "nav_tab_servicios"
    ),
    TopLevelDestination(
        route = EmpleadosRoute,
        selectedIcon = Icons.Filled.Badge,
        unselectedIcon = Icons.Outlined.Badge,
        label = "Empleados",
        testTag = "nav_tab_empleados"
    ),
    TopLevelDestination(
        route = ProductosRoute,
        // Cambiamos el ícono del carrito por una caja de inventario acorde al ABML
        selectedIcon = Icons.Filled.Inventory2,
        unselectedIcon = Icons.Outlined.Inventory2,
        label = "Productos",
        testTag = "nav_tab_productos"
    ),
    TopLevelDestination(
        route = FinanzasRoute,
        selectedIcon = Icons.Filled.AccountBalanceWallet,
        unselectedIcon = Icons.Outlined.AccountBalanceWallet,
        label = "Finanzas",
        testTag = "nav_tab_finanzas"
    )
)