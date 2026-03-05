package com.example.practicabad.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.example.practicabad.R
import com.example.practicabad.ui.model.Product
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailScreen(
    productId: String,
    onBackClick: () -> Unit,
    onAddToCartClick: (String) -> Unit,
    onFavoriteClick: (String, Boolean) -> Unit
) {
    // Тестовые данные товаров (в реальном проекте загружались бы с сервера)
    val allProducts = listOf(
        Product(
            id = "1",
            name = "Nike Air Max",
            price = "13 999 ₽",
            oldPrice = "15 999 ₽",
            category = "Outdoor",
            description = "Ретро-кроссовки в стиле баскетбола отличаются массивной подошвой и спортивным силуэтом. Прочный нейлоновый верх с замшевыми вставками обеспечивает поддержку и воздухопроницаемость. Амортизирующая подошва гарантирует мягкость и отзывчивость при каждом шаге. Прочная резиновая подошва обеспечивает сцепление и долговечность для бега или активного образа жизни.",
            imageRes = R.drawable.image_1,
            isFavorite = false
        ),
        Product(
            id = "2",
            name = "Adidas Ultraboost",
            price = "17 999 ₽",
            oldPrice = null,
            category = "Бег",
            description = "Беговые кроссовки с инновационной амортизацией обеспечат мягкий и плавный бег. Трио-резиновая подошва с пенным сердечником поглощает удары и обеспечивает отзывчивость. Верх из сетки с поддерживающими накладками обеспечивает воздухопроницаемость и поддержку. Идеальный выбор для бегунов, ищущих максимальный комфорт и амортизацию.",
            imageRes = R.drawable.image_2,
            isFavorite = false
        ),
        Product(
            id = "3",
            name = "Puma RS-X",
            price = "12 999 ₽",
            oldPrice = null,
            category = "Кежуал",
            description = "Эти кроссовки сочетают в себе ретро-вдохновение с современными технологиями. Многослойный верх из различных материалов создает уникальный и привлекательный внешний вид. Эксклюзивная подошва обеспечивает амортизацию и комфорт, а выглядывающий механизм шнуровки добавляет спортивный штрих. Идеально подходят для тех, кто хочет выделиться из толпы стильной и функциональной обувью.",
            imageRes = R.drawable.image_3,
            isFavorite = false
        ),
        Product(
            id = "4",
            name = "Reebok Classic",
            price = "9 999 ₽",
            oldPrice = "11 999 ₽",
            category = "Кежуал",
            description = "Ностальгические кеды в стиле ретро перенесут вас в прошлое с классическим холщовым верхом для воздухопроницаемости и комфорта. Контрастная резиновая подошва добавляет винтажный штрих. Удобный круглый носок и гибкая конструкция делают их идеальными для повседневных прогулок и неформальных мероприятий.",
            imageRes = R.drawable.image_1,
            isFavorite = false
        )
    )

    val currentProduct = allProducts.find { it.id == productId } ?: allProducts[0]
    val productsForSwipe = allProducts
    val currentIndex = productsForSwipe.indexOfFirst { it.id == productId }.coerceAtLeast(0)

    var isFavorite by remember { mutableStateOf(currentProduct.isFavorite) }
    var isDescriptionExpanded by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState { productsForSwipe.size }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        pagerState.scrollToPage(currentIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        val newProduct = productsForSwipe[pagerState.currentPage]
        isFavorite = newProduct.isFavorite
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
                text = "Детали товара",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = {
                    isFavorite = !isFavorite
                    onFavoriteClick(productsForSwipe[pagerState.currentPage].id, isFavorite)
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Избранное",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }

        // Галерея изображений (горизонтальный пейджер)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color(0xFFF5F5F5))
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Image(
                    painter = painterResource(id = productsForSwipe[page].imageRes),
                    contentDescription = productsForSwipe[page].name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Индикаторы страниц
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(productsForSwipe.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (pagerState.currentPage == index) 10.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (pagerState.currentPage == index)
                                    MaterialTheme.colorScheme.primary
                                else Color.Gray.copy(alpha = 0.5f)
                            )
                    )
                }
            }
        }

        // Информация о товаре
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Категория и название
            Text(
                text = productsForSwipe[pagerState.currentPage].category,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = productsForSwipe[pagerState.currentPage].name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Цена
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = productsForSwipe[pagerState.currentPage].price,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                productsForSwipe[pagerState.currentPage].oldPrice?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = it,
                        fontSize = 18.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Описание
            Text(
                text = "Описание",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = productsForSwipe[pagerState.currentPage].description ?: "Описание отсутствует",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                maxLines = if (isDescriptionExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis
            )

            // Кнопка "Подробнее"
            TextButton(
                onClick = { isDescriptionExpanded = !isDescriptionExpanded },
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = if (isDescriptionExpanded) "Свернуть" else "Подробнее",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Кнопка добавления в корзину
            Button(
                onClick = { onAddToCartClick(productsForSwipe[pagerState.currentPage].id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Добавить в корзину",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}