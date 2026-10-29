package com.example.marcia.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Type-safe serializable navigation routes for Jetpack Compose Navigation 3.
 */
sealed interface ScreenRoute : NavKey

@Serializable
data object LoginRoute : ScreenRoute

// Primary Bottom Navigation Destinations
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

// Secondary / Workflow Destinations with serializable arguments
@Serializable
data class DetalleTurnoRoute(val appointmentId: String) : ScreenRoute

@Serializable
data object NuevoTurnoRoute : ScreenRoute

@Serializable
data class DetalleServicioRoute(val serviceId: String) : ScreenRoute

@Serializable
data class DetalleEmpleadoRoute(val employeeId: String) : ScreenRoute

/**
 * Representation of top-level destinations shown in the bottom navigation bar.
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
        route = FinanzasRoute,
        selectedIcon = Icons.Filled.AccountBalanceWallet,
        unselectedIcon = Icons.Outlined.AccountBalanceWallet,
        label = "Finanzas",
        testTag = "nav_tab_finanzas"
    )
)
