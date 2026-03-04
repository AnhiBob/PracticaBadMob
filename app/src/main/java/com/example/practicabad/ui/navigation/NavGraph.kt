package com.example.practicabad.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practicabad.ui.screens.*

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
                    navController.navigate("forgot_password")
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

        composable("forgot_password") {
            ForgotPasswordScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSendClick = {
                    navController.navigate("otp_verification")
                }
            )
        }

        composable("otp_verification") {
            OtpVerificationScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onVerifyClick = {
                    navController.navigate("create_new_password")
                }
            )
        }

        composable("create_new_password") {
            CreateNewPasswordScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveClick = {
                    navController.navigate("sign_in") {
                        popUpTo("create_new_password") { inclusive = true }
                    }
                }
            )
        }
    }
}