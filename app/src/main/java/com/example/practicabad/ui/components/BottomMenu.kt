package com.example.practicabad.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
                MenuIcon(
                    icon = Icons.Default.Home,
                    isSelected = selectedItem == 0,
                    onClick = { onItemSelected(0) }
                )

                MenuIcon(
                    icon = Icons.Default.FavoriteBorder,
                    selectedIcon = Icons.Default.Favorite,
                    isSelected = selectedItem == 1,
                    onClick = { onItemSelected(1) }
                )
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
                MenuIcon(
                    icon = Icons.Default.FavoriteBorder,
                    isSelected = selectedItem == 2,
                    onClick = { onItemSelected(2) }
                )

                MenuIcon(
                    icon = Icons.Default.Person,
                    isSelected = selectedItem == 3,
                    onClick = { onItemSelected(3) }
                )
            }
        }
    }
}

@Composable
fun MenuIcon(
    icon: ImageVector,
    selectedIcon: ImageVector? = null,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = if (isSelected && selectedIcon != null) selectedIcon else icon,
            contentDescription = null,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black
        )
    }
}