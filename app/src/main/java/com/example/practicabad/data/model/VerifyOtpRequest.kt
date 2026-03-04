package com.example.practicabad.data.model

data class VerifyOtpRequest(
    val email: String,
    val token: String,
    val type: String
)