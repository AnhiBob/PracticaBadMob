package com.example.practicabad.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicabad.R
import com.example.practicabad.ui.model.Product

@Composable
fun FavoriteScreen(
    onProductClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    // Состояние для списка избранного
    var favoriteProducts by remember {
        mutableStateOf(
            listOf(
                Product(
                    id = "1",
                    name = "Nike Air Max",
                    price = "13 999 ₽",
                    oldPrice = "15 999 ₽",
                    category = "Outdoor",
                    imageRes = R.drawable.image_1,
                    isFavorite = true
                ),
                Product(
                    id = "3",
                    name = "Puma RS-X",
                    price = "12 999 ₽",
                    oldPrice = null,
                    category = "Кежуал",
                    imageRes = R.drawable.image_3,
                    isFavorite = true
                ),
                Product(
                    id = "5",
                    name = "New Balance 574",
                    price = "14 999 ₽",
                    oldPrice = null,
                    category = "Outdoor",
                    imageRes = R.drawable.image_2,
                    isFavorite = true
                ),
                Product(
                    id = "7",
                    name = "Vans Old Skool",
                    price = "8 999 ₽",
                    oldPrice = null,
                    category = "Кежуал",
                    imageRes = R.drawable.image_1,
                    isFavorite = true
                )
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Верхняя панель
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Назад"
                )
            }

            Text(
                text = "Избранное",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        if (favoriteProducts.isEmpty()) {
            // Пустое состояние
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = Color.LightGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "В избранном пока пусто",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Добавляйте товары в избранное",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
            }
        } else {
            // Список избранных товаров
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoriteProducts) { product ->
                    FavoriteItem(
                        product = product,
                        onProductClick = onProductClick,
                        onRemoveClick = { productId ->
                            favoriteProducts = favoriteProducts.filter { it.id != productId }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(
    product: Product,
    onProductClick: (String) -> Unit,
    onRemoveClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onProductClick(product.id)
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Изображение
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Информация о товаре
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.category,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = product.price,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    product.oldPrice?.let {
                        Text(
                            text = it,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }
            }

            // Кнопка удаления из избранного
            IconButton(
                onClick = { onRemoveClick(product.id) }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Удалить из избранного",
                    tint = Color.Red
                )
            }
        }
    }
}