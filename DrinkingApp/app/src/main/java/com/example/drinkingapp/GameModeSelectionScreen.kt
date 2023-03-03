package com.example.drinkingapp

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drinkingapp.ui.theme.*

@Composable
fun GameModeSelectionScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavbarTop(screenName = "Game Mode", backButton = true, navController = navController)

        Spacer(modifier = Modifier.height(140.dp))

        GradientButton(
            text = "Who´s most likely?",
            textColor = Color.White,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    greenButtonColor2,
                    greenButtonColor1
                )
            ),
            onClick = { navController.navigate(Screen.Lobby.route) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        GradientButton(
            text = "Would you rather?",
            textColor = Color.White,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    purpleButtonColor2,
                    purpleButtonColor1
                )
            )
        ) {

        }

        Spacer(modifier = Modifier.height(20.dp))

        GradientButton(
            text = "Music quiz!",
            textColor = Color.White,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    redButtonColor2,
                    redButtonColor1
                )
            )
        ) {

        }

        Spacer(modifier = Modifier.height(160.dp))

        Text(text = "Please select a game mode...")


    }
}


