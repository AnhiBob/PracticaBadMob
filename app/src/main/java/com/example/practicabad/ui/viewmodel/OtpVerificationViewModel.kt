package com.example.practicabad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicabad.data.api.RetrofitInstance
import com.example.practicabad.data.model.VerifyOtpRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class OtpVerificationState {
    object Idle : OtpVerificationState()
    object Loading : OtpVerificationState()
    data class Success(val resetToken: String?) : OtpVerificationState()
    data class Error(val message: String) : OtpVerificationState()
}

class OtpVerificationViewModel : ViewModel() {
    private val _state = MutableStateFlow<OtpVerificationState>(OtpVerificationState.Idle)
    val state: StateFlow<OtpVerificationState> = _state

    private var _resetToken: String? = null
    val resetToken: String? get() = _resetToken

    fun verifyOtp(email: String, otpCode: String, type: String = "recovery") {
        viewModelScope.launch {
            try {
                _state.value = OtpVerificationState.Loading

                val response = RetrofitInstance.userManagementService.verifyOtp(
                    VerifyOtpRequest(
                        email = email,
                        token = otpCode,
                        type = type
                    )
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        _resetToken = it.refresh_token
                        _state.value = OtpVerificationState.Success(it.refresh_token)
                    } ?: run {
                        _state.value = OtpVerificationState.Error("Пустой ответ от сервера")
                    }
                } else {
                    val errorMessage = when (response.code()) {
                        401 -> "Неверный код подтверждения"
                        404 -> "Код не найден"
                        429 -> "Слишком много попыток"
                        else -> "Ошибка: ${response.message()}"
                    }
                    _state.value = OtpVerificationState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is java.net.ConnectException -> "Нет подключения к интернету"
                    is java.net.SocketTimeoutException -> "Таймаут соединения"
                    else -> "Ошибка сети: ${e.message}"
                }
                _state.value = OtpVerificationState.Error(errorMessage)
                Log.e("OtpVM", "Error: ${e.message}", e)
            }
        }
    }

    fun resetState() {
        _state.value = OtpVerificationState.Idle
        _resetToken = null
    }
}