package com.example.practicabad.data.api

import com.example.practicabad.data.model.*
import retrofit2.Response
import retrofit2.http.*

const val SUPABASE_URL = "https://ltljvsjtbekeiwzbpkou.supabase.co/"
const val API_KEY = "sb_publishable_g91qJqa_he7kqPFZ2_6-2Q_qLHs-2o5"

interface UserManagementService {

    @Headers(
        "apikey: $API_KEY",
        "Content-Type: application/json"
    )
    @POST("auth/v1/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @Headers(
        "apikey: $API_KEY",
        "Content-Type: application/json"
    )
    @POST("auth/v1/token?grant_type=password")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<SignInResponse>

    @Headers(
        "apikey: $API_KEY",
        "Content-Type: application/json"
    )
    @POST("auth/v1/recover")
    suspend fun recoverPassword(@Body request: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @Headers(
        "apikey: $API_KEY",
        "Content-Type: application/json"
    )
    @POST("auth/v1/verify")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): Response<VerifyOtpResponse>

    @Headers(
        "apikey: $API_KEY",
        "Content-Type: application/json"
    )
    @PUT("auth/v1/user")
    suspend fun changePassword(
        @Header("Authorization") authorization: String,
        @Body request: ChangePasswordRequest
    ): Response<ChangePasswordResponse>
}