package com.example.marcia.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme


val Primary = Color(0xFF2F0211) // Deep Velvet Plum[cite: 10]
val OnPrimary = Color(0xFFFFFFFF)
val PrimaryContainer = Color(0xFF4A1525)
val OnPrimaryContainer = Color(0xFFC57A8B)

val Secondary = Color(0xFF8C4B55) // Bronze Rose[cite: 10]
val OnSecondary = Color(0xFFFFFFFF)
val SecondaryContainer = Color(0xFFFEAAB6)

val Background = Color(0xFFFCF9F7) // Alabaster Blush[cite: 10]
val Surface = Color(0xFFFCF9F7)
val SurfaceVariant = Color(0xFFE5E2E0)
val OnSurface = Color(0xFF1B1C1B)
val OnSurfaceVariant = Color(0xFF524346)

val Success = Color(0xFF2E5A44)
val SuccessContainer = Color(0xFFEBF3EE)

val MarciaColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    background = Background,
    surface = Surface,
    onSurface = OnSurface,
    onSurfaceVariant = OnSurfaceVariant,
    surfaceVariant = SurfaceVariant
)