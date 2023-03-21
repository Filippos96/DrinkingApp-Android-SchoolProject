package com.example.drinkingapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CreatePrompt(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
) {

    var prompt by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavbarTop(screenName = "Create Prompt", backButton = false, navController = navController)

        //Spacer(modifier = Modifier.height(140.dp))
        Spacer(modifier = Modifier.height(177.dp))

        Image(
            painter = painterResource(id = R.drawable.martini),
            contentDescription = "martini",
            modifier = Modifier.size(160.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))
        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            label = { Text(text = "Who's most likely to...") },
            placeholder = { Text(text = "Enter your prompt") }
        )

        Spacer(modifier = Modifier.height(200.dp))

        OrangeButton(
            navController = navController,
            buttonText = "SUBMIT",
            onClick = {
                gameRoomViewModel.createNewPrompt(prompt, navController)
            }
        )

    }
}