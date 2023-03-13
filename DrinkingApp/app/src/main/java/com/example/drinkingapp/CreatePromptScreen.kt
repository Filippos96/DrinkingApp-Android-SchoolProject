package com.example.drinkingapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drinkingapp.ui.theme.*

@Composable
fun CreatePrompt(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel,
    lobbyKey: String
) {

    var prompt by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavbarTop(screenName = "Create Prompt", backButton = true, navController = navController)

        Spacer(modifier = Modifier.height(140.dp))

        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            label = { Text(text = "Prompt") },
            placeholder = { Text(text = "Enter your prompt") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OrangeButton(
            navController = navController,
            buttonText = "SUBMIT",
            onClick = {
                gameRoomViewModel.createNewPrompt(prompt, lobbyKey, navController)
            }
        )

    }
}