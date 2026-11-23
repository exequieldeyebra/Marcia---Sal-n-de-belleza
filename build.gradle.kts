// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false // Plugin principal de aplicación Android
    alias(libs.plugins.kotlin.compose) apply false // Plugin del compilador de Jetpack Compose
    alias(libs.plugins.kotlin.serialization) apply false // Plugin para serialización de rutas y datos[cite: 5]
    alias(libs.plugins.ksp) apply false // Declara KSP a nivel proyecto para que el módulo :app lo pueda usar
}