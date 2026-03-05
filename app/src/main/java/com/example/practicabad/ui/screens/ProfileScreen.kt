package com.example.practicabad.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.example.practicabad.ui.components.DisableButton

@Composable
fun ProfileScreen(
    onEditProfileClick: () -> Unit = {},
    onChangePhotoClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onSignOutClick: () -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("Екатерина") }
    var lastName by remember { mutableStateOf("Серафимова") }
    var email by remember { mutableStateOf("ekaterina@mail.com") }
    var phone by remember { mutableStateOf("+7 (999) 123-45-67") }
    var address by remember { mutableStateOf("Москва, Россия") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Верхняя часть с заголовком и кнопкой редактирования
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Профиль",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = {
                    if (isEditing) {
                        // Отмена редактирования
                        isEditing = false
                    } else {
                        onEditProfileClick()
                        isEditing = true
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = if (isEditing) "Отмена" else "Редактировать",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Аватар
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
            ) {
                // Заглушка для фото профиля
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Аватар",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (isEditing) {
                TextButton(
                    onClick = onChangePhotoClick
                ) {
                    Text(
                        text = "Изменить фото профиля",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$name $lastName",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = email,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Поля профиля
        if (isEditing) {
            // Режим редактирования
            ProfileEditField(
                label = "Имя",
                value = name,
                onValueChange = { name = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProfileEditField(
                label = "Фамилия",
                value = lastName,
                onValueChange = { lastName = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProfileEditField(
                label = "Email",
                value = email,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProfileEditField(
                label = "Телефон",
                value = phone,
                onValueChange = { phone = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProfileEditField(
                label = "Адрес",
                value = address,
                onValueChange = { address = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onSaveClick()
                    isEditing = false
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Сохранить",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        } else {
            // Режим просмотра
            ProfileInfoRow(label = "Имя", value = name)
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            ProfileInfoRow(label = "Фамилия", value = lastName)
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            ProfileInfoRow(label = "Email", value = email)
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            ProfileInfoRow(label = "Телефон", value = phone)
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            ProfileInfoRow(label = "Адрес", value = address)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка выхода
        Button(
            onClick = onSignOutClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF4444)
            )
        ) {
            Text(
                text = "Выйти",
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.White
            )
        }
    }
}

@Composable
fun ProfileInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ProfileEditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )
    }
}