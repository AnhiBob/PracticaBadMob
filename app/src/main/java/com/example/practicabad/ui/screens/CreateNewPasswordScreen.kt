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
import com.example.practicabad.ui.viewmodel.ChangePasswordState
import com.example.practicabad.ui.viewmodel.CreateNewPasswordViewModel

@Composable
fun CreateNewPasswordScreen(
    onBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    resetToken: String = "",
    viewModel: CreateNewPasswordViewModel = viewModel()
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmError by remember { mutableStateOf<String?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    // Обработка состояния смены пароля
    LaunchedEffect(state) {
        when (state) {
            is ChangePasswordState.Success -> {
                showSuccessDialog = true
                viewModel.resetState()
            }
            is ChangePasswordState.Error -> {
                errorMessage = (state as ChangePasswordState.Error).message
                showErrorDialog = true
                viewModel.resetState()
            }
            else -> {}
        }
    }

    fun validatePasswords(): Boolean {
        var isValid = true

        if (password.length < 6) {
            passwordError = "Пароль должен содержать минимум 6 символов"
            isValid = false
        } else {
            passwordError = null
        }

        if (confirmPassword != password) {
            confirmError = "Пароли не совпадают"
            isValid = false
        } else {
            confirmError = null
        }

        return isValid
    }

    val isButtonEnabled = password.isNotBlank() &&
            confirmPassword.isNotBlank() &&
            state !is ChangePasswordState.Loading

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
                enabled = state !is ChangePasswordState.Loading
            ) {
                Text("← Назад")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Новый пароль",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Поле Пароль
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = null
            },
            label = { Text("Новый пароль") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    enabled = state !is ChangePasswordState.Loading
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) R.drawable.eye_close else R.drawable.eye_open
                        ),
                        contentDescription = if (passwordVisible) "Скрыть пароль" else "Показать пароль"
                    )
                }
            },
            singleLine = true,
            isError = passwordError != null,
            enabled = state !is ChangePasswordState.Loading
        )

        if (passwordError != null) {
            Text(
                text = passwordError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Поле Подтверждение пароля
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmError = null
            },
            label = { Text("Подтвердите пароль") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(
                    onClick = { confirmVisible = !confirmVisible },
                    enabled = state !is ChangePasswordState.Loading
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (confirmVisible) R.drawable.eye_close else R.drawable.eye_open
                        ),
                        contentDescription = if (confirmVisible) "Скрыть пароль" else "Показать пароль"
                    )
                }
            },
            singleLine = true,
            isError = confirmError != null,
            enabled = state !is ChangePasswordState.Loading
        )

        if (confirmError != null) {
            Text(
                text = confirmError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Кнопка сохранения или индикатор загрузки
        if (state is ChangePasswordState.Loading) {
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
                    if (validatePasswords()) {
                        viewModel.changePassword(resetToken, password)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isButtonEnabled
            ) {
                Text("Сохранить")
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
            title = { Text("Успех") },
            text = { Text("Пароль успешно изменен") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        onSaveClick()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}