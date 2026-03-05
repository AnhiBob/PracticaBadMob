package com.example.practicabad.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
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
        startDestination = "onboarding"
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
                    navController.navigate("home") {
                        popUpTo("sign_in") { inclusive = true }
                    }
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
                    navController.navigate("home") {
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
            HomeScreen(
                onProductClick = { productId ->
                    // Переход на детали товара (День 3)
                    navController.navigate("product_detail/$productId")
                },
                onCartClick = {
                    // Переход в корзину (День 4)
                    navController.navigate("cart")
                },
                onSearchClick = {
                    // Поиск
                },
                onProfileClick = {
                    navController.navigate("profile")
                },
                onFavoriteClick = {
                    navController.navigate("favorite")
                },
                onNotificationsClick = {
                    navController.navigate("notifications")
                }
            )
        }

        composable("profile") {
            ProfileScreen(
                onEditProfileClick = {
                    // Режим редактирования уже внутри экрана
                },
                onChangePhotoClick = {
                    // Камера уже внутри экрана
                },
                onSaveClick = {
                    navController.popBackStack()
                },
                onSignOutClick = {
                    navController.navigate("sign_in") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }

        composable("favorite") {
            // Заглушка для избранного
            androidx.compose.material3.Text(
                text = "Избранное",
                modifier = androidx.compose.ui.Modifier.fillMaxSize()
            )
        }

        composable("notifications") {
            // Заглушка для уведомлений
            androidx.compose.material3.Text(
                text = "Уведомления",
                modifier = androidx.compose.ui.Modifier.fillMaxSize()
            )
        }

        composable("cart") {
            // Заглушка для корзины
            androidx.compose.material3.Text(
                text = "Корзина",
                modifier = androidx.compose.ui.Modifier.fillMaxSize()
            )
        }

        composable("product_detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            // Заглушка для деталей товара
            androidx.compose.material3.Text(
                text = "Детали товара: $productId",
                modifier = androidx.compose.ui.Modifier.fillMaxSize()
            )
        }
        composable("catalog") {
            CatalogScreen(
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}