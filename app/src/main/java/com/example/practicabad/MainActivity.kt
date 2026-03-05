package com.example.practicabad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.practicabad.ui.navigation.NavGraph
import com.example.practicabad.ui.theme.PracticaBadTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Устанавливаем SplashScreen ДО super.onCreate
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            PracticaBadTheme {
                NavGraph()
            }
        }
    }
}