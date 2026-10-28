package com.example.marcia.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.loginbasico.ui.ViewModel.LoginViewModel
import com.example.marcia.ui.screen.Empleados.EmpleadosScreen

import com.example.marcia.ui.screen.FinanzasScreen
import com.example.marcia.ui.screen.LoginScreen
import com.example.marcia.ui.screen.ServiciosScreen

@Composable
fun MarciaSalonApp(
    backStack: NavBackStack<NavKey> = rememberNavBackStack(EmpleadosRoute)
) {
    val loginViewModel: LoginViewModel = viewModel()
    val currentDestination = backStack.lastOrNull()

    // Check if current route is one of the top level destinations
    val showBottomBar by remember(currentDestination) {
        derivedStateOf {
            TOP_LEVEL_DESTINATIONS.any { topLevel ->
                topLevel.route == currentDestination
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.navigationBars,
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                MarciaBottomNavBar(
                    backStack = backStack,
                    currentDestination = currentDestination
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            entryProvider = entryProvider {
                entry<LoginRoute> {
                    LoginScreen(
                        loginViewModel = loginViewModel,
                        onLoginSuccess = {
                            backStack.clear()
                            backStack.add(ServiciosRoute)
                        }
                    )
                }

                // Tab 1: Turnos (Agenda)
//                entry<TurnosRoute> {
//                    TurnosScreen(
//                        onNavigateToTurnoDetail = { appointmentId ->
//                            backStack.add(DetalleTurnoRoute(appointmentId = appointmentId))
//                        },
//                        onNavigateToNuevoTurno = {
//                            backStack.add(NuevoTurnoRoute)
//                        }
//                    )
//                }

                // Tab 2: Servicios
                entry<ServiciosRoute> {
                    ServiciosScreen(
//                        onNavigateToServiceDetail = { serviceId ->
//                            backStack.add(DetalleServicioRoute(serviceId = serviceId))
//                        },
//                        onNavigateToNuevoTurno = {
//                            backStack.add(NuevoTurnoRoute)
//                        }
                    )
                }

                // Tab 3: Empleados
                entry<EmpleadosRoute> {
                    EmpleadosScreen(
                    )
                }

                // Tab 4: Finanzas
                entry<FinanzasRoute> {
                    FinanzasScreen()
                }

                // Sub-Screens
//                entry<DetalleTurnoRoute> { key ->
//                    DetalleTurnoScreen(
//                        appointmentId = key.appointmentId,
//                        onNavigateBack = { backStack.removeLastOrNull() }
//                    )
//                }
//                entry<NuevoTurnoRoute> {
//                    NuevoTurnoScreen(
//                        onNavigateBack = { backStack.removeLastOrNull() }
//                    )
//                }
//                entry<DetalleServicioRoute> { key ->
//                    DetalleServicioScreen(
//                        serviceId = key.serviceId,
//                        onNavigateBack = { backStack.removeLastOrNull() },
//                        onNavigateToNuevoTurno = { backStack.add(NuevoTurnoRoute) }
//                    )
//                }
//                entry<DetalleEmpleadoRoute> { key ->
//                    DetalleEmpleadoScreen(
//                        employeeId = key.employeeId,
//                        onNavigateBack = { backStack.removeLastOrNull() }
//                    )
//                }
            }
        )
    }
}

@Composable
fun MarciaBottomNavBar(
    backStack: NavBackStack<NavKey>,
    currentDestination: NavKey?
) {
    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .testTag("marcia_bottom_nav_bar"),
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        tonalElevation = 8.dp
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            val isSelected = currentDestination == destination.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        backStack.clear()
                        backStack.add(destination.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                        contentDescription = destination.label
                    )
                },
                label = {
                    Text(
                        text = destination.label,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                modifier = Modifier.testTag(destination.testTag)
            )
        }
    }
}
