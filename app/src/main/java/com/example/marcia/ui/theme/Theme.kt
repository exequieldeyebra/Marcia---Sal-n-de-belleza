package com.example.marcia.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


//


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

import androidx.compose.ui.graphics.Color

@Composable
fun MarciaSalonDeBellezaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // 3. IMPORTANTE: Cambiamos dynamicColor a 'false' por defecto.
    // Esto evita que en Android 12+ la app tome los colores del fondo
    // de pantalla del usuario en lugar de los tuyos.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> MarciaColorScheme
        else -> MarciaColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}




private val DarkColorScheme = darkColorScheme(
    primary = PlumInversePrimary,
    onPrimary = PlumPrimary,
    primaryContainer = PlumPrimaryContainer,
    onPrimaryContainer = PlumInversePrimary,
    secondary = RoseSecondaryContainer,
    onSecondary = PlumPrimary,
    secondaryContainer = RoseOnSecondaryContainer,
    onSecondaryContainer = RoseSecondaryFixed,
    tertiary = ChampagneTertiaryFixed,
    onTertiary = ChampagneTertiary,
    background = Color(0xFF1B1417),
    surface = Color(0xFF1E1719),
    surfaceVariant = Color(0xFF36272B),
    onSurface = Color(0xFFF2ECEE),
    onSurfaceVariant = Color(0xFFD7C1C4),
    outline = OutlineColor,
    outlineVariant = OutlineVariantColor
)

private val LightColorScheme = lightColorScheme(
    primary = PlumPrimary,
    onPrimary = Color.White,
    primaryContainer = PlumPrimaryContainer,
    onPrimaryContainer = Color.White,
    secondary = RoseSecondary,
    onSecondary = Color.White,
    secondaryContainer = RoseSecondaryContainer,
    onSecondaryContainer = RoseOnSecondaryContainer,
    tertiary = ChampagneTertiary,
    onTertiary = Color.White,
    tertiaryContainer = ChampagneTertiaryContainer,
    onTertiaryContainer = ChampagneTertiaryFixed,
    background = SurfaceAlabaster,
    surface = SurfaceAlabaster,
    surfaceVariant = SurfaceContainer,
    onSurface = OnSurfaceText,
    onSurfaceVariant = OnSurfaceVariantText,
    surfaceContainerLowest = SurfaceContainerLowest,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainer = SurfaceContainer,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceContainerHighest = SurfaceContainerHighest,
    outline = OutlineColor,
    outlineVariant = OutlineVariantColor,
    error = StatusError,
    onError = Color.White,
    errorContainer = StatusErrorContainer,
    onErrorContainer = StatusError
)

@Composable
fun MarciaSalonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}