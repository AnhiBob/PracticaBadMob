package com.example.practicabad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicabad.data.api.RetrofitInstance
import com.example.practicabad.data.model.SignInRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SignInState {
    object Idle : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    data class Error(val message: String) : SignInState()
}

class SignInViewModel : ViewModel() {
    private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    val signInState: StateFlow<SignInState> = _signInState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                _signInState.value = SignInState.Loading

                val response = RetrofitInstance.userManagementService.signIn(
                    SignInRequest(email, password)
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        // Здесь можно сохранить токен
                        Log.d("SignIn", "User authenticated: ${it.user.email}")
                        _signInState.value = SignInState.Success
                    }
                } else {
                    val errorMessage = when (response.code()) {
                        400 -> "Неверный email или пароль"
                        401 -> "Неверные учетные данные"
                        422 -> "Неверный формат email"
                        429 -> "Слишком много попыток. Попробуйте позже"
                        else -> "Ошибка входа: ${response.message()}"
                    }
                    _signInState.value = SignInState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is java.net.ConnectException -> "Нет подключения к интернету"
                    is java.net.SocketTimeoutException -> "Таймаут соединения"
                    else -> "Ошибка сети: ${e.message}"
                }
                _signInState.value = SignInState.Error(errorMessage)
                Log.e("SignInViewModel", "Error: ${e.message}", e)
            }
        }
    }

    fun resetState() {
        _signInState.value = SignInState.Idle
    }
}