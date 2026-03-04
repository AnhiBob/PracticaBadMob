package com.example.practicabad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicabad.data.api.RetrofitInstance
import com.example.practicabad.data.model.ChangePasswordRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ChangePasswordState {
    object Idle : ChangePasswordState()
    object Loading : ChangePasswordState()
    object Success : ChangePasswordState()
    data class Error(val message: String) : ChangePasswordState()
}

class CreateNewPasswordViewModel : ViewModel() {
    private val _state = MutableStateFlow<ChangePasswordState>(ChangePasswordState.Idle)
    val state: StateFlow<ChangePasswordState> = _state

    fun changePassword(token: String, newPassword: String) {
        viewModelScope.launch {
            try {
                _state.value = ChangePasswordState.Loading

                val response = RetrofitInstance.userManagementService.changePassword(
                    authorization = "Bearer $token",
                    request = ChangePasswordRequest(password = newPassword)
                )

                if (response.isSuccessful) {
                    _state.value = ChangePasswordState.Success
                } else {
                    val errorMessage = when (response.code()) {
                        401 -> "Недействительный токен"
                        422 -> "Слишком слабый пароль"
                        429 -> "Слишком много попыток"
                        else -> "Ошибка: ${response.message()}"
                    }
                    _state.value = ChangePasswordState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is java.net.ConnectException -> "Нет подключения к интернету"
                    is java.net.SocketTimeoutException -> "Таймаут соединения"
                    else -> "Ошибка сети: ${e.message}"
                }
                _state.value = ChangePasswordState.Error(errorMessage)
                Log.e("ChangePasswordVM", "Error: ${e.message}", e)
            }
        }
    }

    fun resetState() {
        _state.value = ChangePasswordState.Idle
    }
}