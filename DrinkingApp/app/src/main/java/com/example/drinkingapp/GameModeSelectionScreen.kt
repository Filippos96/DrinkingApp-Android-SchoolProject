package com.example.drinkingapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.drinkingapp.ui.theme.*

@Composable
fun gameModeSelectionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lobby",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        GradientButton(
            text = "Who's Most Likely?",
            textColor = Color.White,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    purpleButtonColor2,
                    purpleButtonColor1
                )
            )
        ) {

        }
        GradientButton(
            text = "Would you rather?",
            textColor = Color.White,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    redButtonColor2,
                    redButtonColor1
                )
            )
        ) {

        }
        GradientButton(
            text = "Music quiz!",
            textColor = Color.White,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    greenButtonColor2,
                    greenButtonColor1
                )
            )
        ) {

        }

    }
}