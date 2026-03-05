package com.example.practicabad.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.practicabad.R

@Composable
fun BottomMenu(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
    ) {
        // Фоновая картинка
        Image(
            painter = painterResource(id = R.drawable.vector_1789),
            contentDescription = null,
            modifier = Modifier.matchParentSize()
        )

        // Контент меню
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Левая группа иконок
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = { onItemSelected(0) },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "Главная",
                        tint = if (selectedItem == 0)
                            MaterialTheme.colorScheme.primary
                        else Color.Black
                    )
                }

                IconButton(
                    onClick = { onItemSelected(1) },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (selectedItem == 1)
                                R.drawable.favorite_fill
                            else R.drawable.favorite
                        ),
                        contentDescription = "Избранное",
                        tint = if (selectedItem == 1)
                            MaterialTheme.colorScheme.primary
                        else Color.Black
                    )
                }
            }

            // Центральная кнопка корзины (выше)
            FloatingActionButton(
                onClick = onCartClick,
                modifier = Modifier.offset(y = (-20).dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bag_2),
                    contentDescription = "Корзина",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Правая группа иконок
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = { onItemSelected(2) },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "Уведомления",
                        tint = if (selectedItem == 2)
                            MaterialTheme.colorScheme.primary
                        else Color.Black
                    )
                }

                IconButton(
                    onClick = { onItemSelected(3) },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Профиль",
                        tint = if (selectedItem == 3)
                            MaterialTheme.colorScheme.primary
                        else Color.Black
                    )
                }
            }
        }
    }
}