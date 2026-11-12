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
import com.example.marcia.ui.screen.productos.CarritoScreen
import com.example.marcia.ui.screen.productos.CompraConfirmadaScreen
import com.example.marcia.ui.screen.productos.DetalleProductoScreen
import com.example.marcia.ui.screen.productos.ProductosScreen
import com.example.marcia.ui.viewmodel.ProductosViewModel

/**
 * Componente principal que coordina la navegación tipada (Navigation 3),
 * el diseño del Scaffold global y la barra de navegación inferior del salón de belleza.
 */
@Composable
fun MarciaSalonApp(
    backStack: NavBackStack<NavKey> = rememberNavBackStack(EmpleadosRoute)
) {
    // ViewModel para la autenticación de usuarios
    val loginViewModel: LoginViewModel = viewModel()

    // ViewModel compartido para gestionar todo el flujo de productos, stock y carrito
    val productosViewModel: ProductosViewModel = viewModel()

    val currentDestination = backStack.lastOrNull()

    // Validamos si la ruta actual pertenece a las pantallas principales para mostrar la barra inferior
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

        // Contenedor principal de navegación tipada
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            entryProvider = entryProvider {

                // Ruta de Login inicial
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
                entry<TurnosRoute> {
                    TurnosScreen()
                }

                // Tab 2: Servicios
                entry<ServiciosRoute> {
                    ServiciosScreen()
                }

                // Tab 3: Empleados
                entry<EmpleadosRoute> {
                    EmpleadosScreen()
                }

                // Tab 4: Finanzas
                entry<FinanzasRoute> {
                    FinanzasScreen()
                }

                // ========================================================
                // MÓDULO DE PRODUCTOS Y VENTAS 🛍️
                // ========================================================

                // Pantalla Principal / Catálogo de Productos
                entry<ProductosRoute> {
                    ProductosScreen(
                        viewModel = productosViewModel,
                        onProductoClick = { productoId ->
                            backStack.add(DetalleProductoRoute(productoId = productoId))
                        },
                        onIrAlCarritoClick = {
                            backStack.add(CarritoRoute)
                        }
                    )
                }

                // Pantalla de Detalle de un Producto individual
                entry<DetalleProductoRoute> { key ->
                    val productoEncontrado = productosViewModel.listaProductos.find { it.id == key.productoId }

                    if (productoEncontrado != null) {
                        DetalleProductoScreen(
                            producto = productoEncontrado,
                            onVolverClick = { backStack.removeLastOrNull() },
                            onAgregarAlCarrito = {
                                productosViewModel.agregarAlCarrito(productoEncontrado)
                                backStack.removeLastOrNull()
                            }
                        )
                    }
                }

                // Pantalla de Carrito de Compras y Cobro
                entry<CarritoRoute> {
                    CarritoScreen(
                        viewModel = productosViewModel,
                        onVolverClick = { backStack.removeLastOrNull() },
                        onVentaExitosa = {
                            val totalCobrado = productosViewModel.totalVenta
                            val medioPagoUsado = productosViewModel.medioPagoSeleccionado

                            // Al llamar a finalizarVenta(), se valida, se limpia el carrito y avanza
                            if (productosViewModel.finalizarVenta()) {
                                backStack.add(CompraConfirmadaRoute(total = totalCobrado, medioPago = medioPagoUsado))
                            }
                        }
                    )
                }

                // Pantalla de Confirmación de Venta Exitosa
                entry<CompraConfirmadaRoute> { key ->
                    CompraConfirmadaScreen(
                        totalCobrado = key.total,
                        medioPago = key.medioPago,
                        onVolverAlCatalogo = {
                            backStack.clear()
                            backStack.add(ProductosRoute)
                        }
                    )
                }

            }
        )
    }
}

/**
 * Componente de la barra de navegación inferior (Bottom Bar).
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