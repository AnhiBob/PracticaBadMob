package com.example.practicabad.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OtpVerificationScreen(
    onBackClick: () -> Unit = {},
    onVerifyClick: () -> Unit = {}
) {
    var otpCode by remember { mutableStateOf("") }
    var timer by remember { mutableStateOf(60) }
    var isTimerActive by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

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
            TextButton(onClick = onBackClick) {
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
                }
            ) {
                Text("Отправить код повторно")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (otpCode.length == 6) {
                    // TODO: проверить код
                    if (otpCode == "123456") { // для примера
                        onVerifyClick()
                    } else {
                        isError = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = otpCode.length == 6
        ) {
            Text("Подтвердить")
        }
    }
}