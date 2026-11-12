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
import com.example.marcia.ui.screen.TurnosScreen
import com.example.marcia.ui.screen.productos.DetalleProductoScreen
import com.example.marcia.ui.screen.productos.FormularioProductoScreen
import com.example.marcia.ui.screen.productos.ProductosAdminScreen
import com.example.marcia.ui.viewmodel.ProductosViewModel

/**
 * Componente raíz de la aplicación.
 * Coordina la navegación tipada (Navigation 3), el Scaffold principal
 * y la visibilidad dinámica de la barra de navegación inferior.
 */
@Composable
fun MarciaSalonApp(
    backStack: NavBackStack<NavKey> = rememberNavBackStack(EmpleadosRoute)
) {
    // ViewModel encargado de la sesión y validación de acceso
    val loginViewModel: LoginViewModel = viewModel()

    // ViewModel único y compartido para gestionar el estado del ABML de productos
    val productosViewModel: ProductosViewModel = viewModel()

    // Obtenemos la pantalla actual en el tope de la pila de navegación
    val currentDestination = backStack.lastOrNull()

    // Determinamos si se debe mostrar la barra inferior (solo en las secciones principales)
    val showBottomBar by remember(currentDestination) {
        derivedStateOf {
            TOP_LEVEL_DESTINATIONS.any { topLevel ->
                topLevel.route == currentDestination
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
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

        // Contenedor principal que resuelve qué pantalla dibujar según la ruta activa
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            entryProvider = entryProvider {

                // Pantalla inicial de inicio de sesión
                entry<LoginRoute> {
                    LoginScreen(
                        loginViewModel = loginViewModel,
                        onLoginSuccess = {
                            backStack.clear()
                            backStack.add(ServiciosRoute)
                        }
                    )
                }

                // Sección 1: Agenda de Turnos
                entry<TurnosRoute> {
                    TurnosScreen()
                }

                // Sección 2: Catálogo de Servicios
                entry<ServiciosRoute> {
                    ServiciosScreen()
                }

                // Sección 3: Gestión de Empleados
                entry<EmpleadosRoute> {
                    EmpleadosScreen()
                }

                // Sección 4: Control Financiero
                entry<FinanzasRoute> {
                    FinanzasScreen()
                }

                // ========================================================
                // MÓDULO DE ADMINISTRACIÓN DE PRODUCTOS (ABML) 📦
                // ========================================================

                // 1. LISTADO Y BAJA: Pantalla principal del inventario
                entry<ProductosRoute> {
                    ProductosAdminScreen(
                        viewModel = productosViewModel,
                        onNuevoProductoClick = {
                            // Navega al formulario vacío para crear un producto (ALTA)
                            backStack.add(FormularioProductoRoute(productoId = null))
                        },
                        onEditarProductoClick = { productoId ->
                            // Navega al formulario pasando el ID para editarlo (MODIFICACIÓN)
                            backStack.add(FormularioProductoRoute(productoId = productoId))
                        }
                    )
                }

                // 2. FICHA TÉCNICA: Vista detallada del producto
                entry<DetalleProductoRoute> { key ->
                    val productoEncontrado = productosViewModel.listaProductos.find { it.id == key.productoId }

                    if (productoEncontrado != null) {
                        DetalleProductoScreen(
                            producto = productoEncontrado,
                            onVolverClick = { backStack.removeLastOrNull() },
                            onEditarClick = {
                                // Permite saltar directo a la edición desde la vista de detalle
                                backStack.add(FormularioProductoRoute(productoId = productoEncontrado.id))
                            }
                        )
                    }
                }

                // 3. ALTA Y MODIFICACIÓN: Formulario con los 4 campos requeridos
                entry<FormularioProductoRoute> { key ->
                    // Si viene un ID buscamos el producto en memoria; si es null queda vacío para crear
                    val productoAEditar = key.productoId?.let { id ->
                        productosViewModel.listaProductos.find { it.id == id }
                    }

                    FormularioProductoScreen(
                        productoExistente = productoAEditar,
                        onGuardarClick = { nombre, descripcion, precio, stock ->
                            // Ejecuta el guardado en el ViewModel (crea o actualiza según corresponda)
                            productosViewModel.guardarProducto(
                                id = key.productoId,
                                nombre = nombre,
                                descripcion = descripcion,
                                precio = precio,
                                stock = stock
                            )
                            // Vuelve automáticamente al listado tras guardar
                            backStack.removeLastOrNull()
                        },
                        onVolverClick = { backStack.removeLastOrNull() }
                    )
                }

            }
        )
    }
}

/**
 * Barra de navegación inferior estilizada con Material 3.
 * Permite alternar entre las secciones principales limpiando el historial intermedio.
 */
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