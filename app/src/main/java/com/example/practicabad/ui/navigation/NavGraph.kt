package com.example.practicabad.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practicabad.ui.screens.CatalogScreen
import com.example.practicabad.ui.screens.CreateNewPasswordScreen
import com.example.practicabad.ui.screens.FavoriteScreen
import com.example.practicabad.ui.screens.ForgotPasswordScreen
import com.example.practicabad.ui.screens.HomeScreen
import com.example.practicabad.ui.screens.OnboardScreen
import com.example.practicabad.ui.screens.OtpVerificationScreen
import com.example.practicabad.ui.screens.ProductDetailScreen
import com.example.practicabad.ui.screens.ProfileScreen
import com.example.practicabad.ui.screens.RegisterAccountScreen
import com.example.practicabad.ui.screens.SignInScreen

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
                    navController.navigate("product_detail/$productId")
                },
                onCartClick = {
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
                },
                onCatalogClick = {
                    navController.navigate("catalog")
                }
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

        composable("favorite") {
            FavoriteScreen(
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("product_detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                onBackClick = {
                    navController.popBackStack()
                },
                onAddToCartClick = { id ->
                    navController.navigate("cart")
                },
                onFavoriteClick = { id, isFavorite ->
                    // Обработка избранного
                    println("Товар $id избранное: $isFavorite")
                }
            )
        }

        composable("profile") {
            ProfileScreen(
                onEditProfileClick = {
                    // Режим редактирования внутри экрана
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

        composable("cart") {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Корзина (в разработке)")
            }
        }

        composable("notifications") {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Уведомления (в разработке)")
            }
        }
    }
}