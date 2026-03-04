package com.example.practicabad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicabad.data.api.RetrofitInstance
import com.example.practicabad.data.model.SignUpRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SignUpState {
    object Idle : SignUpState()
    object Loading : SignUpState()
    object Success : SignUpState()
    data class Error(val message: String) : SignUpState()
}

class SignUpViewModel : ViewModel() {
    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            try {
                _signUpState.value = SignUpState.Loading

                val response = RetrofitInstance.userManagementService.signUp(
                    SignUpRequest(email, password)
                )

                if (response.isSuccessful) {
                    _signUpState.value = SignUpState.Success
                } else {
                    val errorMessage = when (response.code()) {
                        400 -> "Неверный email или пароль"
                        422 -> "Неверный формат email"
                        429 -> "Слишком много запросов. Попробуйте позже"
                        else -> "Ошибка регистрации: ${response.message()}"
                    }
                    _signUpState.value = SignUpState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is java.net.ConnectException -> "Нет подключения к интернету"
                    is java.net.SocketTimeoutException -> "Таймаут соединения"
                    else -> "Ошибка сети: ${e.message}"
                }
                _signUpState.value = SignUpState.Error(errorMessage)
                Log.e("SignUpViewModel", "Error: ${e.message}", e)
            }
        }
    }

    fun resetState() {
        _signUpState.value = SignUpState.Idle
    }
}