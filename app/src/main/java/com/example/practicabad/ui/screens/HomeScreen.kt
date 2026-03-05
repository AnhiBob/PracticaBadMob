package com.example.practicabad.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicabad.R
import com.example.practicabad.ui.components.BottomMenu
import com.example.practicabad.ui.components.CategoryChip
import com.example.practicabad.ui.components.ProductCard
import com.example.practicabad.ui.model.Category
import com.example.practicabad.ui.model.Product

@Composable
fun HomeScreen(
    onProductClick: (String) -> Unit,
    onCartClick: () -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onCatalogClick: () -> Unit  // Добавлен новый параметр
) {
    var selectedItem by remember { mutableStateOf(0) }
    var selectedCategory by remember { mutableStateOf("all") }

    // Тестовые данные категорий
    val categories = listOf(
        Category("all", "Все", true),
        Category("outdoor", "Outdoor"),
        Category("tennis", "Tennis"),
        Category("men", "Мужские"),
        Category("women", "Женские")
    )

    // Тестовые данные товаров
    val products = listOf(
        Product(
            id = "1",
            name = "Nike Air Max",
            price = "13 999 ₽",
            oldPrice = "15 999 ₽",
            category = "Outdoor",
            description = "Ретро-кроссовки в стиле баскетбола",
            imageRes = R.drawable.image_1,
            isFavorite = false
        ),
        Product(
            id = "2",
            name = "Adidas Ultraboost",
            price = "17 999 ₽",
            oldPrice = null,
            category = "Бег",
            description = "Беговые кроссовки с инновационной амортизацией",
            imageRes = R.drawable.image_2,
            isFavorite = false
        ),
        Product(
            id = "3",
            name = "Puma RS-X",
            price = "12 999 ₽",
            oldPrice = null,
            category = "Кежуал",
            description = "Сочетание ретро-вдохновения с современными технологиями",
            imageRes = R.drawable.image_3,
            isFavorite = false
        ),
        Product(
            id = "4",
            name = "Reebok Classic",
            price = "9 999 ₽",
            oldPrice = "11 999 ₽",
            category = "Кежуал",
            description = "Ностальгические кеды в стиле ретро",
            imageRes = R.drawable.image_1,
            isFavorite = false
        ),
        Product(
            id = "5",
            name = "New Balance 574",
            price = "14 999 ₽",
            oldPrice = null,
            category = "Outdoor",
            description = "Классическая модель для повседневной носки",
            imageRes = R.drawable.image_2,
            isFavorite = false
        ),
        Product(
            id = "6",
            name = "Converse Chuck Taylor",
            price = "7 999 ₽",
            oldPrice = "9 999 ₽",
            category = "Кежуал",
            description = "Легендарные кеды на все времена",
            imageRes = R.drawable.image_3,
            isFavorite = false
        )
    )

    Scaffold(
        bottomBar = {
            BottomMenu(
                selectedItem = selectedItem,
                onItemSelected = { index ->
                    selectedItem = index
                    when (index) {
                        1 -> onFavoriteClick()
                        2 -> onNotificationsClick()
                        3 -> onProfileClick()
                    }
                },
                onCartClick = onCartClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Верхняя панель
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Главная",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )

                // Поиск
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Поле поиска
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF5F5F5))
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onSearchClick() }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Поиск",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Поиск...",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Кнопка фильтра
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onSearchClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.sliders),
                            contentDescription = "Фильтр",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // Основной контент
            when (selectedItem) {
                0 -> {
                    // Главная
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // Категории
                        item {
                            Column {
                                Text(
                                    text = "Категории",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )

                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(categories) { category ->
                                        CategoryChip(
                                            category = category,
                                            onClick = { id ->
                                                selectedCategory = id
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // Популярные товары
                        item {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Популярное",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "Все",
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 14.sp,
                                        modifier = Modifier.clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) {
                                            onCatalogClick()  // ← ИСПРАВЛЕНО: теперь используем колбэк
                                        }
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(products.take(4)) { product ->
                                        ProductCard(
                                            product = product,
                                            onProductClick = onProductClick,
                                            onFavoriteClick = { id, isFavorite ->
                                                println("Товар $id избранное: $isFavorite")
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // Акции
                        item {
                            Column {
                                Text(
                                    text = "Акции",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFF4CAF50)
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = "Summer Sale",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                            Text(
                                                text = "15% OFF",
                                                fontSize = 28.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = Color.White
                                            )
                                        }

                                        Button(
                                            onClick = {},
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.White
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Text(
                                                text = "Смотреть",
                                                color = Color(0xFF4CAF50)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Новинки
                        item {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Новинки",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "Все",
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 14.sp,
                                        modifier = Modifier.clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) {
                                            onCatalogClick()  // ← ИСПРАВЛЕНО
                                        }
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(products.drop(4).take(2)) { product ->
                                        ProductCard(
                                            product = product,
                                            onProductClick = onProductClick,
                                            onFavoriteClick = { id, isFavorite ->
                                                println("Товар $id избранное: $isFavorite")
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                1 -> {
                    // Избранное
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Избранное",
                            fontSize = 20.sp,
                            color = Color.Gray
                        )
                    }
                }
                2 -> {
                    // Уведомления
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Уведомления",
                            fontSize = 20.sp,
                            color = Color.Gray
                        )
                    }
                }
                3 -> {
                    // Профиль
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Профиль",
                            fontSize = 20.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}