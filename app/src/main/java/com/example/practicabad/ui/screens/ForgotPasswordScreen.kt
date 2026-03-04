package com.example.practicabad.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practicabad.ui.viewmodel.ForgotPasswordState
import com.example.practicabad.ui.viewmodel.ForgotPasswordViewModel
import java.util.regex.Pattern

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit = {},
    onSendClick: () -> Unit = {},
    viewModel: ForgotPasswordViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val state by viewModel.state.collectAsState()

    // Функция проверки email
    fun isValidEmail(email: String): Boolean {
        if (email.isEmpty()) return false
        val emailPattern = Pattern.compile("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$")
        return emailPattern.matcher(email).matches()
    }

    // Обработка состояния
    LaunchedEffect(state) {
        when (state) {
            is ForgotPasswordState.Success -> {
                showSuccessDialog = true
                viewModel.resetState()
            }
            is ForgotPasswordState.Error -> {
                errorMessage = (state as ForgotPasswordState.Error).message
                showErrorDialog = true
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Кнопка назад
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(
                onClick = onBackClick,
                enabled = state !is ForgotPasswordState.Loading
            ) {
                Text("← Назад")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Восстановление пароля",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Введите ваш email, и мы отправим код для сброса пароля",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            isError = email.isNotEmpty() && !isValidEmail(email),
            enabled = state !is ForgotPasswordState.Loading
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

        Spacer(modifier = Modifier.height(24.dp))

        // Кнопка отправки или индикатор загрузки
        if (state is ForgotPasswordState.Loading) {
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
                onClick = {
                    if (isValidEmail(email)) {
                        viewModel.recoverPassword(email)
                    } else {
                        errorMessage = "Введите корректный email"
                        showErrorDialog = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = email.isNotBlank() && isValidEmail(email)
            ) {
                Text("Отправить")
            }
        }
    }

    // Диалог ошибки
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Ошибка") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    // Диалог успеха
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Проверьте email") },
            text = { Text((state as? ForgotPasswordState.Success)?.message ?: "Код отправлен") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        onSendClick()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}