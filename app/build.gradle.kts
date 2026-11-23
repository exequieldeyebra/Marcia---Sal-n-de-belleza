plugins {
    alias(libs.plugins.android.application) // Plugin principal para compilar la app Android
    alias(libs.plugins.kotlin.compose) // Plugin del compilador de Jetpack Compose[cite: 5]
    alias(libs.plugins.kotlin.serialization) // Plugin para serializar rutas y objetos[cite: 5]
    alias(libs.plugins.ksp) // Activa KSP para que Room genere el código de la base de datos al compilar
}

android {
    namespace = "com.example.marcia" // Paquete base del proyecto[cite: 5]
    compileSdk {
        version = release(37) // Versión del SDK de Android con la que se compila[cite: 5]
    }

    defaultConfig {
        applicationId = "com.example.marcia" // Identificador único de la aplicación[cite: 5]
        minSdk = 30 // Versión mínima de Android requerida[cite: 5]
        targetSdk = 37 // Versión objetivo de Android[cite: 5]
        versionCode = 1 // Número interno de versión[cite: 5]
        versionName = "1.0" // Nombre de versión visible[cite: 5]

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Runner para tests instrumentados[cite: 5]
    }

    buildTypes {
        release {
            optimization {
                enable = false // Optimización desactivada en build de release[cite: 5]
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11 // Compatibilidad fuente con Java 11[cite: 5]
        targetCompatibility = JavaVersion.VERSION_11 // Bytecode generado compatible con Java 11[cite: 5]
    }
    buildFeatures {
        compose = true // Habilita Jetpack Compose en el módulo[cite: 5]
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom)) // BOM para mantener alineadas las versiones de Compose[cite: 5]
    implementation(libs.androidx.activity.compose) // Integración de Compose con Activity[cite: 5]
    implementation(libs.androidx.compose.foundation.layout) // Layouts base de Compose[cite: 5]
    implementation(libs.androidx.compose.material3) // Componentes de Material Design 3[cite: 5]
    implementation(libs.androidx.compose.ui) // Núcleo de interfaz de Compose[cite: 5]
    implementation(libs.androidx.compose.ui.graphics) // Colores, dibujo y gráficos en Compose[cite: 5]
    implementation(libs.androidx.compose.ui.tooling.preview) // Soporte para @Preview en el IDE[cite: 5]
    implementation(libs.androidx.core.ktx) // Extensiones Kotlin para APIs de Android[cite: 5]
    implementation(libs.androidx.lifecycle.runtime.ktx) // Ciclo de vida y soporte de corrutinas[cite: 5]
    implementation(libs.androidx.material3) // Material 3[cite: 5]
    implementation(libs.kotlinx.serialization.json) // Serialización JSON[cite: 5]
    testImplementation(libs.junit) // Pruebas unitarias en JVM local[cite: 5]
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM para pruebas instrumentadas de Compose[cite: 5]
    androidTestImplementation(libs.androidx.compose.ui.test.junit4) // Reglas de test de UI en Compose[cite: 5]
    androidTestImplementation(libs.androidx.espresso.core) // Pruebas instrumentadas con Espresso[cite: 5]
    androidTestImplementation(libs.androidx.junit) // JUnit para pruebas en dispositivo/emulador[cite: 5]
    debugImplementation(libs.androidx.compose.ui.test.manifest) // Manifest auxiliar para debug y tests[cite: 5]
    debugImplementation(libs.androidx.compose.ui.tooling) // Herramientas de inspección en modo debug[cite: 5]
    implementation("androidx.compose.material:material-icons-extended") // Íconos extendidos de Material[cite: 5]

    // Navigation 3[cite: 5]
    implementation(libs.androidx.navigation3.ui) // Contenedor visual NavDisplay de Navigation 3[cite: 5]
    implementation(libs.androidx.navigation3.runtime) // Backstack y gestión de entradas de Navigation 3[cite: 5]
    implementation(libs.androidx.lifecycle.viewmodel.navigation3) // Integración de ViewModel con Navigation 3[cite: 5]
    implementation(libs.androidx.material3.adaptive.navigation3) // Soporte adaptativo para Navigation 3[cite: 5]
    implementation(libs.kotlinx.serialization.core) // Serialización tipada para rutas[cite: 5]

    // Room: base de datos local SQLite
    implementation(libs.androidx.room.runtime) // Motor principal de Room en ejecución
    implementation(libs.androidx.room.ktx) // Extensiones de Kotlin con Flow y suspend
    ksp(libs.androidx.room.compiler) // Generador de código de Room vía KSP
}