package com.example.practicabad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.practicabad.ui.screens.RegisterAccountScreen
import com.example.practicabad.ui.theme.PracticaBadTheme   // ЭТОТ ИМПОРТ ДОЛЖЕН БЫТЬ

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticaBadTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterAccountScreen()
                }
            }
        }
    }
}