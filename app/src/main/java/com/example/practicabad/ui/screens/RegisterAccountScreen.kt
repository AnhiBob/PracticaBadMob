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
import com.example.practicabad.ui.viewmodel.SignUpState
import com.example.practicabad.ui.viewmodel.SignUpViewModel
import java.util.regex.Pattern

@Composable
fun RegisterAccountScreen(
    onSignInClick: () -> Unit = {},
    onSignUpSuccess: () -> Unit = {},
    viewModel: SignUpViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isAgreed by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val signUpState by viewModel.signUpState.collectAsState()
    val context = LocalContext.current

    // Обработка состояния регистрации
    LaunchedEffect(signUpState) {
        when (signUpState) {
            is SignUpState.Success -> {
                onSignUpSuccess()
                viewModel.resetState()
            }
            is SignUpState.Error -> {
                errorMessage = (signUpState as SignUpState.Error).message
                showErrorDialog = true
                viewModel.resetState()
            }
            else -> {}
        }
    }

    // Функция проверки email
    fun isValidEmail(email: String): Boolean {
        if (email.isEmpty()) return false

        val emailPattern = Pattern.compile(
            "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$"
        )
        return emailPattern.matcher(email).matches()
    }

    // Кнопка активна только если все поля заполнены и есть согласие
    val isButtonEnabled = name.isNotBlank() &&
            email.isNotBlank() &&
            password.isNotBlank() &&
            isAgreed &&
            signUpState !is SignUpState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Регистрация",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Поле Имя
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = signUpState !is SignUpState.Loading
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Поле Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            isError = email.isNotEmpty() && !isValidEmail(email),
            enabled = signUpState !is SignUpState.Loading
        )

        if (email.isNotEmpty() && !isValidEmail(email)) {
            Text(
                text = "Email должен быть вида: name@domen.ru (только маленькие буквы и цифры)",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

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
                    enabled = signUpState !is SignUpState.Loading
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
            enabled = signUpState !is SignUpState.Loading
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Чекбокс согласия
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isAgreed,
                onCheckedChange = { isAgreed = it },
                enabled = signUpState !is SignUpState.Loading
            )
            Text(
                text = "Я согласен на обработку персональных данных",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка регистрации или индикатор загрузки
        if (signUpState is SignUpState.Loading) {
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
                    if (!isValidEmail(email)) {
                        errorMessage = "Неверный формат email"
                        showErrorDialog = true
                    } else {
                        viewModel.signUp(email, password)
                    }
                },
                enabled = isButtonEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Зарегистрироваться")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ссылка на вход
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Уже есть аккаунт? ")
            TextButton(
                onClick = onSignInClick,
                enabled = signUpState !is SignUpState.Loading
            ) {
                Text("Войти")
            }
        }
    }

    // Диалог с ошибкой
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