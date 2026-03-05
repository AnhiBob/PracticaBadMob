package com.example.practicabad.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practicabad.R
import com.example.practicabad.ui.viewmodel.SignInState
import com.example.practicabad.ui.viewmodel.SignInViewModel
import java.util.regex.Pattern

@Composable
fun SignInScreen(
    onSignUpClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onSignInSuccess: () -> Unit = {},
    viewModel: SignInViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val signInState by viewModel.signInState.collectAsState()
    val context = LocalContext.current

    // Функция проверки email
    fun isValidEmail(email: String): Boolean {
        if (email.isEmpty()) return false
        val emailPattern = Pattern.compile("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$")
        return emailPattern.matcher(email).matches()
    }

    // Обработка состояния входа
    LaunchedEffect(signInState) {
        when (signInState) {
            is SignInState.Success -> {
                onSignInSuccess()
                viewModel.resetState()
            }
            is SignInState.Error -> {
                errorMessage = (signInState as SignInState.Error).message
                showErrorDialog = true
                viewModel.resetState()
            }
            else -> {}
        }
    }

    val isButtonEnabled = email.isNotBlank() &&
            password.isNotBlank() &&
            isValidEmail(email) &&
            signInState !is SignInState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Вход",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Поле Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            isError = email.isNotEmpty() && !isValidEmail(email),
            enabled = signInState !is SignInState.Loading
        )

        if (email.isNotEmpty() && !isValidEmail(email)) {
            Text(
                text = "Неверный формат email",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Поле Пароль
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    enabled = signInState !is SignInState.Loading
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) R.drawable.eye_close
                            else R.drawable.eye_open
                        ),
                        contentDescription = if (passwordVisible) "Скрыть пароль"
                        else "Показать пароль"
                    )
                }
            },
            singleLine = true,
            enabled = signInState !is SignInState.Loading
        )

        // Ссылка "Забыли пароль?"
        TextButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier.align(Alignment.End),
            enabled = signInState !is SignInState.Loading
        ) {
            Text("Забыли пароль?")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Кнопка входа или индикатор загрузки
        if (signInState is SignInState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Button(
                onClick = { viewModel.signIn(email, password) },
                modifier = Modifier.fillMaxWidth(),
                enabled = isButtonEnabled
            ) {
                Text("Войти")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ссылка на регистрацию
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Нет аккаунта? ")
            TextButton(
                onClick = onSignUpClick,
                enabled = signInState !is SignInState.Loading
            ) {
                Text("Зарегистрироваться")
            }
        }
    }

    // Диалог с ошибкой
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Ошибка входа") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}