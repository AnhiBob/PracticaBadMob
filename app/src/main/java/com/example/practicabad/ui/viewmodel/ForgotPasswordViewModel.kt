package com.example.practicabad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicabad.data.api.RetrofitInstance
import com.example.practicabad.data.model.ForgotPasswordRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ForgotPasswordState {
    object Idle : ForgotPasswordState()
    object Loading : ForgotPasswordState()
    data class Success(val message: String) : ForgotPasswordState()
    data class Error(val message: String) : ForgotPasswordState()
}

class ForgotPasswordViewModel : ViewModel() {
    private val _state = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Idle)
    val state: StateFlow<ForgotPasswordState> = _state

    fun recoverPassword(email: String) {
        viewModelScope.launch {
            try {
                _state.value = ForgotPasswordState.Loading

                val response = RetrofitInstance.userManagementService.recoverPassword(
                    ForgotPasswordRequest(email)
                )

                if (response.isSuccessful) {
                    _state.value = ForgotPasswordState.Success(
                        "Код восстановления отправлен на $email"
                    )
                } else {
                    val errorMessage = when (response.code()) {
                        400 -> "Неверный email"
                        404 -> "Пользователь с таким email не найден"
                        429 -> "Слишком много запросов. Попробуйте позже"
                        else -> "Ошибка: ${response.message()}"
                    }
                    _state.value = ForgotPasswordState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is java.net.ConnectException -> "Нет подключения к интернету"
                    is java.net.SocketTimeoutException -> "Таймаут соединения"
                    else -> "Ошибка сети: ${e.message}"
                }
                _state.value = ForgotPasswordState.Error(errorMessage)
                Log.e("ForgotPasswordVM", "Error: ${e.message}", e)
            }
        }
    }

    fun resetState() {
        _state.value = ForgotPasswordState.Idle
    }
}