package com.example.drinkingapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NavbarTop(screenName: String, backButton: Boolean, navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .height(30.dp),
            color = Color.Transparent
        ) {
            if (backButton) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "BackButton",
                    modifier = Modifier
                        .size(ButtonDefaults.IconSize)
                        .clickable {
                               navController.popBackStack()
                        },
                    tint = Color.White
                )
            }
        }

        Surface(
            modifier = Modifier
                .weight(7f),
            color = Color.Transparent
        ) {
            Text(
                text = screenName,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        Surface(
            modifier = Modifier
                .weight(1f)
        ) {

        }
    }
}