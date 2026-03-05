package com.example.practicabad.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.practicabad.data.model.OnboardingSlide
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardScreen(
    onGetStartedClick: () -> Unit
) {
    val slides = listOf(
        OnboardingSlide(
            title = "Добро пожаловать",
            subtitle = "Начнем путешествие",
            description = "Умная, великолепная и модная коллекция",
            buttonText = "Начать",
            imageRes = R.drawable.image_1
        ),
        OnboardingSlide(
            title = "У вас есть сила",
            subtitle = "В вашей комнате много красивых растений",
            description = "Изучите сейчас",
            buttonText = "Далее",
            imageRes = R.drawable.image_2
        ),
        OnboardingSlide(
            title = "Готовы?",
            subtitle = "Начнем покупки",
            description = "Лучшие кроссовки для тебя",
            buttonText = "Далее",
            imageRes = R.drawable.image_3
        )
    )

    val pagerState = rememberPagerState { slides.size }
    val coroutineScope = rememberCoroutineScope()
    var currentPage by remember { mutableStateOf(0) }

    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val alpha by animateFloatAsState(
                targetValue = if (pagerState.currentPage == page) 1f else 0f,
                animationSpec = tween(durationMillis = 500),
                label = "alpha"
            )

            OnboardingSlideItem(
                slide = slides[page],
                alpha = alpha,
                isFirstSlide = page == 0  // ← Добавляем этот параметр
            )
        }

        // Индикаторы и кнопка внизу
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Индикаторы точек
            Row(
                modifier = Modifier.padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(slides.size) { index ->
                    val width by animateDpAsState(
                        targetValue = if (pagerState.currentPage == index) 43.dp else 28.dp,
                        animationSpec = tween(durationMillis = 300),
                        label = "dotWidth"
                    )

                    val color by animateColorAsState(
                        targetValue = if (pagerState.currentPage == index)
                            Color.White
                        else Color(0xFF2B6B8B),
                        animationSpec = tween(durationMillis = 300),
                        label = "dotColor"
                    )

                    Box(
                        modifier = Modifier
                            .width(width)
                            .height(8.dp)  // фиксированная высота 8dp
                            .background(
                                color = color,
                                shape = RoundedCornerShape(50)  // делает овал
                            )
                    )
                }
            }

            // Кнопка
            Button(
                onClick = {
                    if (pagerState.currentPage == slides.size - 1) {
                        onGetStartedClick()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Text(
                    text = if (pagerState.currentPage == slides.size - 1) "Начать" else "Далее",
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun OnboardingSlideItem(
    slide: OnboardingSlide,
    alpha: Float,
    isFirstSlide: Boolean  // Добавляем параметр
) {
    if (isFirstSlide) {
        // Первый слайд - картинка на всю ширину
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF48B2E7))
        ) {
            Spacer(modifier = Modifier.height(122.dp))
            // Текст внизу
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = slide.title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(122.dp))
            // Картинка на всю ширину
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Spacer(modifier = Modifier.height(122.dp))
                Image(
                    painter = painterResource(id = slide.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }


        }
    } else {
        // Остальные слайды - как было
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF48B2E7))
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = slide.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = slide.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = slide.subtitle,
                fontSize = 18.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = slide.description,
                fontSize = 16.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        }
    }
}