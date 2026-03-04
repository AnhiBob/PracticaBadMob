package com.example.practicabad.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit = {},
    onSendClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

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
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (email.isNotBlank()) {
                    showDialog = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotBlank()
        ) {
            Text("Отправить")
        }
    }

    // Диалог подтверждения
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Проверьте email") },
            text = { Text("Мы отправили код восстановления на $email") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onSendClick()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}