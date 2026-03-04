package com.example.practicabad.ui.model

import androidx.annotation.DrawableRes

data class Product(
    val id: String,
    val name: String,
    val price: String,
    val oldPrice: String? = null,
    val category: String,
    @DrawableRes val imageRes: Int,
    val isFavorite: Boolean = false
)