package com.example.practicabad.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicabad.R
import com.example.practicabad.ui.model.OnboardingSlide
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
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

    // Анимация появления/исчезновения
    val transition = updateTransition(targetState = currentPage, label = "pageTransition")

    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            // Анимируем появление каждого слайда
            val alpha by animateFloatAsState(
                targetValue = if (pagerState.currentPage == page) 1f else 0f,
                animationSpec = tween(durationMillis = 500),
                label = "alpha"
            )

            OnboardingSlideItem(
                slide = slides[page],
                alpha = alpha
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
            // Индикаторы точек с анимацией
            Row(
                modifier = Modifier.padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(slides.size) { index ->
                    val size by animateDpAsState(
                        targetValue = if (pagerState.currentPage == index) 12.dp else 8.dp,
                        animationSpec = tween(durationMillis = 300),
                        label = "dotSize"
                    )

                    val color by animateColorAsState(
                        targetValue = if (pagerState.currentPage == index)
                            MaterialTheme.colorScheme.primary
                        else Color.Gray.copy(alpha = 0.5f),
                        animationSpec = tween(durationMillis = 300),
                        label = "dotColor"
                    )

                    Box(
                        modifier = Modifier
                            .size(size)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }

            // Кнопка с изменяющимся текстом
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
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = if (pagerState.currentPage == slides.size - 1) "Начать" else "Далее",
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun OnboardingSlideItem(
    slide: OnboardingSlide,
    alpha: Float
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Анимируем появление картинки
        AnimatedVisibility(
            visible = alpha > 0.5f,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            Image(
                painter = painterResource(id = slide.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 32.dp),
                contentScale = ContentScale.Fit
            )
        }

        // Анимируем появление текста
        AnimatedVisibility(
            visible = alpha > 0.5f,
            enter = fadeIn(animationSpec = tween(delayMillis = 200)) + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
}