package com.example.practicabad.data.model

import androidx.annotation.DrawableRes

data class OnboardingSlide(
    val title: String,
    val subtitle: String,
    val description: String,
    val buttonText: String,
    @DrawableRes val imageRes: Int
)