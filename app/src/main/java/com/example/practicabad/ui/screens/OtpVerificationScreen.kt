package com.example.practicabad.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practicabad.ui.viewmodel.OtpVerificationState
import com.example.practicabad.ui.viewmodel.OtpVerificationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OtpVerificationScreen(
    onBackClick: () -> Unit = {},
    onVerifyClick: (resetToken: String) -> Unit = {},
    viewModel: OtpVerificationViewModel = viewModel()
) {
    var otpCode by remember { mutableStateOf("") }
    var timer by remember { mutableStateOf(60) }
    var isTimerActive by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    var userEmail by remember { mutableStateOf("test@mail.com") } // В реальном проекте получать из предыдущего экрана
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Таймер
    LaunchedEffect(isTimerActive) {
        if (isTimerActive) {
            while (timer > 0) {
                delay(1000L)
                timer--
            }
            isTimerActive = false
        }
    }

    // Обработка состояния верификации
    LaunchedEffect(state) {
        when (state) {
            is OtpVerificationState.Success -> {
                val token = (state as OtpVerificationState.Success).resetToken
                if (token != null) {
                    onVerifyClick(token)
                } else {
                    errorMessage = "Ошибка получения токена"
                    showErrorDialog = true
                }
                viewModel.resetState()
            }
            is OtpVerificationState.Error -> {
                errorMessage = (state as OtpVerificationState.Error).message
                showErrorDialog = true
                isError = true
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
                enabled = state !is OtpVerificationState.Loading
            ) {
                Text("← Назад")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Подтверждение",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Введите код из email",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = otpCode,
            onValueChange = {
                if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                    otpCode = it
                    isError = false
                }
            },
            label = { Text("Код подтверждения") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            isError = isError,
            enabled = state !is OtpVerificationState.Loading,
            supportingText = if (isError) {
                { Text("Неверный код", color = MaterialTheme.colorScheme.error) }
            } else null
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Таймер и повторная отправка
        if (isTimerActive) {
            Text("Отправить повторно через $timer сек")
        } else {
            TextButton(
                onClick = {
                    timer = 60
                    isTimerActive = true
                    // TODO: повторная отправка кода
                },
                enabled = state !is OtpVerificationState.Loading
            ) {
                Text("Отправить код повторно")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Кнопка подтверждения или индикатор загрузки
        if (state is OtpVerificationState.Loading) {
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
                    viewModel.verifyOtp(userEmail, otpCode, "recovery")
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = otpCode.length == 6
            ) {
                Text("Подтвердить")
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
}