package com.example.practicabad.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practicabad.ui.screens.*
import androidx.compose.material3.Text

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "onboarding"  // Меняем стартовый экран
    ) {
        composable("onboarding") {
            OnboardScreen(
                onGetStartedClick = {
                    navController.navigate("sign_in") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }

        composable("sign_in") {
            SignInScreen(
                onSignUpClick = {
                    navController.navigate("sign_up")
                },
                onForgotPasswordClick = {
                    navController.navigate("forgot_password")
                },
                onSignInSuccess = {
                    navController.navigate("home")
                }
            )
        }

        composable("sign_up") {
            RegisterAccountScreen(
                onSignInClick = {
                    navController.navigate("sign_in") {
                        popUpTo("sign_up") { inclusive = true }
                    }
                },
                onSignUpSuccess = {
                    navController.navigate("home")
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
                onVerifyClick = { resetToken ->
                    navController.navigate("create_new_password/$resetToken")
                }
            )
        }

        composable("create_new_password/{resetToken}") { backStackEntry ->
            val resetToken = backStackEntry.arguments?.getString("resetToken") ?: ""
            CreateNewPasswordScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveClick = {
                    navController.navigate("sign_in") {
                        popUpTo("create_new_password") { inclusive = true }
                    }
                },
                resetToken = resetToken
            )
        }

        composable("home") {
            // TODO: добавить HomeScreen
            Text("Home Screen")
        }
    }
}