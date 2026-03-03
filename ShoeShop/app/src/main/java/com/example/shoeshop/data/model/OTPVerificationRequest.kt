

data class OTPVerificationRequest(
    val type: String,
    val email: String,
    val token: String
)