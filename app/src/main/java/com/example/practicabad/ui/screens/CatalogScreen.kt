package com.example.practicabad.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import com.example.practicabad.ui.components.CategoryChip
import com.example.practicabad.ui.model.Category
import com.example.practicabad.ui.model.Product

@Composable
fun CatalogScreen(
    onProductClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("all") }
    var searchQuery by remember { mutableStateOf("") }

    // Категории
    val categories = listOf(
        Category("all", "Все", true),
        Category("outdoor", "Outdoor"),
        Category("tennis", "Tennis"),
        Category("men", "Мужские"),
        Category("women", "Женские"),
        Category("running", "Бег"),
        Category("casual", "Кежуал")
    )

    // Все товары
    val allProducts = listOf(
        Product(
            id = "1",
            name = "Nike Air Max",
            price = "13 999 ₽",
            oldPrice = "15 999 ₽",
            category = "Outdoor",
            imageRes = R.drawable.image_1,
            isFavorite = false
        ),
        Product(
            id = "2",
            name = "Adidas Ultraboost",
            price = "17 999 ₽",
            oldPrice = null,
            category = "Бег",
            imageRes = R.drawable.image_2,
            isFavorite = false
        ),
        Product(
            id = "3",
            name = "Puma RS-X",
            price = "12 999 ₽",
            oldPrice = null,
            category = "Кежуал",
            imageRes = R.drawable.image_3,
            isFavorite = false
        ),
        Product(
            id = "4",
            name = "Reebok Classic",
            price = "9 999 ₽",
            oldPrice = "11 999 ₽",
            category = "Кежуал",
            imageRes = R.drawable.image_1,
            isFavorite = false
        ),
        Product(
            id = "5",
            name = "New Balance 574",
            price = "14 999 ₽",
            oldPrice = null,
            category = "Outdoor",
            imageRes = R.drawable.image_2,
            isFavorite = false
        ),
        Product(
            id = "6",
            name = "Converse Chuck Taylor",
            price = "7 999 ₽",
            oldPrice = "9 999 ₽",
            category = "Кежуал",
            imageRes = R.drawable.image_3,
            isFavorite = false
        ),
        Product(
            id = "7",
            name = "Vans Old Skool",
            price = "8 999 ₽",
            oldPrice = null,
            category = "Кежуал",
            imageRes = R.drawable.image_1,
            isFavorite = false
        ),
        Product(
            id = "8",
            name = "Asics Gel-Kayano",
            price = "15 999 ₽",
            oldPrice = "17 999 ₽",
            category = "Бег",
            imageRes = R.drawable.image_2,
            isFavorite = false
        ),
        Product(
            id = "9",
            name = "Hoka Bondi 8",
            price = "16 999 ₽",
            oldPrice = null,
            category = "Бег",
            imageRes = R.drawable.image_3,
            isFavorite = false
        ),
        Product(
            id = "10",
            name = "Salomon Speedcross",
            price = "14 999 ₽",
            oldPrice = "16 999 ₽",
            category = "Outdoor",
            imageRes = R.drawable.image_1,
            isFavorite = false
        )
    )

    // Фильтрация товаров
    val filteredProducts = remember(selectedCategory, searchQuery) {
        allProducts.filter { product ->
            val categoryMatch = selectedCategory == "all" ||
                    product.category.lowercase() == selectedCategory.lowercase()
            val searchMatch = searchQuery.isEmpty() ||
                    product.name.contains(searchQuery, ignoreCase = true)
            categoryMatch && searchMatch
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Верхняя панель с поиском
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Заголовок и кнопка назад
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Назад",
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "Каталог",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Поле поиска
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Поиск товаров...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Поиск"
                    )
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.LightGray
                )
            )
        }

        // Категории (горизонтальный скролл)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                CategoryChip(
                    category = category.copy(
                        isSelected = category.id == selectedCategory
                    ),
                    onClick = { id ->
                        selectedCategory = id
                    }
                )
            }
        }

        // Результаты поиска
        if (filteredProducts.isEmpty()) {
            // Пустое состояние
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.LightGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Товары не найдены",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Попробуйте изменить параметры поиска",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
            }
        } else {
            // Сетка товаров
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredProducts.chunked(2)) { rowProducts ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowProducts.forEach { product ->
                            CatalogProductCard(
                                product = product,
                                onProductClick = onProductClick,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Если в ряду один товар, добавляем пустой Placeholder
                        if (rowProducts.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CatalogProductCard(
    product: Product,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
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
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // Изображение
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Категория
            Text(
                text = product.category,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.primary
            )

            // Название
            Text(
                text = product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                minLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Цена
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
                        fontSize = 11.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
        }
    }
}

// Вспомогательная функция для группировки товаров по 2
fun <T> List<T>.chunked(size: Int): List<List<T>> {
    val result = mutableListOf<List<T>>()
    for (i in indices step size) {
        result.add(subList(i, (i + size).coerceAtMost(size)))
    }
    return result
}