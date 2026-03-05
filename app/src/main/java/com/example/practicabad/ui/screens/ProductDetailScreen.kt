package com.example.practicabad.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        ),
        Product(
            id = "5",
            name = "New Balance 574",
            price = "14 999 ₽",
            oldPrice = null,
            category = "Outdoor",
            description = "Классическая модель для повседневной носки с превосходной амортизацией. Верх из замши и сетки обеспечивает комфорт и долговечность. Универсальный дизайн подходит для любого образа.",
            imageRes = R.drawable.image_2,
            isFavorite = false
        ),
        Product(
            id = "6",
            name = "Converse Chuck Taylor",
            price = "7 999 ₽",
            oldPrice = "9 999 ₽",
            category = "Кежуал",
            description = "Легендарные кеды, которые никогда не выходят из моды. Холщовый верх, резиновая подошва и узнаваемый дизайн делают их фаворитами уже много десятилетий.",
            imageRes = R.drawable.image_3,
            isFavorite = false
        ),
        Product(
            id = "7",
            name = "Vans Old Skool",
            price = "8 999 ₽",
            oldPrice = null,
            category = "Кежуал",
            description = "Классические скейтерские кеды с замшевыми вставками и фирменной боковой полосой. Прочные и стильные, созданы для повседневной носки.",
            imageRes = R.drawable.image_1,
            isFavorite = false
        ),
        Product(
            id = "8",
            name = "Asics Gel-Kayano",
            price = "15 999 ₽",
            oldPrice = "17 999 ₽",
            category = "Бег",
            description = "Профессиональные беговые кроссовки с технологией гелевой амортизации. Обеспечивают максимальную поддержку и комфорт на длинных дистанциях.",
            imageRes = R.drawable.image_2,
            isFavorite = false
        )
    )

    val currentIndex = allProducts.indexOfFirst { it.id == productId }.coerceAtLeast(0)
    val pagerState = rememberPagerState { allProducts.size }
    val coroutineScope = rememberCoroutineScope()

    var isDescriptionExpanded by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(allProducts[currentIndex].isFavorite) }

    LaunchedEffect(Unit) {
        pagerState.scrollToPage(currentIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        isFavorite = allProducts[pagerState.currentPage].isFavorite
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
                    onFavoriteClick(allProducts[pagerState.currentPage].id, isFavorite)
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Избранное",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }

        // Галерея изображений
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
                    painter = painterResource(id = allProducts[page].imageRes),
                    contentDescription = allProducts[page].name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Индикаторы
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(allProducts.size) { index ->
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
            val currentProduct = allProducts[pagerState.currentPage]

            Text(
                text = currentProduct.category,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = currentProduct.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentProduct.price,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                currentProduct.oldPrice?.let {
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

            Text(
                text = "Описание",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = currentProduct.description ?: "Описание отсутствует",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                maxLines = if (isDescriptionExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis
            )

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

            Button(
                onClick = { onAddToCartClick(currentProduct.id) },
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