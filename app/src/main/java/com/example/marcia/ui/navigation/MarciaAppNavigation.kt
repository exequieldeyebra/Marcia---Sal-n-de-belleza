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
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.loginbasico.ui.ViewModel.LoginViewModel
import com.example.marcia.ui.screen.Empleados.EmpleadosScreen
import com.example.marcia.ui.screen.FinanzasScreen
import com.example.marcia.ui.screen.LoginScreen
import com.example.marcia.ui.screen.ServiciosScreen
//import com.example.marcia.ui.screen.TurnosScreen
//import com.example.ui.screens.DetalleEmpleadoScreen
//import com.example.ui.screens.DetalleServicioScreen
//import com.example.ui.screens.DetalleTurnoScreen
//import com.example.ui.screens.EmpleadosScreen
//import com.example.ui.screens.FinanzasScreen
//import com.example.ui.screens.NuevoTurnoScreen
//import com.example.ui.screens.ServiciosScreen
//import com.example.ui.screens.TurnosScreen
//import com.example.ui.theme.PlumPrimary
//import com.example.ui.theme.PlumPrimaryContainer
//import com.example.ui.theme.RoseSecondary
//import com.example.ui.theme.RoseSecondaryFixed

@Composable
fun MarciaSalonApp(
    navController: NavHostController = rememberNavController()
) {
    val loginViewModel: LoginViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Check if current route is one of the top level destinations
    val showBottomBar by remember(currentDestination) {
        derivedStateOf {
            TOP_LEVEL_DESTINATIONS.any { topLevel ->
                currentDestination?.hasRoute(topLevel.route::class) == true
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
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LoginRoute,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable <LoginRoute>{
                LoginScreen(loginViewModel, onLoginSuccess = {navController.navigate(ServiciosRoute)}) }
            // Tab 1: Turnos (Agenda)
//            composable<TurnosRoute> {
//                TurnosScreen(
//                    onNavigateToTurnoDetail = { appointmentId ->
//                        navController.navigate(DetalleTurnoRoute(appointmentId = appointmentId))
//                    },
//                    onNavigateToNuevoTurno = {
//                        navController.navigate(NuevoTurnoRoute)
//                    }
//                )
//            }

            // Tab 2: Servicios
            composable<ServiciosRoute> {
                ServiciosScreen(

//                    onNavigateToServiceDetail = { serviceId ->
//                        navController.navigate(DetalleServicioRoute(serviceId = serviceId))
//                    },
//                    onNavigateToNuevoTurno = {
//                        navController.navigate(NuevoTurnoRoute)
//                    }
                )
            }

            // Tab 3: Empleados
//            composable<EmpleadosRoute> {
//                EmpleadosScreen(
//                    onNavigateToEmployeeDetail = { employeeId ->
//                        navController.navigate(DetalleEmpleadoRoute(employeeId = employeeId))
//                    }
//                )
//            }

            // Tab 4: Finanzas
            composable<FinanzasRoute> {
                FinanzasScreen()
            }

//            // Sub-Screen: Detalle de Turno (Ficha Clínica & Sesión)
//            composable<DetalleTurnoRoute> { backStackEntry ->
//                val route = backStackEntry.toRoute<DetalleTurnoRoute>()
//                DetalleTurnoScreen(
//                    appointmentId = route.appointmentId,
//                    onNavigateBack = {
//                        navController.popBackStack()
//                    }
//                )
//            }
//
//            // Sub-Screen: Nuevo Turno
//            composable<NuevoTurnoRoute> {
//                NuevoTurnoScreen(
//                    onNavigateBack = {
//                        navController.popBackStack()
//                    }
//                )
//            }
//
//            // Sub-Screen: Detalle de Servicio
//            composable<DetalleServicioRoute> { backStackEntry ->
//                val route = backStackEntry.toRoute<DetalleServicioRoute>()
//                DetalleServicioScreen(
//                    serviceId = route.serviceId,
//                    onNavigateBack = {
//                        navController.popBackStack()
//                    },
//                    onNavigateToNuevoTurno = {
//                        navController.navigate(NuevoTurnoRoute)
//                    }
//                )
//            }
//
//            // Sub-Screen: Detalle de Empleado
//            composable<DetalleEmpleadoRoute> { backStackEntry ->
//                val route = backStackEntry.toRoute<DetalleEmpleadoRoute>()
//                DetalleEmpleadoScreen(
//                    employeeId = route.employeeId,
//                    onNavigateBack = {
//                        navController.popBackStack()
//                    }
//                )
//            }
        }
    }
}

@Composable
fun MarciaBottomNavBar(
    navController: NavHostController,
    currentDestination: androidx.navigation.NavDestination?
) {
    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .testTag("marcia_bottom_nav_bar"),
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        tonalElevation = 8.dp
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(destination.route::class)
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
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
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = RoseSecondaryFixed,
//                    selectedTextColor = PlumPrimary,
//                    indicatorColor = PlumPrimaryContainer,
//                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
//                ),
                modifier = Modifier.testTag(destination.testTag)
            )
        }
    }
}