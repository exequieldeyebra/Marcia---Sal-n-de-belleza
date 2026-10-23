package com.example.marcia.ui.navigation

import LoginScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.marcia.ui.screen.EmpleadosScreen
import com.example.marcia.ui.screen.FinanzasScreen
import com.example.marcia.ui.screen.ServiciosScreen
import com.example.marcia.ui.screen.TurnosScreen

@Composable
fun MarciaAppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("dashboard") {
            MainDashboardScreen()
        }
    }
}

@Composable
fun MainDashboardScreen() {
    val bottomNavController = rememberNavController()
    val items = listOf("turnos", "servicios", "empleados", "finanzas")

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                NavigationBarItem(
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Turnos") },
                    label = { Text("Turnos") },
                    selected = currentRoute == "turnos",
                    onClick = { bottomNavController.navigate("turnos") { popUpTo(bottomNavController.graph.findStartDestination().id) { saveState = true } } }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Face, contentDescription = "Servicios") },
                    label = { Text("Servicios") },
                    selected = currentRoute == "servicios",
                    onClick = { bottomNavController.navigate("servicios") { popUpTo(bottomNavController.graph.findStartDestination().id) { saveState = true } } }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Empleados") },
                    label = { Text("Empleados") },
                    selected = currentRoute == "empleados",
                    onClick = { bottomNavController.navigate("empleados") { popUpTo(bottomNavController.graph.findStartDestination().id) { saveState = true } } }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.AccountBalanceWallet, contentDescription = "Finanzas") },
                    label = { Text("Finanzas") },
                    selected = currentRoute == "finanzas",
                    onClick = { bottomNavController.navigate("finanzas") { popUpTo(bottomNavController.graph.findStartDestination().id) { saveState = true } } }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "turnos",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("turnos") { TurnosScreen() }
            composable("servicios") { ServiciosScreen() }
            composable("empleados") { EmpleadosScreen() }
            composable("finanzas") { FinanzasScreen() }
        }
    }
}