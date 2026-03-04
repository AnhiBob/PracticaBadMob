package com.example.practicabad.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practicabad.ui.screens.RegisterAccountScreen
import com.example.practicabad.ui.screens.SignInScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "sign_in"
    ) {
        composable("sign_in") {
            SignInScreen(
                onSignUpClick = {
                    navController.navigate("sign_up")
                },
                onForgotPasswordClick = {
                    // TODO: перейти на восстановление пароля
                }
            )
        }

        composable("sign_up") {
            RegisterAccountScreen(
                onSignInClick = {
                    navController.navigate("sign_in") {
                        popUpTo("sign_up") { inclusive = true }
                    }
                }
            )
        }
    }
}