package com.example.drinkingapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun QuestionScreen(
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

        NavbarTop(screenName = "Who's Most likely to..", backButton = false, navController = navController)

        Spacer(modifier = Modifier.height(140.dp))

        Text(text = gameRoomViewModel.prompts.last())

        Spacer(modifier = Modifier.height(10.dp))

        for ( player in gameRoomViewModel.lobby.value.players ) {
            Button(
                onClick = {
                    gameRoomViewModel.submitAnswer(player.username, navController)
                    navController.navigate(Screen.Waiting.route)
                }
            ) {
                Text(text = player.username)
            }
        }
    }
}