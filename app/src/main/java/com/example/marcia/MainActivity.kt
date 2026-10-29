package com.example.marcia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loginbasico.ui.ViewModel.LoginViewModel
//import com.example.marcia.ui.navigation.MarciaAppNavigation
import com.example.marcia.ui.navigation.MarciaSalonApp
import com.example.marcia.ui.theme.MarciaSalonDeBellezaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarciaSalonDeBellezaTheme(

            ) {
                MarciaSalonApp()
            }

//
//            MarciaSalonDeBellezaTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    val loginViewModel: LoginViewModel = viewModel()
//                    //MarciaAppNavigation()
//
//                }
//            }
        }
    }
}